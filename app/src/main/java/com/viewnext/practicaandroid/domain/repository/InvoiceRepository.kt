package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.domain.data.InvoiceEntity

interface InvoiceRepository {
    suspend fun getInvoices(): List<InvoiceEntity>
}

class OfflineInvoiceRepository : InvoiceRepository {
    override suspend fun getInvoices(): List<InvoiceEntity> {
        return listOf(
            InvoiceEntity("2023-10-01", 100.0, "Pagado"),
            InvoiceEntity("2023-10-02", 200.0, "Pendiente"),
            InvoiceEntity("2023-10-03", 300.0, "Anulado"),
            InvoiceEntity("2023-10-04", 400.0, "Pagado"),
            InvoiceEntity("2023-10-05", 500.0, "Pendiente"),
            InvoiceEntity("2023-10-06", 600.0, "Anulado"),
            InvoiceEntity("2023-10-07", 700.0, "Pagado"),
            InvoiceEntity("2023-10-08", 800.0, "Pendiente"),
            InvoiceEntity("2023-10-09", 900.0, "Anulado"),
            InvoiceEntity("2023-10-10", 1000.0, "Pagado"),
            InvoiceEntity("2023-10-11", 1100.0, "Pendiente"),
            InvoiceEntity("2023-10-12", 1200.0, "Anulado"),
        )
    }
}