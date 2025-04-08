package com.viewnext.practicaandroid.core.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viewnext.practicaandroid.domain.data.InvoiceEntity
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import kotlinx.coroutines.launch

class InvoiceListViewModel(private val repository: InvoiceRepository) : ViewModel() {
    private val _invoices = mutableStateOf<List<InvoiceEntity>>(emptyList())
    val invoices: List<InvoiceEntity> get() = _invoices.value

    init {
        viewModelScope.launch{
            _invoices.value = repository.getInvoices()
        }
    }
}