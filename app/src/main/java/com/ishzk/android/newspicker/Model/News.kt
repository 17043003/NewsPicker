package com.ishzk.android.newspicker.model

import java.io.Serializable
import java.util.*

data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
): Serializable

data class Article(
    val source: NewsSource,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String,
): Serializable{
    fun shortContent(): String = this.content.take(100) + "..."
}

data class NewsSource(
    val id: String?,
    val name: String,
): Serializable