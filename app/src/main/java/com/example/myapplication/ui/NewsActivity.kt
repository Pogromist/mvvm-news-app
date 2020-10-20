package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.adapters.NewsAdapter
import com.example.myapplication.data.api.NewsAPI
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.models.NewsResponse
import com.example.myapplication.data.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.recyclerview_item.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var newsRepository: NewsRepository
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val apiService: NewsAPI = RetrofitInstance.api
        newsRepository = NewsRepository(apiService)

        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        viewModel.newsDetails.observe(this, Observer {
            newsAdapter.differ.submitList(it.articles)
        })

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val intent = Intent(this, DetailNewsActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            adapter = newsAdapter
        }
    }


}