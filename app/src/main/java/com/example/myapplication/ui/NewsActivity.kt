package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.adapters.NewsAdapter
import com.example.myapplication.data.api.NewsAPI
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.data.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity(), SearchDialogFragment.SearchDialogListener {

    lateinit var viewModel: NewsViewModel
    lateinit var newsRepository: NewsRepository
    lateinit var newsAdapter: NewsAdapter
    lateinit var searchView: View

    @SuppressLint("SimpleDateFormat", "InflateParams")
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
        searchView = layoutInflater.inflate(R.layout.search_window, null)

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra("url", it.url)
            intent.putExtra("urlToImage", it.urlToImage)
            intent.putExtra("title", it.title)
            intent.putExtra("sourceName", it.source?.name)
            intent.putExtra("description", it.description)
            this.startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.searchBtn -> {
                openSearchFragment()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSearchFragment() {
        val searchDialogFragment = SearchDialogFragment()
        searchDialogFragment.show(supportFragmentManager, "search dialog")
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            adapter = newsAdapter
        }
    }

    override fun applyText(query: String) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show()
    }
}