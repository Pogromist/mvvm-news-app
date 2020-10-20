package com.example.myapplication.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.api.NewsAPI
import com.example.myapplication.data.models.NewsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsNetworkDataSource(
    private val apiService: NewsAPI,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse

    fun searchNews() {
        _networkState.value = NetworkState.LOADING

        compositeDisposable.add(
            apiService.searchForNews("software", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _newsResponse.value = it
                        _networkState.value = NetworkState.LOADED
                    },
                    {
                        _networkState.value = NetworkState.ERROR
                        Log.e("NewsData", it.message)
                    }
                )
        )
    }
}