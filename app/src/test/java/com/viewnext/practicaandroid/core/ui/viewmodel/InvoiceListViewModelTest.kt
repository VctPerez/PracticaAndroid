package com.viewnext.practicaandroid.core.ui.viewmodel

import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import com.viewnext.practicaandroid.domain.data.InvoicesResponse
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class InvoiceListViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshInvoices_withNoExceptionsAndEmptyFilter() {
        runTest {
            // Arrange
            val invoiceRepositoryMock = mock(InvoiceRepository::class.java)
            val filter = InvoiceFilter()
            val response = InvoicesResponse(1, listOf(InvoiceEntity("", 0.0, "")))
            `when`(invoiceRepositoryMock.getInvoices()).thenReturn(response)

            val viewModel = InvoiceListViewModel(invoiceRepositoryMock)


            // Act
            Dispatchers.setMain(dispatcher)
            viewModel.refreshInvoices(filter)
            dispatcher.scheduler.advanceUntilIdle()
            Dispatchers.resetMain()

            // Assert
            val uiState = viewModel.uiState.value
            assertEquals(response.invoices, uiState.invoices)
            assertEquals(false, uiState.isLoading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshInvoices_withNoExceptionsAndNonEmptyFilter() {
        runTest {
            // Arrange
            val invoiceRepositoryMock = mock(InvoiceRepository::class.java)
            val filter = InvoiceFilter(minAmount = 1f)
            val response = InvoicesResponse(1, listOf(InvoiceEntity("", 1.0, "")))
            `when`(invoiceRepositoryMock.getInvoicesWithFilter(filter)).thenReturn(response)


            val viewModel = InvoiceListViewModel(invoiceRepositoryMock)


            // Act
            Dispatchers.setMain(dispatcher)
            viewModel.refreshInvoices(filter)
            dispatcher.scheduler.advanceUntilIdle()
            Dispatchers.resetMain()

            // Assert
            val uiState = viewModel.uiState.value
            assertEquals(response.invoices, uiState.invoices)
            assertEquals(false, uiState.isLoading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshInvoices_withException() {
        runTest {
            // Arrange
            val invoiceRepositoryMock = mock(InvoiceRepository::class.java)
            val filter = InvoiceFilter()
            `when`(invoiceRepositoryMock.getInvoices()).thenThrow(RuntimeException("Error"))


            val viewModel = InvoiceListViewModel(invoiceRepositoryMock)


            // Act
            Dispatchers.setMain(dispatcher)
            viewModel.refreshInvoices(filter)
            dispatcher.scheduler.advanceUntilIdle()
            Dispatchers.resetMain()

            // Assert
            val uiState = viewModel.uiState.value
            assertEquals("Error", uiState.error)
            assertEquals(emptyList<InvoiceEntity>(), uiState.invoices)
            assertEquals(false, uiState.isLoading)
        }
    }
}