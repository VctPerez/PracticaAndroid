package com.viewnext.practicaandroid.domain.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status : String,
    val totalResults : Int,
    val articles : List<NewsArticle>
)