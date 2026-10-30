package com.christa.vp_week6.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christa.vp_week6.model.Project
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.NavyCard
import com.christa.vp_week6.ui.theme.SuccessText
import com.christa.vp_week6.ui.theme.TextGray
import com.christa.vp_week6.widgets.CampusCard
import com.christa.vp_week6.widgets.CampusTopBar
import com.christa.vp_week6.widgets.StatusBadge
import com.christa.vp_week6.widgets.TechTag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectDetailScreen(
    project: Project?,
    communityName: String,
    onBackClick: () -> Unit,
    onToggleStatus: () -> Unit
) {
    Scaffold(
        topBar = {
            CampusTopBar(title = "Project Detail", onBackClick = onBackClick)
        }
    ) { innerPadding ->
        if (project == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Proyek tidak ditemukan", color = TextGray)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFF1E293B), NavyCard)))
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            communityName.uppercase(),
                            color = Color(0xFF93C5FD),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(Color(0xFF1E3A8A).copy(alpha = 0.7f), RoundedCornerShape(50))
                                .border(1.dp, Color(0xFF3B82F6).copy(alpha = 0.6f), RoundedCornerShape(50))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        Spacer(Modifier.height(14.dp))
                        Text(project.title, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }

                CampusCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Mark as Done", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Status:  ", fontSize = 13.sp, color = TextGray)
                                StatusBadge(project.isCompleted)
                            }
                        }
                        Switch(
                            checked = project.isCompleted,
                            onCheckedChange = { onToggleStatus() },
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = SuccessText,
                                checkedThumbColor = Color.White,
                                uncheckedTrackColor = CardBorder,
                                uncheckedThumbColor = Color.White,
                                uncheckedBorderColor = Color.Transparent
                            )
                        )
                    }
                }

                CampusCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Deskripsi Lengkap Proyek", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(project.description, fontSize = 14.sp, color = Color(0xFF475569), lineHeight = 22.sp)
                    }
                }

                CampusCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Tech Stack Terpilih", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.height(10.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            project.techStack.forEach { TechTag(it, showIcon = true) }
                        }
                    }
                }
            }
        }
    }
}