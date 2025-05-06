package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.dataretrofit.service.UserApiService
import com.viewnext.practicaandroid.domain.data.UserDetailsResponse

interface UserRepository {
    suspend fun getUserDetails(): UserDetailsResponse
}

class DefaultUserRepository(
    private val userService: UserApiService
) : UserRepository{
    override suspend fun getUserDetails(): UserDetailsResponse {
        return userService.getUserDetails()
    }

}