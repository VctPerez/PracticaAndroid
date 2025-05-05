package com.viewnext.practicaandroid.core.db

import android.content.Context
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.times

class InvoiceDatabaseTest {
    @Test
    fun getDatabase_nullInstance_createNewInstance() {
        // Arrange
        val context = mock(Context::class.java)
        `when`(context.applicationContext).thenReturn(context)
        //val InvoiceDatabaseMock = mockObject(InvoiceDatabase::class.java)
        //val database = InvoiceDatabase.getDatabase(context)

        // Act
        val result = InvoiceDatabase.getDatabase(context)

        // Assert
        assertNotNull(result)
    }

    @Test
    fun getDatabase_nonNullInstance_returnSameInstance() {
        // Arrange
        val context = mock(Context::class.java)
        `when`(context.applicationContext).thenReturn(context)
        val database1 = InvoiceDatabase.getDatabase(context)

        // Act
        val database2 = InvoiceDatabase.getDatabase(context)

        // Assert
        assertNotNull(database1)
        assertNotNull(database2)
        assertEquals(database1, database2) { "Expected the same instance of InvoiceDatabase" }
        verify(context, times(1)).applicationContext // Verify that applicationContext was called only once
    }
}