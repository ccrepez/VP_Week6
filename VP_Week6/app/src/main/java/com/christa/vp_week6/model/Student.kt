package com.christa.vp_week6.model

data class Student(
    val name: String,
    val major: String,
    val batch: Int,
    val nim: String
) {
    val initials: String
        get() = name.split(" ").take(2).joinToString("") { it.first().uppercase() }
}