package com.viewnext.practicaandroid.domain.data

import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoicesResponse(
    @SerialName("numFacturas")
    val total: Int,

    @SerialName("facturas")
    val invoices: List<InvoiceEntity>
)
