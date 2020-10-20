package com.example.myapplication.data.models

data class NewsResponse(
    val articles: List<Article>? = null,
    val status: String = "",
    val totalResults: Int? = null
)