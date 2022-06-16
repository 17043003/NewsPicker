package com.ishzk.android.newspicker.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var service: NewsApiService

    init {
        val gsonFactory = GsonConverterFactory.create(GsonBuilder().serializeNulls().create())

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(gsonFactory)
            .build()

        service = retrofit.create(NewsApiService::class.java)
    }

    fun getService(): NewsApiService = service
}