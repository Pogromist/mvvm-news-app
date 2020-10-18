package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.NewsResponse
import com.example.myapplication.repository.NewsRepository
import com.example.myapplication.util.NetworkState
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val searchNews: MutableLiveData<NetworkState<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(NetworkState.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : NetworkState<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return NetworkState.Success(resultResponse)
            }
        }
        return NetworkState.Error(response.message())
    }
}