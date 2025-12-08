package com.example.intenteksplisit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnPindah = findViewById<Button>(R.id.btn_pindah)
        val btnPindahData = findViewById<Button>(R.id.btn_pindah_dengan_data)

        // Pindah Activity
        btnPindah.setOnClickListener {
            val intent = Intent(this, ActivityKedua::class.java)
            startActivity(intent)
        }

        // Pindah Activity dengan data
        btnPindahData.setOnClickListener {
            val intent = Intent(this, ActivityKedua::class.java)
            intent.putExtra("Kampus", "Universitas Kuningan")
            intent.putExtra("Fakultas", "Fakultas Ilmu Komputer")
            intent.putExtra("Prodi", "Teknik Informatika")
            intent.putExtra("Matkul", "Bahasa Pemrograman 3")
            startActivity(intent)
        }
    }
}