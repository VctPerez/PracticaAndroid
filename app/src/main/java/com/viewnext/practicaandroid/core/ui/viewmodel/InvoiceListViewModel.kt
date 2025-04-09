package com.viewnext.practicaandroid.core.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viewnext.practicaandroid.core.PracticaAndroidApplication
import com.viewnext.practicaandroid.domain.data.InvoiceEntity
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InvoiceListViewModel(private val repository: InvoiceRepository) : ViewModel() {

    data class UiState(
        val invoices: List<InvoiceEntity> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val response = repository.getInvoices()
                Log.d("","Invoices: ${response.invoices}")
                _uiState.update { it.copy(invoices = response.invoices) }
            } catch (e: Exception) {
                Log.e("","Error: ${e.message}")
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PracticaAndroidApplication)
                val repository: InvoiceRepository = application.container.invoiceRepository
                InvoiceListViewModel(repository)
            }
        }
    }
}