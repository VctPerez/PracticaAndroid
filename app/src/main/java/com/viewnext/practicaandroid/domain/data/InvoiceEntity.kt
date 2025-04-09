package com.viewnext.practicaandroid.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceEntity(
    @SerialName("fecha")
    val date : String,

    @SerialName("importeOrdenacion")
    val amount : Double,

    @SerialName("descEstado")
    val status : String
)