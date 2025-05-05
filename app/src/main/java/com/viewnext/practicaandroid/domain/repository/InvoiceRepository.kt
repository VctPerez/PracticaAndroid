package com.viewnext.practicaandroid.domain.repository

import android.util.Log
import com.viewnext.practicaandroid.core.db.dao.InvoiceDao
import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import com.viewnext.practicaandroid.domain.data.InvoicesResponse
import com.viewnext.practicaandroid.domain.parseDateFromYYYYMMDD
import com.viewnext.practicaandroid.domain.parseDateToYYYYMMDD
import kotlinx.coroutines.flow.first

interface InvoiceRepository {
    suspend fun getInvoices(): InvoicesResponse
    suspend fun getInvoicesWithFilter(filter: InvoiceFilter): InvoicesResponse

    fun parseInvoicesDateFromDB(invoices: List<InvoiceEntity>): List<InvoiceEntity> {
        return invoices.map { invoice ->
            invoice.copy(date = parseDateFromYYYYMMDD(invoice.date))
        }
    }

    fun parseInvoicesDateToDB(invoices: List<InvoiceEntity>): List<InvoiceEntity> {
        return invoices.map { invoice ->
            invoice.copy(date = parseDateToYYYYMMDD(invoice.date))
        }
    }
}

class NetworkInvoiceRepository(
    private val invoiceApiService: InvoiceApiService
) : InvoiceRepository {
    override suspend fun getInvoices(): InvoicesResponse {
        return invoiceApiService.getInvoices()
    }

    override suspend fun getInvoicesWithFilter(filter: InvoiceFilter): InvoicesResponse {
        return invoiceApiService.getInvoices() // API DOES NOT SUPPORT FILTERING
    }
}

class InvoiceRepositoryWrapper(
    private val offlineInvoiceRepository: OfflineInvoiceRepository,
    private val networkInvoiceRepository: NetworkInvoiceRepository,
    private var firstLoad : Boolean = true
) : InvoiceRepository {

    override suspend fun getInvoices(): InvoicesResponse {
        if(firstLoad) {
            val invoicesResponse = networkInvoiceRepository.getInvoices()
            offlineInvoiceRepository.insertInvoices(parseInvoicesDateToDB(invoicesResponse.invoices))
            firstLoad = false
            return invoicesResponse
        }else{
            val invoicesResponse = offlineInvoiceRepository.getInvoices()
            val parseCopy = invoicesResponse.copy(
                invoices = parseInvoicesDateFromDB(invoicesResponse.invoices)
            )
            return parseCopy
        }
    }

    override suspend fun getInvoicesWithFilter(filter: InvoiceFilter) : InvoicesResponse{
        val response = offlineInvoiceRepository.getInvoicesWithFilter(filter)
        return response.copy(
            invoices = parseInvoicesDateFromDB(response.invoices)
        )
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

    override suspend fun getInvoicesWithFilter(filter: InvoiceFilter): InvoicesResponse {
        val filteredInvoices = invoiceDao.getInvoices(filter.minAmount, filter.maxAmount,
            filter.startDate, filter.endDate,
            filter.isPaid, filter.isCancelled,
            filter.isFixedFee, filter.isPending,
            filter.isPaymentPlan)
            .first()
        return InvoicesResponse(
            invoices = filteredInvoices,
            total = filteredInvoices.size,
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

    private val staticInvoicesList = listOf(
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

    override suspend fun getInvoices(): InvoicesResponse{
        return InvoicesResponse(
            total = staticInvoicesList.size,
            invoices = staticInvoicesList
        )
    }

    override suspend fun getInvoicesWithFilter(filter: InvoiceFilter): InvoicesResponse {
        return InvoicesResponse(
            total = staticInvoicesList.size,
            invoices = staticInvoicesList
        )
    }
}