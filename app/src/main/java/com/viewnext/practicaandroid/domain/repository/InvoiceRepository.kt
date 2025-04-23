package com.viewnext.practicaandroid.domain.repository

import android.util.Log
import com.viewnext.practicaandroid.core.db.dao.InvoiceDao
import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import com.viewnext.practicaandroid.domain.data.InvoicesResponse
import kotlinx.coroutines.flow.first

interface InvoiceRepository {
    suspend fun getInvoices(): InvoicesResponse
}

class NetworkInvoiceRepository(
    private val invoiceApiService: InvoiceApiService
) : InvoiceRepository {
    override suspend fun getInvoices(): InvoicesResponse {
        return invoiceApiService.getInvoices()
    }
}

class InvoiceRepositoryWrapper(
    private val offlineInvoiceRepository: OfflineInvoiceRepository,
    private val networkInvoiceRepository: NetworkInvoiceRepository,
    private var firstLoad : Boolean = true
) : InvoiceRepository {

    override suspend fun getInvoices(): InvoicesResponse {
        if(firstLoad) {
            Log.d("", "firstLoad")
            val invoicesResponse = networkInvoiceRepository.getInvoices()
            offlineInvoiceRepository.insertInvoices(invoicesResponse.invoices)
            firstLoad = false
            return invoicesResponse
        }else{
            Log.d("", "offline")
            return offlineInvoiceRepository.getInvoices()
        }
    }
}

class OfflineInvoiceRepository(
    private val invoiceDao: InvoiceDao
) : InvoiceRepository {
    override suspend fun getInvoices(): InvoicesResponse {
        val invoiceList = invoiceDao.getAllInvoices().first()
        return InvoicesResponse(
            invoices = invoiceList,
            total = invoiceList.size,
        )
    }

    suspend fun insertInvoices(invoices: List<InvoiceEntity>) {
        invoiceDao.deleteAllInvoices()
        for (invoice in invoices) {
            invoiceDao.insertInvoice(invoice)
        }
    }
}

class TestInvoiceRepository : InvoiceRepository {
    override suspend fun getInvoices(): InvoicesResponse{
        val invoices = listOf(
            InvoiceEntity("01/10/2023", 100.0, "Pagada"),
            InvoiceEntity("02/10/2023", 200.0, "Pendiente"),
            InvoiceEntity("03/10/2023", 300.0, "Anulado"),
            InvoiceEntity("04/10/2023", 400.0, "Pagada"),
            InvoiceEntity("05/10/2023", 500.0, "Pendiente"),
            InvoiceEntity("06/10/2023", 600.0, "Anulado"),
            InvoiceEntity("07/10/2023", 700.0, "Pagada"),
            InvoiceEntity("08/10/2023", 800.0, "Pendiente"),
            InvoiceEntity("09/10/2023", 900.0, "Anulado"),
            InvoiceEntity("10/10/2023", 1000.0, "Pagada"),
            InvoiceEntity("11/10/2023", 1100.0, "Pendiente"),
            InvoiceEntity("12/10/2023", 1200.0, "Anulado"),
        )

        return InvoicesResponse(
            total = invoices.size,
            invoices = invoices
        )
    }
}