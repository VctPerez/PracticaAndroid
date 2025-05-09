package com.viewnext.practicaandroid.dataretrofit.service

import com.viewnext.practicaandroid.domain.data.NewsResponse
import io.github.cdimascio.dotenv.dotenv
import retrofit2.http.GET
import retrofit2.http.Query

val dotenv = try{
    dotenv {
        directory = "/assets"
        filename = "env"
    }
}catch (e: Exception){
    null
}
val API_KEY: String = dotenv?.get("NEWS_API_KEY")?:""

interface NewsService {

    @GET("everything?q=iberdrola&language=es&pageSize=10")
    suspend fun getNews(@Query("apiKey") apiKey: String = API_KEY): NewsResponse
}