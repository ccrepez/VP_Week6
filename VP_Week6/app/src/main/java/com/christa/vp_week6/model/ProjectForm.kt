package com.christa.vp_week6.model

data class ProjectForm(
    val isOpen: Boolean = false,
    val title: String = "",
    val description: String = "",
    val selectedTech: Set<String> = emptySet()
) {
    val isValid: Boolean
        get() = title.isNotBlank() && description.isNotBlank() && selectedTech.isNotEmpty()
}