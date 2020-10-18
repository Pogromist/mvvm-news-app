package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.adapters.NewsAdapter
import com.example.myapplication.db.ArticleDatabase
import com.example.myapplication.repository.NewsRepository
import com.example.myapplication.util.NetworkState
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    val TAG = "NewsActivity"
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val intent = Intent(this, DetailNewsActivity::class.java)
            this.startActivity(intent)
        }

        observeLiveData()
        viewModel.searchNews("software") // onStart LiveData
    }

    private fun observeLiveData() {
        viewModel.searchNews.observe(this, Observer { it ->
            when(it) {
                is NetworkState.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is NetworkState.Error -> {
                    hideProgressBar()
                    it.message?.let {
                        Log.e(TAG, "An error occurred" )
                    }
                }
                is NetworkState.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            adapter = newsAdapter
        }
    }
}