package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.dataretrofit.service.InvoiceApiService
import com.viewnext.practicaandroid.dataretrofit.service.UserApiService
import com.viewnext.practicaandroid.domain.data.UserDetailsEntity

interface UserRepository {
    suspend fun getUserDetails(): UserDetailsEntity
}

class MockUserRepository(
    private val userService: UserApiService
) : UserRepository{
    override suspend fun getUserDetails(): UserDetailsEntity {
        return userService.getUserDetails()
    }

}