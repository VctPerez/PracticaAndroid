package com.viewnext.practicaandroid.dataretrofit

import android.content.Context
import com.viewnext.practicaandroid.domain.repository.DefaultUserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class DefaultAppContainerTest {

    @Test
    fun toogleMocking_changesMockState() {
        // Arrange
        val initialMockState = DefaultAppContainer.isMocking()

        // Act
        DefaultAppContainer.toggleMocking()

        // Assert
        assertNotEquals(initialMockState, DefaultAppContainer.isMocking()) {
            "Expected the mocking state to change after toggling"
        }
    }

    @Test
    fun invoiceRepository_whenToogleMocking_returnsDifferentsInstances() {
        // Arrange
        val context = mock(Context::class.java)
        `when`(context.applicationContext).thenReturn(context)
        val appContainer = DefaultAppContainer(context)

        // Act
        val firstInstance = appContainer.invoiceRepository
        DefaultAppContainer.toggleMocking()
        val secondInstance = appContainer.invoiceRepository

        // Assert
        assertNotEquals(firstInstance, secondInstance) {
            "Expected different instances of invoiceRepository after toggling mocking"
        }
    }

    @Test
    fun invoiceRepository_whenisNotTooglingMock_returnsSameInstance(){
        // Arrange
        val context = mock(Context::class.java)
        `when`(context.applicationContext).thenReturn(context)
        val appContainer = DefaultAppContainer(context)

        // Act
        val firstInstance = appContainer.invoiceRepository
        val secondInstance = appContainer.invoiceRepository

        // Assert
        assertEquals(firstInstance, secondInstance)
    }

    @Test
    fun userRepository_returnsDefaultUserRepository() {
        // Arrange
        val context = mock(Context::class.java)
        `when`(context.applicationContext).thenReturn(context)
        val appContainer = DefaultAppContainer(context)

        // Act
        val userRepository = appContainer.userRepository

        // Assert
        assert(userRepository is DefaultUserRepository) {
            "Expected userRepository to be an instance of DefaultUserRepository"
        }
    }
}