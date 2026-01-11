package com.pab.tugas_m9

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// API: https://apidl.asepharyana.tech/docs/#/Search/get_api_search_mahasiswa
interface StudentApiService {
    @GET("api/search/mahasiswa")
    fun searchStudents(@Query("query") query: String): Call<List<Student>>
}

