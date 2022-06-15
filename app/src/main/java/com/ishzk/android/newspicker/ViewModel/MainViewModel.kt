package com.ishzk.android.newspicker.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ishzk.android.newspicker.BuildConfig
import com.ishzk.android.newspicker.Model.Article
import com.ishzk.android.newspicker.Service.NewsApiService
import com.ishzk.android.newspicker.Service.RetrofitClient
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {
    private var service: NewsApiService = RetrofitClient.getService()
    val newsList: MutableLiveData<List<Article>> by lazy { MutableLiveData<List<Article>>() }

    fun fetchNews(query: String, fromDate: Date){
        liveData<List<Article>> {
            val from = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(fromDate)
            val result = service.getNews(query, BuildConfig.apikey, from).execute().body() ?: return@liveData
            if(result.status == "ok"){
                newsList.value = result.articles
            }
        }
    }
}