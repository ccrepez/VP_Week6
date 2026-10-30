package com.christa.vp_week6.model

data class Community(
    val id: String,
    val name: String,
    val fullName: String,
    val tagline: String,
    val members: Int,
    val description: String,
    val color: Long,
    val isJoined: Boolean = false
)