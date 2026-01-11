package com.pab.modul9_retrofit.network

import com.pab.modul9_retrofit.model.NewsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("cnn/terbaru")
    suspend fun getNews(): NewsResponse
}
