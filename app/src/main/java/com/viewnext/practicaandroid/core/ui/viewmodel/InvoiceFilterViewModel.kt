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

    fun setStartDate(date: String) {
        _uiState.value = _uiState.value.copy(startDate = parseDateToYYYYMMDD(date))
    }
    fun setEndDate(date: String) {
        _uiState.value = _uiState.value.copy(endDate = parseDateToYYYYMMDD(date))
    }
    fun setMinAmount(amount: Float) {
        _uiState.value = _uiState.value.copy(minAmount = amount)
    }
    fun setMaxAmount(amount: Float) {
        _uiState.value = _uiState.value.copy(maxAmount = amount)
    }
    fun setIsPaid(isPaid: Boolean) {
        _uiState.value = _uiState.value.copy(isPaid = isPaid)
    }
    fun setIsCancelled(isCancelled: Boolean) {
        _uiState.value = _uiState.value.copy(isCancelled = isCancelled)
    }
    fun setIsFixedFee(isFixedFee: Boolean) {
        _uiState.value = _uiState.value.copy(isFixedFee = isFixedFee)
    }
    fun setIsPending(isPending: Boolean) {
        _uiState.value = _uiState.value.copy(isPending = isPending)
    }
    fun setIsPaymentPlan(isPaymentPlan: Boolean) {
        _uiState.value = _uiState.value.copy(isPaymentPlan = isPaymentPlan)
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
