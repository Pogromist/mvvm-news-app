package com.example.myapplication.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)