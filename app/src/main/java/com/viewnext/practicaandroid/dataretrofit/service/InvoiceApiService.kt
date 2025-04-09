package com.viewnext.practicaandroid.dataretrofit.service

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.viewnext.practicaandroid.domain.data.InvoiceEntity
import com.viewnext.practicaandroid.domain.data.InvoicesResponse
import retrofit2.http.GET

interface InvoiceApiService {

    @GET("facturas")
    @Mock
    @MockResponse(body = "facturas.json")
    suspend fun getInvoices(): InvoicesResponse
}