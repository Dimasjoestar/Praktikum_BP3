package com.pab.modul3_activity2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //inisiasikan button ke variabel
        val btnPindah = findViewById<Button>(R.id.btn_pindah_halaman)
        //aksi ketika button diklik
        btnPindah.setOnClickListener {
            val intent = Intent(this, HalamanSelanjutnya::class.java)
            startActivity(intent)
        }

    }
}