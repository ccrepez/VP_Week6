package com.christa.vp_week6.data

import com.christa.vp_week6.model.Community
import com.christa.vp_week6.model.Project
import com.christa.vp_week6.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CampusRepository {

    val student = Student(
        name = "Aditya Pratama",
        major = "Teknik Informatika",
        batch = 2022,
        nim = "22001045"
    )

    val techOptions = listOf(
        "Kotlin", "Jetpack Compose", "Firebase", "Ktor",
        "Room", "Python", "Google Maps SDK", "CameraX"
    )

    private val _communities = MutableStateFlow(
        listOf(
            Community(
                "gdsc", "GDSC", "Google Developer Student Club", "Google Developer Student Club", 142,
                "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.",
                0xFF1A73E8, isJoined = true
            ),
            Community(
                "cyber", "CyberSecurity Guild", "CyberSecurity Guild", "Security & Pentesting", 88,
                "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.",
                0xFFD32F2F, isJoined = true
            ),
            Community(
                "ai", "AI Society", "AI Society", "Machine Learning & LLM", 115,
                "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.",
                0xFF7B1FA2
            ),
            Community(
                "mobile", "Mobile Dev Community", "Mobile Dev Community", "Native Jetpack Compose & iOS", 95,
                "Belajar bareng membuat aplikasi mobile native, mulai dari desain UI sampai rilis ke Play Store.",
                0xFF00897B, isJoined = true
            ),
            Community(
                "data", "Data Science Club", "Data Science Club", "Data Analytics & Visualization", 74,
                "Mengolah data kampus menjadi insight lewat Python, dashboard interaktif, dan statistik terapan.",
                0xFFEF6C00
            ),
            Community(
                "uiux", "UI/UX Collective", "UI/UX Collective", "Product Design & Research", 63,
                "Diskusi user research, wireframing, prototyping di Figma, dan usability testing.",
                0xFFC2185B
            ),
            Community(
                "game", "Game Dev Studio", "Game Dev Studio", "Unity & Godot", 51,
                "Membuat game 2D dan 3D bersama, ikut game jam, dan belajar desain level.",
                0xFF388E3C
            )
        )
    )
    val communities: StateFlow<List<Community>> = _communities.asStateFlow()

    private val _projects = MutableStateFlow(
        listOf(
            Project(
                "proj-gdsc-01", "gdsc", "Smart Campus Navigation",
                "Aplikasi peta interaktif gedung kampus berbasis Jetpack Compose dan Google Maps SDK. Membantu mahasiswa dan dosen menemukan ruang kelas, laboratorium, perpustakaan, dan fasilitas lain lengkap dengan rute tercepat.",
                listOf("Kotlin", "Jetpack Compose", "Room", "Google Maps SDK")
            ),
            Project(
                "proj-gdsc-02", "gdsc", "Student Attendance QR Scanner",
                "Pemindai QR untuk presensi kehadiran workshop. Data kehadiran langsung tersimpan di Firebase.",
                listOf("Jetpack Compose", "Firebase", "CameraX"),
                isCompleted = true
            ),
            Project(
                "proj-cyber-03", "cyber", "Campus WiFi Audit Tool",
                "Script untuk memetakan perangkat di jaringan lab dan mendeteksi port yang terbuka.",
                listOf("Python")
            ),
            Project(
                "proj-mobile-04", "mobile", "Kantin Pre-Order App",
                "Aplikasi pesan makanan kantin sebelum jam istirahat supaya tidak perlu antre lama.",
                listOf("Kotlin", "Jetpack Compose", "Firebase")
            )
        )
    )
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private var nextNumber = _projects.value.size + 1

    fun isJoined(communityId: String): Boolean =
        _communities.value.any { it.id == communityId && it.isJoined }

    fun toggleJoin(communityId: String) {
        _communities.update { list ->
            list.map { if (it.id == communityId) it.copy(isJoined = !it.isJoined) else it }
        }
    }

    fun generateProjectId(communityId: String): String {
        val id = "proj-$communityId-${nextNumber.toString().padStart(2, '0')}"
        nextNumber++
        return id
    }

    fun addProject(project: Project) {
        _projects.update { it + project }
    }

    fun deleteProject(projectId: String) {
        _projects.update { list -> list.filterNot { it.id == projectId } }
    }

    fun toggleProjectStatus(projectId: String) {
        _projects.update { list ->
            list.map { if (it.id == projectId) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }
}