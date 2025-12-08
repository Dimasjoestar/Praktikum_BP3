package com.example.intenteksplisit

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityKedua : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kedua)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengambil data dari intent
        var dataUniv = intent.getStringExtra("Kampus")
        var dataFakultas = intent.getStringExtra("Fakultas")
        var dataProdi = intent.getStringExtra("Prodi")
        var dataMatkul = intent.getStringExtra("Matkul")

        // Menampilkan data ke layout
        val univ = findViewById<TextView>(R.id.univ)
        val fakultas = findViewById<TextView>(R.id.fakultas)
        val prodi = findViewById<TextView>(R.id.prodi)
        val matkul = findViewById<TextView>(R.id.matkul)

        // Menampilkan data ke layout
        univ.text = dataUniv
        fakultas.text = dataFakultas
        prodi.text = dataProdi
        matkul.text = dataMatkul
    }
}