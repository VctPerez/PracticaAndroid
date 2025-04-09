package com.viewnext.practicaandroid.dataretrofit.service

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.viewnext.practicaandroid.domain.data.UserDetailsEntity
import retrofit2.http.GET

interface UserApiService {

    @GET("details")
    @Mock
    @MockResponse(
        body = """
            {
                "cau": "ES0021000000001994LJ1FA000",
                "requestStatus": "No hemos recibido ninguna solicitud de autoconsumo",
                "type": "Con excendentes y compensación individual - Consumo",
                "compensation": "Precio PVPC",
                "installationPower": "5kWp"
            }
        """
    )
    suspend fun getUserDetails(): UserDetailsEntity

}