package com.pab.modul3_activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat

class MainActivity : AppCompatActivity() {
    private lateinit var activity_status: TextView
    private lateinit var layout_utama: LinearLayoutCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_status = findViewById(R.id.status)
        layout_utama = findViewById(R.id.main_layout)
    }

    override fun onStart() {
        super.onStart()
        val logMessage = "onStart dipanggil"
        Log.d("MainActivity", logMessage)
        activity_status.text = "onStart Sedang Dipanggil"
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume sedang dipanggil")
        activity_status.text = "onResume Sedang Dipanggil"
        layout_utama.setBackgroundColor(Color.CYAN)
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause sedang dipanggil")
        activity_status.text = "onPause Sedang Dipanggil"
        layout_utama.setBackgroundColor(Color.GREEN)
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop sedang dipanggil")
        activity_status.text = "onStop Sedang Dipanggil"
        layout_utama.setBackgroundColor(Color.RED)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy Sedang dipanggil")
        activity_status.text = "onDestroy Sedang Dipanggil"
    }
}