package com.example.myapplication.data.api

import com.example.myapplication.data.models.NewsResponse
import com.example.myapplication.util.Constants.Companion.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    /*@GET("v2/everything")
    suspend fun searchForNews (
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>*/

    @GET("v2/everything")
    fun searchForNews (
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Observable<NewsResponse>

}