package com.viewnext.practicaandroid.domain.repository

import com.viewnext.practicaandroid.dataretrofit.service.NewsService
import com.viewnext.practicaandroid.domain.data.NewsArticle
import com.viewnext.practicaandroid.domain.data.NewsResponse
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any

class NewsRepositoryTest {
    @Test
    fun getNews_whenApiDoesntReturnOk_shouldReturnArticles() {
        runTest {
            // Arrange
            val mockNewsService = mock(NewsService::class.java)
            `when`(mockNewsService.getNews(any())).thenReturn(NewsResponse(
                "error",
                totalResults = 0,
                articles = emptyList()
            ))
            val newsRepository = NewsRepository(mockNewsService)

            // Act
            val result = newsRepository.getNews()

            // Assert
            assertEquals(0, result.size)
        }
    }

    @Test
    fun getNews_whenApiReturnsOk_shouldReturnEmptyList() {
        runTest {
            // Arrange
            val responseList = listOf(
                NewsArticle(
                    NewsArticle.Source("1", "source"),
                    "description",
                    "url",
                    "urlToImage",
                    "publishedAt",
                    "source",
                    publishedAt = "publishedAt",
                    content = "content",
                )
            )
            val mockNewsService = mock(NewsService::class.java)
            `when`(mockNewsService.getNews(any())).thenReturn(NewsResponse(
                "ok",
                totalResults = responseList.size,
                articles = responseList
            ))
            val newsRepository = NewsRepository(mockNewsService)

            // Act
            val result = newsRepository.getNews()

            // Assert
            assertEquals(responseList, result)
        }
    }
}