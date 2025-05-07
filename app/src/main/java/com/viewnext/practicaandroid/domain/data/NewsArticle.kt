package com.viewnext.practicaandroid.domain.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsArticle(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) {
    @Serializable
    data class Source(
        val id: String?,
        val name: String
    )
}
