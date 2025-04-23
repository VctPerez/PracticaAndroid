package com.viewnext.practicaandroid.domain.data

const val DEFAULT_START_DATE = "0001-01-01"
const val DEFAULT_END_DATE = "9999-12-31"
const val DEFAULT_MIN_AMOUNT = 0f
const val DEFAULT_MAX_AMOUNT = 300f

data class InvoiceFilter(
    val startDate: String = DEFAULT_START_DATE,
    val endDate: String = DEFAULT_END_DATE,
    val minAmount: Float = DEFAULT_MIN_AMOUNT,
    val maxAmount: Float = DEFAULT_MAX_AMOUNT,
    val isPaid: Boolean = false,
    val isCancelled: Boolean = false,
    val isFixedFee: Boolean = false,
    val isPending: Boolean = false,
    val isPaymentPlan: Boolean = false
)

fun InvoiceFilter.isEmpty(): Boolean {
    return startDate == DEFAULT_START_DATE && endDate == DEFAULT_END_DATE &&
            minAmount == DEFAULT_MIN_AMOUNT && maxAmount == DEFAULT_MAX_AMOUNT &&
            !isPaid && !isCancelled && !isFixedFee && !isPending && !isPaymentPlan
}
