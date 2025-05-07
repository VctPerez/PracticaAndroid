package com.viewnext.practicaandroid.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viewnext.practicaandroid.core.PracticaAndroidApplication
import com.viewnext.practicaandroid.domain.data.NewsArticle
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import com.viewnext.practicaandroid.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private  val repository: NewsRepository) : ViewModel() {

    class UiState(
        val news: List<NewsArticle> = emptyList(),
        val isLoading: Boolean = true,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _uiState.value = UiState(isLoading = true)
                val news = repository.getNews()
                _uiState.value = UiState(news = news, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = UiState(error = e.message, isLoading = false)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PracticaAndroidApplication)
                val repository: NewsRepository = application.container.newsRepository
                NewsViewModel(repository)
            }
        }
    }

}