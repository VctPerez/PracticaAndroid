package com.viewnext.practicaandroid.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import com.viewnext.practicaandroid.domain.parseDateToYYYYMMDD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InvoiceFilterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InvoiceFilter())
    val uiState: StateFlow<InvoiceFilter> get() = _uiState.asStateFlow()

    fun setFilter(invoiceFilter: InvoiceFilter){
        _uiState.value = invoiceFilter
    }

    fun clearFilters() {
        _uiState.value = InvoiceFilter()
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                InvoiceFilterViewModel()
            }
        }
    }
}
