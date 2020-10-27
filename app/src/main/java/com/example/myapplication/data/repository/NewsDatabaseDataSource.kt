package com.example.myapplication.data.repository

import com.example.myapplication.data.models.Article
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsDatabaseDataSource () {

    fun saveArticle(article: Article) {
        DatabaseRepository.insertNews(article)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getSavedNews() = DatabaseRepository.getSavedNews()

}