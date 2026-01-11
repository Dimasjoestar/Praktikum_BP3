package com.pab.tugas_m9

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://apidl.asepharyana.tech/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        val progress = findViewById<ProgressBar>(R.id.progress)
        val query = findViewById<EditText>(R.id.search_query)
        val btn = findViewById<Button>(R.id.search_btn)

        recycler.layoutManager = LinearLayoutManager(this)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(StudentApiService::class.java) as StudentApiService

        fun doSearch(q: String) {
            progress.visibility = View.VISIBLE
            api.searchStudents(q).enqueue(object : Callback<List<Student>> {
                override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                    progress.visibility = View.GONE
                    if (response.isSuccessful) {
                        val body = response.body() ?: emptyList()
                        recycler.adapter = StudentAdapter(body)
                    } else {
                        Toast.makeText(this@MainActivity, "API error: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                    progress.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Network error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            })
        }

        btn.setOnClickListener {
            val q = query.text.toString().trim()
            if (q.isEmpty()) {
                Toast.makeText(this, "Masukkan NIM atau Nama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            doSearch(q)
        }
    }
}