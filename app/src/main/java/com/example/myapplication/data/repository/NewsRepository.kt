package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.api.NewsAPI
import com.example.myapplication.data.models.NewsResponse
import io.reactivex.disposables.CompositeDisposable

class NewsRepository(private val apiService: NewsAPI) {

    lateinit var newsNetworkDataSource: NewsNetworkDataSource

    fun fetchNews(compositeDisposable: CompositeDisposable) : LiveData<NewsResponse> {
        newsNetworkDataSource = NewsNetworkDataSource(apiService, compositeDisposable)
        newsNetworkDataSource.searchNews()

        return newsNetworkDataSource.newsResponse
    }

    fun getNetworkState() : LiveData<NetworkState> {
        return newsNetworkDataSource.networkState
    }

}