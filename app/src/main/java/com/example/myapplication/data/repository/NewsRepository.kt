package com.example.myapplication.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myapplication.data.api.NewsAPI
import com.example.myapplication.data.models.Article
import com.example.myapplication.data.models.NewsResponse
import com.example.myapplication.db.ArticleDatabase
import io.reactivex.Completable
import io.reactivex.disposables.Disposable

class NewsRepository(private val apiService: NewsAPI) {

    lateinit var newsNetworkDataSource: NewsNetworkDataSource

    fun fetchNews(disposable: Disposable): LiveData<NewsResponse> {
        newsNetworkDataSource = NewsNetworkDataSource(apiService, disposable)
        newsNetworkDataSource.searchNews()

        return newsNetworkDataSource.newsResponse
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return newsNetworkDataSource.networkState
    }

}

object DatabaseRepository {

    private lateinit var newsDatabase: ArticleDatabase

    fun buildNewsDatabase(context: Context) {
        newsDatabase =
            Room.databaseBuilder(context, ArticleDatabase::class.java, "news_database")
                .build()
    }

    fun insertNews(article: Article): Completable {
        return newsDatabase.getArticleDao().insert(article)
    }

    fun getSavedNews() = newsDatabase.getArticleDao().getAllArticles()
}