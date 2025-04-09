package com.viewnext.practicaandroid.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoicesResponse(
    @SerialName("numFacturas")
    val total: Int,

    @SerialName("facturas")
    val invoices: List<InvoiceEntity>
)
