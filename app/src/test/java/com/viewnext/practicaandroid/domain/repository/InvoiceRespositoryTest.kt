package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class InvoiceRespositoryTest {

    @Nested
    inner class NetworkInvoiceRepository{

        lateinit var invoiceApiService : InvoiceApiService

        init {
            invoiceApiService = mock(InvoiceApiService::class.java)
            invoiceApiService.
        }

        @Test

    }
}