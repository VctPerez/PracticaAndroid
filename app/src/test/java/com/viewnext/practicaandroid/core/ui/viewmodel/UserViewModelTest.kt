package com.viewnext.practicaandroid.core.ui.viewmodel

import android.annotation.SuppressLint
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import com.viewnext.practicaandroid.domain.data.UserDetailsResponse
import com.viewnext.practicaandroid.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun initalization_withNoExceptions_test() {
        runTest {
            // Arrange
            val response = UserDetailsResponse("123123123", "OK", "Type", "Compensation", "Power")

            val userRepositoryMock = mock(UserRepository::class.java)
            `when`(userRepositoryMock.getUserDetails()).thenReturn(response)

            // Act
            Dispatchers.setMain(dispatcher)
            val userViewModel = UserViewModel(userRepositoryMock)
            dispatcher.scheduler.advanceUntilIdle()
            Dispatchers.resetMain()

            val result = userViewModel.state.value

            // Assert
            assertEquals(response, result.userDetails)
            assertEquals(false, result.loading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun initalization_withException_test() {
        runTest {

            // Arrange
            val userRepositoryMock = mock(UserRepository::class.java)
            `when`(userRepositoryMock.getUserDetails()).thenThrow(RuntimeException("Error"))

            // Act
            Dispatchers.setMain(dispatcher)
            val userViewModel = UserViewModel(userRepositoryMock)
            dispatcher.scheduler.advanceUntilIdle()
            Dispatchers.resetMain()

            val result = userViewModel.state.value

            // Assert
            assertEquals(UserDetailsResponse(), result.userDetails)
            assertEquals(false, result.loading)
            assertEquals("Error", result.error)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("CheckResult")
    @Test
    fun factory_createsViewModelInstance() {
        // Arrange
        val userRepositoryMock = mock(UserRepository::class.java)
        Dispatchers.setMain(dispatcher)
        val userViewModel = UserViewModel(userRepositoryMock)
        dispatcher.scheduler.advanceUntilIdle()
        Dispatchers.resetMain()
        val factoryMock = mock(UserViewModel.Factory::class.java)
        `when`(factoryMock.create(UserViewModel::class.java)).thenReturn(userViewModel)

        // Act

        val viewModel = factoryMock.create(UserViewModel::class.java)

        // Assert
        assertEquals(viewModel, userViewModel)
    }
}