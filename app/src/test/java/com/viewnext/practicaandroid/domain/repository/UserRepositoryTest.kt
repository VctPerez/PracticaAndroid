package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.dataretrofit.service.UserApiService
import com.viewnext.practicaandroid.domain.data.UserDetailsResponse
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class UserRepositoryTest {
    private val userService: UserApiService = mock(UserApiService::class.java)
    private lateinit var userRepository: UserRepository

    @Test
    fun getUserDetails_returnsUserDetails() {
        runTest {
            // Arrange
            `when`(userService.getUserDetails()).thenReturn(UserDetailsResponse())
            userRepository = DefaultUserRepository(userService)

            // Assert
            assertDoesNotThrow{
                runBlocking {
                    userRepository.getUserDetails()
                }
            }
        }

    }

}