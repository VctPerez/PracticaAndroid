package com.viewnext.practicaandroid.domain.data

const val DEFAULT_START_DATE = "0001-01-01"
const val DEFAULT_END_DATE = "9999-12-31"
const val DEFAULT_MIN_AMOUNT = 0f
const val DEFAULT_MAX_AMOUNT = 300f

data class InvoiceFilter(
    var startDate: String = DEFAULT_START_DATE,
    var endDate: String = DEFAULT_END_DATE,
    var minAmount: Float = DEFAULT_MIN_AMOUNT,
    var maxAmount: Float = DEFAULT_MAX_AMOUNT,
    var isPaid: Boolean = false,
    var isCancelled: Boolean = false,
    var isFixedFee: Boolean = false,
    var isPending: Boolean = false,
    var isPaymentPlan: Boolean = false
)

fun InvoiceFilter.isEmpty(): Boolean {
    return startDate == DEFAULT_START_DATE &&
            endDate == DEFAULT_END_DATE &&
            minAmount == DEFAULT_MIN_AMOUNT &&
            maxAmount == DEFAULT_MAX_AMOUNT &&
            !isPaid &&
            !isCancelled &&
            !isFixedFee &&
            !isPending &&
            !isPaymentPlan
}

fun InvoiceFilter.isDefaultStartDate(): Boolean {
    return startDate == DEFAULT_START_DATE
}

fun InvoiceFilter.isDefaultEndDate(): Boolean {
    return endDate == DEFAULT_END_DATE
}

fun InvoiceFilter.test() : Boolean {
    return minAmount == DEFAULT_MIN_AMOUNT && maxAmount == DEFAULT_MAX_AMOUNT
}
