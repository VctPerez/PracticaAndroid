package com.viewnext.practicaandroid.domain.data

import kotlinx.serialization.SerialName

data class InvoiceEntity(
    @SerialName("fecha")
    val date : String,

    @SerialName("importeOrdenacion")
    val amount : Double,

    @SerialName("descEstado")
    val status : String
)