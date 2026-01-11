package com.pab.modul9_retrofit.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("data")
    val data: NewsData = NewsData()
)

// New data class to match the API: `data` is an object that contains a `posts` array
data class NewsData(
    @SerializedName("link")
    val link: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("posts")
    val posts: List<NewsItem> = emptyList()
)

data class NewsItem(
    @SerializedName("link")
    val link: String? = "",
    @SerializedName("thumbnail")
    val image: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val contentSnippet: String? = "",
    @SerializedName("pubDate")
    val isoDate: String? = ""
)
