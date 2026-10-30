package com.christa.vp_week6.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.ChipGray
import com.christa.vp_week6.ui.theme.DangerLight
import com.christa.vp_week6.ui.theme.DangerRed
import com.christa.vp_week6.ui.theme.PrimaryBlue
import com.christa.vp_week6.ui.theme.SuccessLight
import com.christa.vp_week6.ui.theme.SuccessText
import com.christa.vp_week6.ui.theme.TextDark
import com.christa.vp_week6.ui.theme.WarningLight
import com.christa.vp_week6.ui.theme.WarningText

@Composable
fun JoinedBadge() {
    Text(
        "Joined ✓",
        color = DangerRed,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .background(DangerLight.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .border(1.dp, DangerRed.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
fun StatusBadge(isCompleted: Boolean) {
    val textColor = if (isCompleted) SuccessText else WarningText
    val bgColor = if (isCompleted) SuccessLight else WarningLight
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(textColor)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            if (isCompleted) "Completed" else "In Progress",
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TechTag(name: String, showIcon: Boolean = false) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(ChipGray, shape)
            .then(if (showIcon) Modifier.border(1.dp, CardBorder, shape) else Modifier)
            .padding(
                horizontal = if (showIcon) 12.dp else 8.dp,
                vertical = if (showIcon) 8.dp else 4.dp
            )
    ) {
        if (showIcon) {
            Icon(techIcon(name), contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(6.dp))
        }
        Text(
            name,
            fontSize = if (showIcon) 13.sp else 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
    }
}