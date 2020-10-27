package com.example.myapplication.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.api.NewsAPI
import com.example.myapplication.data.models.NewsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.util.NotificationLite.disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class NewsNetworkDataSource(
    private val apiService: NewsAPI,
    private val disposable: Disposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse>
        get() = _newsResponse

    @SuppressLint("SimpleDateFormat")
    fun searchNews() {
        _networkState.value = NetworkState.LOADING

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())

        disposable(
            apiService.searchForNews("software", 1, currentDate)
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