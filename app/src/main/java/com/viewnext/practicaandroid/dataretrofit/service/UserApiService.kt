package com.viewnext.practicaandroid.dataretrofit.service

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.viewnext.practicaandroid.domain.data.UserDetailsResponse
import retrofit2.http.GET

interface UserApiService {

    @GET("details")
    @Mock
    @MockResponse(
        body = "details.json",
    )
    suspend fun getUserDetails(): UserDetailsResponse

}