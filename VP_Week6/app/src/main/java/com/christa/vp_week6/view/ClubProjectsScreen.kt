package com.christa.vp_week6.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.FilterNone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christa.vp_week6.model.Community
import com.christa.vp_week6.model.Project
import com.christa.vp_week6.model.ProjectForm
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.DangerLight
import com.christa.vp_week6.ui.theme.DangerRed
import com.christa.vp_week6.ui.theme.DeepBlue
import com.christa.vp_week6.ui.theme.PrimaryBlue
import com.christa.vp_week6.ui.theme.PrimaryLight
import com.christa.vp_week6.ui.theme.TextGray
import com.christa.vp_week6.widgets.AddProjectDialog
import com.christa.vp_week6.widgets.CampusCard
import com.christa.vp_week6.widgets.CampusTopBar
import com.christa.vp_week6.widgets.IconBox
import com.christa.vp_week6.widgets.JoinedBadge
import com.christa.vp_week6.widgets.SectionHeader
import com.christa.vp_week6.widgets.StatusBadge
import com.christa.vp_week6.widgets.TechTag
import com.christa.vp_week6.widgets.projectColor
import com.christa.vp_week6.widgets.projectIcon

@Composable
fun ClubProjectsScreen(
    community: Community,
    projects: List<Project>,
    form: ProjectForm,
    techOptions: List<String>,
    onBackClick: () -> Unit,
    onProjectClick: (String) -> Unit,
    onDeleteProject: (String) -> Unit,
    onAddClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTechToggle: (String) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            CampusTopBar(
                title = "${community.name} Projects",
                subtitle = community.fullName,
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                containerColor = PrimaryLight,
                contentColor = DeepBlue,
                shape = RoundedCornerShape(20.dp),
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Project", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                CampusCard(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = Color(0xFFEAF2FF),
                    borderColor = Color(0xFFBFDBFE)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(community.fullName, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DeepBlue)
                            Text(
                                "${community.members} Anggota • ${projects.size} Proyek Aktif",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryBlue
                            )
                        }
                        JoinedBadge()
                    }
                }
            }

            item {
                SectionHeader(
                    icon = Icons.Outlined.FilterNone,
                    iconColor = PrimaryBlue,
                    title = "Daftar Proyek Mahasiswa (${projects.size})"
                )
            }

            if (projects.isEmpty()) {
                item {
                    CampusCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Belum ada proyek yang dicatat",
                            color = TextGray,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }
            } else {
                items(projects, key = { it.id }) { project ->
                    ProjectCard(
                        project = project,
                        onClick = { onProjectClick(project.id) },
                        onDelete = { onDeleteProject(project.id) }
                    )
                }
            }
        }
    }

    if (form.isOpen) {
        AddProjectDialog(
            community = community,
            form = form,
            techOptions = techOptions,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            onTechToggle = onTechToggle,
            onCancel = onCancel,
            onSave = onSave
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectCard(project: Project, onClick: () -> Unit, onDelete: () -> Unit) {
    CampusCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                IconBox(icon = projectIcon(project), color = projectColor(project), size = 48.dp)
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(project.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        project.description,
                        fontSize = 13.sp,
                        color = TextGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.width(8.dp))
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(36.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = DangerLight,
                        contentColor = DangerRed
                    )
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Hapus proyek")
                }
            }
            Spacer(Modifier.height(12.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                project.techStack.forEach { TechTag(it) }
            }
            HorizontalDivider(color = CardBorder, modifier = Modifier.padding(vertical = 12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ID: ${project.id}", fontSize = 12.sp, color = TextGray, modifier = Modifier.weight(1f))
                StatusBadge(project.isCompleted)
            }
        }
    }
}