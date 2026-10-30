package com.christa.vp_week6.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christa.vp_week6.model.Community
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.DangerLight
import com.christa.vp_week6.ui.theme.DangerRed
import com.christa.vp_week6.ui.theme.PrimaryBlue
import com.christa.vp_week6.ui.theme.TextDark
import com.christa.vp_week6.ui.theme.TextGray
import com.christa.vp_week6.ui.theme.TextLight
import com.christa.vp_week6.widgets.CampusCard
import com.christa.vp_week6.widgets.CampusTopBar
import com.christa.vp_week6.widgets.CommunityLogo

@Composable
fun CommunitiesScreen(
    communities: List<Community>,
    searchQuery: String?,
    onSearchToggle: () -> Unit,
    onSearchChange: (String) -> Unit,
    onCommunityClick: (String) -> Unit,
    onToggleJoin: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CampusTopBar(
                title = "Communities Directory",
                subtitle = "Pilih komunitas untuk membuka Club Projects",
                actions = {
                    IconButton(onClick = onSearchToggle) {
                        Icon(
                            if (searchQuery == null) Icons.Default.Search else Icons.Default.Close,
                            contentDescription = "Cari komunitas",
                            tint = TextDark
                        )
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (searchQuery != null) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchChange,
                        placeholder = { Text("Cari komunitas...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = CardBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (communities.isEmpty()) {
                item {
                    Text(
                        "Komunitas tidak ditemukan",
                        color = TextGray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }

            items(communities, key = { it.id }) { community ->
                CommunityCard(
                    community = community,
                    onClick = { onCommunityClick(community.id) },
                    onToggleJoin = { onToggleJoin(community.id) }
                )
            }
        }
    }
}

@Composable
private fun CommunityCard(community: Community, onClick: () -> Unit, onToggleJoin: () -> Unit) {
    CampusCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CommunityLogo(community, size = 52.dp)
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(community.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        "${community.tagline} • ${community.members} Mahasiswa",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(community.description, fontSize = 14.sp, color = Color(0xFF475569), lineHeight = 20.sp)
            HorizontalDivider(color = CardBorder, modifier = Modifier.padding(vertical = 14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (community.isJoined) Icons.Outlined.LockOpen else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (community.isJoined) PrimaryBlue else TextLight,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    if (community.isJoined) "Unlocked • Buka Proyek →" else "Terkunci (Gate Akses)",
                    fontSize = 13.sp,
                    fontWeight = if (community.isJoined) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (community.isJoined) PrimaryBlue else TextLight,
                    modifier = Modifier.weight(1f)
                )
                if (community.isJoined) {
                    OutlinedButton(
                        onClick = onToggleJoin,
                        border = BorderStroke(1.dp, DangerRed),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = DangerLight.copy(alpha = 0.4f),
                            contentColor = DangerRed
                        )
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Joined", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onToggleJoin,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Join", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}