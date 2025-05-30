package com.viewnext.practicaandroid.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    suspend fun insertInvoice(invoice: InvoiceEntity)

    @Delete
    suspend fun deleteInvoice(invoice: InvoiceEntity)

    @Query("DELETE FROM invoices")
    suspend fun deleteAllInvoices()

    @Query("SELECT * FROM invoices")
    fun getAllInvoices(): Flow<List<InvoiceEntity>>


    @Query("SELECT * FROM invoices WHERE date BETWEEN :startDate AND :endDate " +
            "AND amount BETWEEN :minAmount AND :maxAmount " +
            "AND ((:isPaid AND status = 'Pagada' OR  " +
            "       :isCancelled AND status = 'Cancelada' OR " +
            "       :isFixedFee AND status = 'Cuota Fija' OR" +
            "       :isPending AND status = 'Pendiente de pago' OR " +
            "       :isPaymentPlan AND status = 'Plan de Pago') " +
            "    OR " +
            "       (NOT :isPaid AND  NOT :isCancelled AND NOT :isFixedFee " +
            "       AND NOT :isPending AND NOT :isPaymentPlan))")
    fun getInvoices(minAmount: Float, maxAmount : Float,
                       startDate : String, endDate : String,
                       isPaid : Boolean, isCancelled : Boolean,
                       isFixedFee : Boolean, isPending : Boolean,
                       isPaymentPlan : Boolean): Flow<List<InvoiceEntity>>

}