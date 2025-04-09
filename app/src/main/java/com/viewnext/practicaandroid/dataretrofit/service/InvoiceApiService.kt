package com.viewnext.practicaandroid.dataretrofit.service

import com.viewnext.practicaandroid.domain.data.InvoiceEntity
import com.viewnext.practicaandroid.domain.data.InvoicesResponse
import retrofit2.http.GET

interface InvoiceApiService {

    @GET("facturas")
    suspend fun getInvoices(): InvoicesResponse
}