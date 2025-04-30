package com.viewnext.practicaandroid.core.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viewnext.practicaandroid.core.PracticaAndroidApplication
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import com.viewnext.practicaandroid.domain.data.InvoicesResponse
import com.viewnext.practicaandroid.domain.data.isEmpty
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InvoiceListViewModel(
    private val repository: InvoiceRepository) : ViewModel()
{
    data class UiState(
        val invoices: List<InvoiceEntity> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    init {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true) }
//            try {
//                val response = repository.getInvoices()
//                Log.d("init","Invoices: ${response.invoices}")
//                _uiState.update { it.copy(invoices = response.invoices) }
//            } catch (e: Exception) {
//                Log.e("init","Error: ${e.message}")
//                _uiState.update { it.copy(error = e.message) }
//            }
//        }
    }

    fun refreshInvoices(filter : InvoiceFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val response : InvoicesResponse
                if(filter.isEmpty()) {
//                    Log.d("Filter","No filter applied")
                     response = repository.getInvoices()
                } else {
//                    Log.d("Filter","Filter applied $filter")
                    response = repository.getInvoicesWithFilter(filter)
                }
//                val response = repository.getInvoices()
                _uiState.update {
                    it.copy(invoices = response.invoices, isLoading = false)
                }
//                Log.d("tamaño - refresh", _uiState.value.invoices.size.toString())
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    companion object{

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PracticaAndroidApplication)
                val repository: InvoiceRepository = application.container.invoiceRepository
                InvoiceListViewModel(repository)
            }
        }
    }
}