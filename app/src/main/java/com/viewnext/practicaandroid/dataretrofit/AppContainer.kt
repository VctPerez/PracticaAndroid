package com.viewnext.practicaandroid.dataretrofit

import android.content.Context
import co.infinum.retromock.Retromock
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.viewnext.practicaandroid.core.db.InvoiceDatabase
import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import com.viewnext.practicaandroid.dataretrofit.service.NewsService
import com.viewnext.practicaandroid.dataretrofit.service.UserApiService
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import com.viewnext.practicaandroid.domain.repository.InvoiceRepositoryWrapper
import com.viewnext.practicaandroid.domain.repository.DefaultUserRepository
import com.viewnext.practicaandroid.domain.repository.NetworkInvoiceRepository
import com.viewnext.practicaandroid.domain.repository.NewsRepository
import com.viewnext.practicaandroid.domain.repository.OfflineInvoiceRepository
import com.viewnext.practicaandroid.domain.repository.UserRepository
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val invoiceRepository: InvoiceRepository
    val userRepository: UserRepository
    val newsRepository: NewsRepository
}

class DefaultAppContainer(private val context : Context) : AppContainer{
    private val invoicesBaseUrl = "https://b1676490-a05a-4532-8f47-b3c9c36f7854.mock.pstmn.io"

    companion object{
        private var useRetromock = false
        private var _invoiceRepository: InvoiceRepository? = null

        fun toggleMocking() {
            useRetromock = !useRetromock
            _invoiceRepository = null

        }
        fun isMocking() : Boolean {
            return useRetromock
        }
    }

    private val invoicesRetrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(invoicesBaseUrl)
        .build()

    private val retromock = Retromock.Builder().retrofit(invoicesRetrofit)
        .defaultBodyFactory(context.assets::open).build()

    private val newsRetrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://newsapi.org/v2/")
        .build()

    private val invoiceApiService: InvoiceApiService
        get() = if (useRetromock) {
            retromock.create(InvoiceApiService::class.java)
        } else {
            invoicesRetrofit.create(InvoiceApiService::class.java)
        }


    /**
     *  * Lazy initialization of the invoice repository. If the repository is already created,
     *  it will return the existing instance. Else, it will create a new instance of the repository
     *  using using the selected ApiService (retrofit or retromock).
     */
    override val invoiceRepository: InvoiceRepository
        get() = _invoiceRepository ?: InvoiceRepositoryWrapper(
            OfflineInvoiceRepository(InvoiceDatabase.getDatabase(context).invoiceDao()),
            NetworkInvoiceRepository(invoiceApiService)
        ).also {
            _invoiceRepository = it
        }

    private val userApiService: UserApiService by lazy {
        retromock.create(UserApiService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        DefaultUserRepository(userApiService)
    }

    private val newsApiService: NewsService by lazy {
        newsRetrofit.create(NewsService::class.java)
    }

    override val newsRepository: NewsRepository by lazy {
        NewsRepository(newsApiService)
    }
}