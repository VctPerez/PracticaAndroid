package com.viewnext.practicaandroid.dataretrofit

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import co.infinum.retromock.Retromock
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import com.viewnext.practicaandroid.dataretrofit.service.UserApiService
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import com.viewnext.practicaandroid.domain.repository.MockUserRepository
import com.viewnext.practicaandroid.domain.repository.NetworkInvoiceRepository
import com.viewnext.practicaandroid.domain.repository.UserRepository
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val invoiceRepository: InvoiceRepository
    val userRepository: UserRepository
}

class DefaultAppContainer(private val context : Context) : AppContainer{
    private val invoicesBaseUrl = "https://b1676490-a05a-4532-8f47-b3c9c36f7854.mock.pstmn.io"

    private val retrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(invoicesBaseUrl)
        .build()

    private val retromock = Retromock.Builder().retrofit(retrofit)
        .defaultBodyFactory(context.assets::open).build()

    private val invoiceApiService: InvoiceApiService by lazy {
        retrofit.create(InvoiceApiService::class.java)
//        retromock.create(InvoiceApiService::class.java)
    }

    override val invoiceRepository: InvoiceRepository by lazy {
        NetworkInvoiceRepository(invoiceApiService)
    }

    private val userApiService: UserApiService by lazy {
        retromock.create(UserApiService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        MockUserRepository(userApiService)
    }
}