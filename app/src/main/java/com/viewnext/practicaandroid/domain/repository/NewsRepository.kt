package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.dataretrofit.service.NewsService
import com.viewnext.practicaandroid.domain.data.NewsArticle

class NewsRepository(private val newsService: NewsService) {

    suspend fun getNews(): List<NewsArticle> {
        val response = newsService.getNews()
        return if (response.status == "ok") {
            response.articles
        } else {
            emptyList()
        }
    }
}