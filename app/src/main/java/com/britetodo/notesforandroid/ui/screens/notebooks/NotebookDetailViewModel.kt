package com.britetodo.notesforandroid.ui.screens.notebooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.britetodo.notesforandroid.data.models.Notebook
import com.britetodo.notesforandroid.data.models.Page
import com.britetodo.notesforandroid.data.repository.NotebookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotebookDetailViewModel @Inject constructor(
    private val repository: NotebookRepository,
) : ViewModel() {

    private val notebookId = MutableStateFlow<String?>(null)

    val notebook: StateFlow<Notebook?> = notebookId
        .filterNotNull()
        .flatMapLatest { id -> repository.observeNotebook(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val pages: StateFlow<List<Page>> = notebookId
        .filterNotNull()
        .flatMapLatest { id -> repository.observePages(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun init(id: String) {
        notebookId.value = id
    }

    fun addPage() = viewModelScope.launch {
        val id = notebookId.value ?: return@launch
        val nb = repository.getNotebook(id) ?: return@launch
        repository.createPage(id, nb.templateId)
    }

    fun deletePage(pageId: String) = viewModelScope.launch {
        val id = notebookId.value ?: return@launch
        repository.deletePage(pageId, id)
    }
}
