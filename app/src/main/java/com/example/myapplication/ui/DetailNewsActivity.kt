package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.api.NewsAPI
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.repository.NetworkState
import com.example.myapplication.data.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_detail_news.*

class DetailNewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var newsRepository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val apiService: NewsAPI = RetrofitInstance.api
        newsRepository = NewsRepository(apiService)

        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        viewModel.newsDetails.observe(this, Observer {
            detailNewsTitle.text = intent.getStringExtra("title")
            detailNewsSourceName.text = intent.getStringExtra("sourceName")
            detailNewsDescription.text = intent.getStringExtra("description")
            Glide.with(this)
                .load(intent.getStringExtra("urlToImage"))
                .into(detailNewsImage)
        })

        viewModel.networkState.observe(this, Observer {
            progressbar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })
    }
}