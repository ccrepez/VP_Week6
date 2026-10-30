package com.christa.vp_week6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christa.vp_week6.data.CampusRepository
import com.christa.vp_week6.model.Community
import com.christa.vp_week6.model.Project
import com.christa.vp_week6.model.ProjectForm
import com.christa.vp_week6.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class CampusUiState(
    val student: Student,
    val communities: List<Community> = emptyList(),
    val projects: List<Project> = emptyList(),
    val form: ProjectForm = ProjectForm(),
    val searchQuery: String? = null
) {
    val joinedCommunities: List<Community>
        get() = communities.filter { it.isJoined }

    val filteredCommunities: List<Community>
        get() {
            val query = searchQuery?.trim().orEmpty()
            return if (query.isEmpty()) {
                communities
            } else {
                communities.filter {
                    it.name.contains(query, ignoreCase = true) || it.tagline.contains(query, ignoreCase = true)
                }
            }
        }
}

class CampusViewModel(
    private val campusRepository: CampusRepository = CampusRepository
) : ViewModel() {

    private val _form = MutableStateFlow(ProjectForm())
    private val _searchQuery = MutableStateFlow<String?>(null)

    val techOptions: List<String> = campusRepository.techOptions

    val uiState: StateFlow<CampusUiState> = combine(
        campusRepository.communities,
        campusRepository.projects,
        _form,
        _searchQuery
    ) { communities, projects, form, searchQuery ->
        CampusUiState(
            student = campusRepository.student,
            communities = communities,
            projects = projects,
            form = form,
            searchQuery = searchQuery
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CampusUiState(
            student = campusRepository.student,
            communities = campusRepository.communities.value,
            projects = campusRepository.projects.value
        )
    )

    fun isJoined(communityId: String): Boolean = campusRepository.isJoined(communityId)

    fun toggleJoin(communityId: String) {
        campusRepository.toggleJoin(communityId)
    }

    fun toggleSearch() {
        _searchQuery.update { if (it == null) "" else null }
    }

    fun onSearchChange(value: String) {
        _searchQuery.value = value
    }

    fun deleteProject(projectId: String) {
        campusRepository.deleteProject(projectId)
    }

    fun toggleProjectStatus(projectId: String) {
        campusRepository.toggleProjectStatus(projectId)
    }

    fun openForm() {
        _form.value = ProjectForm(isOpen = true)
    }

    fun onTitleChange(value: String) {
        _form.update { it.copy(title = value) }
    }

    fun onDescriptionChange(value: String) {
        _form.update { it.copy(description = value) }
    }

    fun toggleTech(tech: String) {
        _form.update { form ->
            val selected = form.selectedTech
            form.copy(selectedTech = if (tech in selected) selected - tech else selected + tech)
        }
    }

    fun closeForm() {
        _form.value = ProjectForm()
    }

    fun saveProject(communityId: String) {
        val form = _form.value
        if (!form.isValid) return

        campusRepository.addProject(
            Project(
                id = campusRepository.generateProjectId(communityId),
                communityId = communityId,
                title = form.title.trim(),
                description = form.description.trim(),
                techStack = techOptions.filter { it in form.selectedTech }
            )
        )
        _form.value = ProjectForm()
    }
}