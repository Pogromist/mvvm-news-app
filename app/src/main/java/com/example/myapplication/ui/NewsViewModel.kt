package com.example.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.models.NewsResponse
import com.example.myapplication.data.repository.NetworkState
import com.example.myapplication.data.repository.NewsRepository
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val newsDetails : LiveData<NewsResponse> by lazy {
        newsRepository.fetchNews(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        newsRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}