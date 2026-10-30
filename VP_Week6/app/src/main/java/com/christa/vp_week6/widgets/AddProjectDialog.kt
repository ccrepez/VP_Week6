package com.christa.vp_week6.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.christa.vp_week6.model.Community
import com.christa.vp_week6.model.ProjectForm
import com.christa.vp_week6.ui.theme.BackgroundGray
import com.christa.vp_week6.ui.theme.CardBorder
import com.christa.vp_week6.ui.theme.DeepBlue
import com.christa.vp_week6.ui.theme.PrimaryBlue
import com.christa.vp_week6.ui.theme.PrimaryLight
import com.christa.vp_week6.ui.theme.TextGray

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddProjectDialog(
    community: Community,
    form: ProjectForm,
    techOptions: List<String>,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTechToggle: (String) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    val fieldShape = RoundedCornerShape(14.dp)
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = BackgroundGray,
        unfocusedContainerColor = BackgroundGray,
        focusedBorderColor = PrimaryBlue,
        unfocusedBorderColor = CardBorder
    )

    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        containerColor = Color.White,
        shape = RoundedCornerShape(28.dp),
        title = {
            Column {
                Text("Add Project", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text(
                    "Komunitas: ${community.name} (${community.fullName})",
                    fontSize = 13.sp,
                    color = TextGray
                )
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text("Judul Proyek *", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = form.title,
                    onValueChange = onTitleChange,
                    placeholder = { Text("Contoh: Smart Campus Navigation") },
                    singleLine = true,
                    shape = fieldShape,
                    colors = fieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))
                Text("Deskripsi Proyek (Minimal 3 Baris) *", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = form.description,
                    onValueChange = onDescriptionChange,
                    placeholder = { Text("Jelaskan proyek yang dikerjakan") },
                    minLines = 3,
                    shape = fieldShape,
                    colors = fieldColors,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Tech Stack (Pilihan Multi-Selection) *",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "${form.selectedTech.size} Dipilih",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryBlue
                    )
                }
                Spacer(Modifier.height(8.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    techOptions.forEach { tech ->
                        val selected = tech in form.selectedTech
                        FilterChip(
                            selected = selected,
                            onClick = { onTechToggle(tech) },
                            label = { Text(tech, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal) },
                            leadingIcon = {
                                Icon(
                                    if (selected) Icons.Default.Check else Icons.Default.Add,
                                    contentDescription = null,
                                    tint = if (selected) DeepBlue else TextGray,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.White,
                                labelColor = TextGray,
                                selectedContainerColor = PrimaryLight,
                                selectedLabelColor = DeepBlue
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selected,
                                borderColor = CardBorder,
                                selectedBorderColor = PrimaryBlue,
                                selectedBorderWidth = 1.dp
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                enabled = form.isValid,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text("Save", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel", color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }
        }
    )
}