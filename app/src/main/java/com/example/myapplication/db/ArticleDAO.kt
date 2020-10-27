package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.models.Article
import io.reactivex.Completable

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (article: Article): Completable

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>
}