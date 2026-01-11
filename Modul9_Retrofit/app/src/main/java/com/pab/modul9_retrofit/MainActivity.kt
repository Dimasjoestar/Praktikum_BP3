package com.pab.modul9_retrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pab.modul9_retrofit.adapter.NewsAdapter
import com.pab.modul9_retrofit.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var rvNews: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var newsAdapter: NewsAdapter

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        fetchNews()
    }

    private fun initViews() {
        rvNews = findViewById(R.id.rvNews)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(emptyList())
        rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsAdapter
        }
    }

    private fun fetchNews() {
        progressBar.visibility = View.VISIBLE
        rvNews.visibility = View.GONE

        Log.d(TAG, "fetchNews: Starting to fetch news...")

        lifecycleScope.launch {
            try {
                Log.d(TAG, "fetchNews: Calling API...")
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.getNews()
                }
                Log.d(TAG, "fetchNews: Response received - success: ${response.success}, total: ${response.total}")

                if (response.success) {
                    // response.data is now a NewsData object; use its `posts` list
                    val posts = response.data.posts
                    Log.d(TAG, "fetchNews: Data count: ${posts.size}")

                    // Update adapter with the posts list
                    newsAdapter.updateData(posts)

                    rvNews.visibility = View.VISIBLE
                } else {
                    Log.e(TAG, "fetchNews: API returned failure - ${response.message}")
                    Toast.makeText(this@MainActivity, "Gagal memuat berita: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "fetchNews: Exception occurred", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}