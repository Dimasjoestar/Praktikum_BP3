package com.pab.tugas_m9

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// We'll assume using a 'top-headlines' style endpoint (NewsAPI.org compatible)

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<RawArticle>?
)

data class RawArticle(
    val title: String?,
    val source: Source?,
    val urlToImage: String?
)

data class Source(val name: String?)

interface NewsApiService {
    @GET("v2/top-headlines")
    fun topHeadlines(@Query("country") country: String = "id"): Call<NewsResponse>
}

