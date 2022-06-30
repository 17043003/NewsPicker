package com.ishzk.android.newspicker.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.hadilq.liveevent.LiveEventConfig
import com.ishzk.android.newspicker.BuildConfig
import com.ishzk.android.newspicker.model.Article
import com.ishzk.android.newspicker.service.NewsApiService
import com.ishzk.android.newspicker.service.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainViewModel"

class MainViewModel: ViewModel() {
    private var service: NewsApiService = RetrofitClient.getService()
    val newsList: MutableLiveData<List<Article>> by lazy { MutableLiveData<List<Article>>() }
    val onTransit = LiveEvent<Uri>(config = LiveEventConfig.Normal)

    fun fetchNews(query: String, fromDate: Date){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val from = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).format(fromDate)
                val result = service.getNews(query, BuildConfig.apikey, from).execute().body()
                    ?: return@withContext
                if (result.status == "ok") {
                    newsList.postValue(result.articles)
                    Log.v(TAG, "total articles:${result.totalResults}")
                }
            }
        }
    }

    fun onClick(item: Article){
        Log.v(TAG, "clicked item: ${item.url}")
        val webpage: Uri = Uri.parse(item.url)

        onTransit.value = webpage
    }
}