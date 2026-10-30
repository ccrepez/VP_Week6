package com.christa.vp_week6.model

data class Project(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val techStack: List<String>,
    val isCompleted: Boolean = false
)