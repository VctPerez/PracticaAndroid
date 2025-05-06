package com.viewnext.practicaandroid.core.db.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
@Entity(tableName = "invoices")
data class InvoiceEntity(

    @SerialName("fecha")
    val date : String,

    @SerialName("importeOrdenacion")
    val amount : Double,

    @SerialName("descEstado")
    val status : String,

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
)