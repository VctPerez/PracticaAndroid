package com.viewnext.practicaandroid.core.ui.viewmodel

import android.annotation.SuppressLint
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class InvoiceFilterViewModelTest {

    private val viewModel = InvoiceFilterViewModel()

    @Test
    fun setFilter_changesStateValue(){
        // Arrange
        val filter = InvoiceFilter(
            startDate = "2023-01-01",
            endDate = "2023-12-31",
            minAmount = 100.0f,
            maxAmount = 200.0f
        )

        // Act
        viewModel.setFilter(filter)

        // Assert
        assertEquals(viewModel.uiState.value, filter)
    }

    @Test
    fun clearFilters_resetsStateValue(){
        // Arrange
        val filter = InvoiceFilter(
            startDate = "2023-01-01",
            endDate = "2023-12-31",
            minAmount = 100.0f,
            maxAmount = 200.0f
        )
        viewModel.setFilter(filter)

        // Act
        viewModel.clearFilters()

        // Assert
        assertEquals(viewModel.uiState.value, InvoiceFilter())
    }

    @SuppressLint("CheckResult")
    @Test
    fun factory_createsViewModelInstance(){
        // Arrange
        val factoryMock = mock(InvoiceFilterViewModel.Factory::class.java)
        `when`(factoryMock.create(InvoiceFilterViewModel::class.java)).thenReturn(viewModel)

        // Act
        val viewModel = factoryMock.create(InvoiceFilterViewModel::class.java)

        // Assert
        assertEquals(viewModel.uiState.value, InvoiceFilter())
    }
}