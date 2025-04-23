package com.viewnext.practicaandroid.domain.data

data class InvoiceFilter(
    val startDate: String = "",
    val endDate: String = "",
    val minAmount: Float = 0f,
    val maxAmount: Float = 300f,
    val isPaid: Boolean = false,
    val isCancelled: Boolean = false,
    val isFixedFee: Boolean = false,
    val isPending: Boolean = false,
    val isPaymentPlan: Boolean = false
)

fun InvoiceFilter.isEmpty(): Boolean {
    return startDate.isEmpty() && endDate.isEmpty() && minAmount == 0f && maxAmount == 100f &&
            !isPaid && !isCancelled && !isFixedFee && !isPending && !isPaymentPlan
}
