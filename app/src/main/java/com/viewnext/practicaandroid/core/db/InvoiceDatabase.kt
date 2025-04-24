package com.viewnext.practicaandroid.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.viewnext.practicaandroid.core.db.dao.InvoiceDao
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity

@Database(entities = [InvoiceEntity::class], version = 1, exportSchema = false)
abstract class InvoiceDatabase : RoomDatabase() {
    abstract fun invoiceDao(): InvoiceDao

    companion object{
        @Volatile
        private var INSTANCE: InvoiceDatabase? = null

        fun getDatabase(context: Context) : InvoiceDatabase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    InvoiceDatabase::class.java,
                    "invoice_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}