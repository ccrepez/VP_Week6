package com.christa.vp_week6.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.FilterNone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christa.vp_week6.model.Community
import com.christa.vp_week6.model.Project
import com.christa.vp_week6.model.Student
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.DeepBlue
import com.christa.vp_week6.ui.theme.PrimaryBlue
import com.christa.vp_week6.ui.theme.PurpleAccent
import com.christa.vp_week6.ui.theme.TextGray
import com.christa.vp_week6.widgets.CampusCard
import com.christa.vp_week6.widgets.CampusTopBar
import com.christa.vp_week6.widgets.CommunityLogo
import com.christa.vp_week6.widgets.IconBox
import com.christa.vp_week6.widgets.JoinedBadge
import com.christa.vp_week6.widgets.SectionHeader
import com.christa.vp_week6.widgets.StatusBadge
import com.christa.vp_week6.widgets.projectColor
import com.christa.vp_week6.widgets.projectIcon

@Composable
fun DashboardScreen(
    student: Student,
    joinedCommunities: List<Community>,
    communities: List<Community>,
    projects: List<Project>,
    onProjectClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CampusTopBar(
                title = "CampusTech Hub",
                subtitle = "Student Profile & Portfolio Dashboard",
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Color(0xFF4F46E5), PrimaryBlue))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(student.initials, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { ProfileCard(student) }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    CounterCard(
                        icon = Icons.Default.Groups,
                        color = PrimaryBlue,
                        count = joinedCommunities.size,
                        label = "Joined Clubs",
                        caption = "Komunitas yang Diikuti",
                        modifier = Modifier.weight(1f)
                    )
                    CounterCard(
                        icon = Icons.Outlined.FilterNone,
                        color = PurpleAccent,
                        count = projects.size,
                        label = "Total Projects",
                        caption = "Seluruh Proyek Dibuat",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                SectionHeader(
                    icon = Icons.Default.Star,
                    iconColor = PrimaryBlue,
                    title = "Semua Komunitas yang Diikuti (${joinedCommunities.size})",
                    trailing = "Status: Joined"
                )
            }

            if (joinedCommunities.isEmpty()) {
                item { EmptyMessage("Belum bergabung dengan komunitas") }
            } else {
                items(joinedCommunities, key = { it.id }) { community ->
                    JoinedClubItem(community, projects.count { it.communityId == community.id })
                }
            }

            item {
                SectionHeader(
                    icon = Icons.Outlined.CheckBoxOutlineBlank,
                    iconColor = PurpleAccent,
                    title = "Semua Proyek Mahasiswa (${projects.size})",
                    trailing = "Total: ${projects.size} Proyek"
                )
            }

            if (projects.isEmpty()) {
                item { EmptyMessage("Belum ada proyek yang dicatat") }
            } else {
                items(projects, key = { it.id }) { project ->
                    val communityName = communities.find { it.id == project.communityId }?.name.orEmpty()
                    ProjectSummaryItem(project, communityName) { onProjectClick(project.id) }
                }
            }
        }
    }
}

@Composable
private fun ProfileCard(student: Student) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(DeepBlue, Color(0xFF2563EB), Color(0xFF3B82F6))))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(student.initials, color = DeepBlue, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    "MAHASISWA AKTIF",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                )
                Spacer(Modifier.height(6.dp))
                Text(student.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    "${student.major}  •  Angkatan ${student.batch}",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 13.sp
                )
                Text("NIM: ${student.nim}", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun CounterCard(
    icon: ImageVector,
    color: Color,
    count: Int,
    label: String,
    caption: String,
    modifier: Modifier = Modifier
) {
    CampusCard(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            IconBox(icon = icon, color = color, size = 40.dp)
            Spacer(Modifier.height(12.dp))
            Text(count.toString(), fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            Text(caption, fontSize = 12.sp, color = TextGray)
        }
    }
}

@Composable
private fun EmptyMessage(text: String) {
    CampusCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text,
            color = TextGray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )
    }
}

@Composable
private fun JoinedClubItem(community: Community, projectCount: Int) {
    CampusCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CommunityLogo(community, size = 40.dp)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(community.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text("${community.tagline} • $projectCount Proyek", fontSize = 12.sp, color = TextGray)
            }
            JoinedBadge()
        }
    }
}

@Composable
private fun ProjectSummaryItem(project: Project, communityName: String, onClick: () -> Unit) {
    CampusCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBox(icon = projectIcon(project), color = projectColor(project), size = 48.dp)
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        communityName.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                    Text(project.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(project.techStack.joinToString(" • "), fontSize = 12.sp, color = TextGray)
                }
            }
            HorizontalDivider(color = CardBorder, modifier = Modifier.padding(vertical = 12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                StatusBadge(project.isCompleted)
            }
        }
    }
}