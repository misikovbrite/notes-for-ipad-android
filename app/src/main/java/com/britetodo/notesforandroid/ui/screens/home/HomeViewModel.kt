package com.britetodo.notesforandroid.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.britetodo.notesforandroid.data.models.Notebook
import com.britetodo.notesforandroid.data.repository.NotebookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NotebookRepository,
) : ViewModel() {

    val notebooks = repository.observeNotebooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites = repository.observeFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createNotebook(
        name: String,
        colorHex: String = "#4A90E2",
        spineColorHex: String = "#357ABD",
        iconName: String = "book",
        templateId: String = "blank",
    ) = viewModelScope.launch {
        repository.createNotebook(name, colorHex, spineColorHex, iconName, templateId)
    }

    fun deleteNotebook(id: String) = viewModelScope.launch {
        repository.deleteNotebook(id)
    }

    fun toggleFavorite(id: String, isFavorite: Boolean) = viewModelScope.launch {
        repository.toggleFavorite(id, isFavorite)
    }

    fun archiveNotebook(id: String) = viewModelScope.launch {
        repository.setArchived(id, true)
    }
}
