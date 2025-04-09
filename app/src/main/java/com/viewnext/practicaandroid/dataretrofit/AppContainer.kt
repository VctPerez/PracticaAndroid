package com.viewnext.practicaandroid.dataretrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import com.viewnext.practicaandroid.domain.repository.NetworkInvoiceRepository
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val invoiceRepository: InvoiceRepository
}

class DefaultAppContainer : AppContainer{
    private val invoicesBaseUrl = "https://b1676490-a05a-4532-8f47-b3c9c36f7854.mock.pstmn.io"

    private val retrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(invoicesBaseUrl)
        .build()

    private val invoiceApiService: InvoiceApiService by lazy {
        retrofit.create(InvoiceApiService::class.java)
    }

    override val invoiceRepository: InvoiceRepository by lazy {
        NetworkInvoiceRepository(invoiceApiService)
    }
}