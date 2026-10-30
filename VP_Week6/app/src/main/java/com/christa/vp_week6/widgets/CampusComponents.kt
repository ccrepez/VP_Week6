package com.christa.vp_week6.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christa.vp_week6.model.Community
import com.christa.vp_week6.model.Project
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.NavyCard
import com.christa.vp_week6.ui.theme.PrimaryBlue
import com.christa.vp_week6.ui.theme.PurpleAccent
import com.christa.vp_week6.ui.theme.TextDark
import com.christa.vp_week6.ui.theme.TextGray

@Composable
fun CampusCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = Color.White,
    borderColor: Color = CardBorder,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val colors = CardDefaults.cardColors(containerColor = containerColor)
    val border = BorderStroke(1.dp, borderColor)

    if (onClick != null) {
        Card(onClick = onClick, modifier = modifier, shape = shape, colors = colors, border = border, content = content)
    } else {
        Card(modifier = modifier, shape = shape, colors = colors, border = border, content = content)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusTopBar(
    title: String,
    subtitle: String? = null,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column {
        TopAppBar(
            title = {
                Column {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextDark)
                    if (subtitle != null) {
                        Text(subtitle, fontSize = 12.sp, color = TextGray)
                    }
                }
            },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextDark)
                    }
                }
            },
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )
        HorizontalDivider(color = CardBorder)
    }
}

@Composable
fun SectionHeader(icon: ImageVector, iconColor: Color, title: String, trailing: String? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.weight(1f))
        if (trailing != null) {
            Text(trailing, color = PrimaryBlue, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun IconBox(icon: ImageVector, color: Color, size: Dp = 44.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(size * 0.5f))
    }
}

@Composable
fun CommunityLogo(community: Community, size: Dp = 44.dp) {
    IconBox(icon = communityIcon(community.id), color = Color(community.color), size = size)
}

@Composable
fun WarningSnackbar(data: SnackbarData) {
    Snackbar(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        containerColor = NavyCard,
        contentColor = Color.White,
        action = {
            TextButton(onClick = { data.performAction() }) {
                Text(data.visuals.actionLabel ?: "OK", color = Color(0xFF93C5FD), fontWeight = FontWeight.Bold)
            }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(10.dp))
            Text(data.visuals.message, fontSize = 14.sp)
        }
    }
}

fun communityIcon(communityId: String): ImageVector = when (communityId) {
    "gdsc" -> Icons.Default.Code
    "cyber" -> Icons.Default.Shield
    "ai" -> Icons.Default.AutoAwesome
    "mobile" -> Icons.Default.PhoneAndroid
    "data" -> Icons.Default.BarChart
    "uiux" -> Icons.Default.Palette
    "game" -> Icons.Default.SportsEsports
    else -> Icons.Default.Groups
}

fun techIcon(tech: String): ImageVector = when (tech) {
    "Kotlin" -> Icons.Default.Code
    "Jetpack Compose" -> Icons.Default.Widgets
    "Room" -> Icons.Default.Storage
    "Google Maps SDK" -> Icons.Default.Place
    "Firebase" -> Icons.Default.LocalFireDepartment
    "Ktor" -> Icons.Default.Dns
    "Python" -> Icons.Default.Terminal
    "CameraX" -> Icons.Default.PhotoCamera
    else -> Icons.Default.Code
}

fun projectIcon(project: Project): ImageVector = when {
    "CameraX" in project.techStack -> Icons.Default.QrCodeScanner
    "Ktor" in project.techStack -> Icons.Default.Cloud
    "Python" in project.techStack -> Icons.Default.Terminal
    else -> Icons.Default.Android
}

fun projectColor(project: Project): Color = when {
    "CameraX" in project.techStack -> Color(0xFF16A34A)
    "Ktor" in project.techStack -> PurpleAccent
    "Python" in project.techStack -> Color(0xFFEA580C)
    else -> PrimaryBlue
}