package com.pab.modul3_activity2

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class HalamanSelanjutnya : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_selanjutnya)
//inisiasikan button ke variabel
        val btnPindah = findViewById<Button>(R.id.btn_pindah_halaman1)
////aksi ketika button diklik
        btnPindah.setOnClickListener {
            finish()
        }
    }
}
