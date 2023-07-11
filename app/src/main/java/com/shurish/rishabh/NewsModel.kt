package com.shurish.rishabh

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)