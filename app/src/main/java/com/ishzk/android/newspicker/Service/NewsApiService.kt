package com.ishzk.android.newspicker.service

import com.ishzk.android.newspicker.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun getNews(@Query("q")query: String, @Query("apikey")apikey: String, @Query("from")from: String): Call<News>
}