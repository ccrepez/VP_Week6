package com.christa.vp_week6.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.DeepBlue
import com.christa.vp_week6.ui.theme.PrimaryLight
import com.christa.vp_week6.ui.theme.TextDark
import com.christa.vp_week6.ui.theme.TextGray
import com.christa.vp_week6.view.ClubProjectsScreen
import com.christa.vp_week6.view.CommunitiesScreen
import com.christa.vp_week6.view.DashboardScreen
import com.christa.vp_week6.view.ProjectDetailScreen
import com.christa.vp_week6.viewmodel.CampusViewModel
import com.christa.vp_week6.widgets.WarningSnackbar
import kotlinx.coroutines.launch

@Composable
fun CampusNavHost(viewModel: CampusViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val showBottomBar = currentDestination?.hasRoute<DashboardRoute>() == true ||
            currentDestination?.hasRoute<CommunitiesRoute>() == true

    val navigateToTab: (Any) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                CampusBottomBar(currentDestination, navigateToTab)
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data -> WarningSnackbar(data) }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<DashboardRoute> {
                DashboardScreen(
                    student = uiState.student,
                    joinedCommunities = uiState.joinedCommunities,
                    communities = uiState.communities,
                    projects = uiState.projects,
                    onProjectClick = { id -> navController.navigate(ProjectDetailRoute(id)) }
                )
            }

            composable<CommunitiesRoute> {
                CommunitiesScreen(
                    communities = uiState.filteredCommunities,
                    searchQuery = uiState.searchQuery,
                    onSearchToggle = viewModel::toggleSearch,
                    onSearchChange = viewModel::onSearchChange,
                    onCommunityClick = { id ->
                        if (viewModel.isJoined(id)) {
                            navController.navigate(ClubProjectsRoute(id))
                        } else {
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar(
                                    message = "Harap bergabung ke komunitas terlebih dahulu!",
                                    actionLabel = "OK",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
                    onToggleJoin = viewModel::toggleJoin
                )
            }

            composable<ClubProjectsRoute> { entry ->
                val communityId = entry.toRoute<ClubProjectsRoute>().communityId
                val community = uiState.communities.first { it.id == communityId }
                ClubProjectsScreen(
                    community = community,
                    projects = uiState.projects.filter { it.communityId == communityId },
                    form = uiState.form,
                    techOptions = viewModel.techOptions,
                    onBackClick = { navController.popBackStack() },
                    onProjectClick = { id -> navController.navigate(ProjectDetailRoute(id)) },
                    onDeleteProject = viewModel::deleteProject,
                    onAddClick = viewModel::openForm,
                    onTitleChange = viewModel::onTitleChange,
                    onDescriptionChange = viewModel::onDescriptionChange,
                    onTechToggle = viewModel::toggleTech,
                    onCancel = viewModel::closeForm,
                    onSave = { viewModel.saveProject(communityId) }
                )
            }

            composable<ProjectDetailRoute> { entry ->
                val projectId = entry.toRoute<ProjectDetailRoute>().projectId
                val project = uiState.projects.find { it.id == projectId }
                val communityName = uiState.communities
                    .find { it.id == project?.communityId }?.name.orEmpty()
                ProjectDetailScreen(
                    project = project,
                    communityName = communityName,
                    onBackClick = { navController.popBackStack() },
                    onToggleStatus = { viewModel.toggleProjectStatus(projectId) }
                )
            }
        }
    }
}

@Composable
private fun CampusBottomBar(currentDestination: NavDestination?, onTabClick: (Any) -> Unit) {
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = DeepBlue,
        selectedTextColor = TextDark,
        indicatorColor = PrimaryLight,
        unselectedIconColor = TextGray,
        unselectedTextColor = TextGray
    )

    Column {
        HorizontalDivider(color = CardBorder)
        NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.hasRoute<DashboardRoute>() } == true,
                onClick = { onTabClick(DashboardRoute) },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text("Dashboard") },
                colors = itemColors
            )
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.hasRoute<CommunitiesRoute>() } == true,
                onClick = { onTabClick(CommunitiesRoute) },
                icon = { Icon(Icons.Default.Groups, contentDescription = null) },
                label = { Text("Communities") },
                colors = itemColors
            )
        }
    }
}