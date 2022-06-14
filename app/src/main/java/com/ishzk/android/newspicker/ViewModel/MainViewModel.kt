package com.ishzk.android.newspicker.ViewModel

import androidx.lifecycle.ViewModel
import com.ishzk.android.newspicker.BuildConfig
import com.ishzk.android.newspicker.Model.Article
import com.ishzk.android.newspicker.Service.NewsApiService
import com.ishzk.android.newspicker.Service.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {
    var service: NewsApiService = RetrofitClient.getService()
    lateinit var newsList: List<Article>

    fun fetchNews(query: String, fromDate: Date){
        CoroutineScope(Dispatchers.IO).launch {
            val from = SimpleDateFormat("yyyy-MM-dd").format(fromDate)
            val result = service.getNews(query, BuildConfig.apikey, from).execute().body()
            if(result != null && result.status == "ok"){
                newsList = result.articles
            }
        }
    }
}