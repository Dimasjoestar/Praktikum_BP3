package com.pab.ecoscanlite

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

/**
 * DetailActivity - Halaman detail informasi limbah
 * Features:
 * - Menampilkan gambar limbah
 * - Menampilkan nama limbah
 * - Menampilkan deskripsi lengkap
 * - Menampilkan tips pengelolaan ramah lingkungan
 * - Data diterima via Intent
 */
class DetailActivity : AppCompatActivity() {

    // UI Components
    private lateinit var btnBack: MaterialButton
    private lateinit var ivLimbah: ImageView
    private lateinit var tvNamaLimbah: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var tvTips: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        
        setupWindowInsets()
        initViews()
        loadDataFromIntent()
        setupClickListeners()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        ivLimbah = findViewById(R.id.ivLimbah)
        tvNamaLimbah = findViewById(R.id.tvNamaLimbah)
        tvDeskripsi = findViewById(R.id.tvDeskripsi)
        tvTips = findViewById(R.id.tvTips)
    }

    private fun loadDataFromIntent() {
        // Get data from Intent using getStringExtra and getIntExtra
        val nama = intent.getStringExtra("EXTRA_NAMA") ?: "Limbah"
        val imageResId = intent.getIntExtra("EXTRA_IMAGE_RES_ID", R.drawable.ic_plastik)
        val deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI") ?: "Tidak ada deskripsi"
        val tips = intent.getStringExtra("EXTRA_TIPS") ?: "Tidak ada tips"

        // Set data to views
        ivLimbah.setImageResource(imageResId)
        tvNamaLimbah.text = nama
        tvDeskripsi.text = deskripsi
        tvTips.text = tips
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish() // Kembali ke activity sebelumnya (Dashboard)
        }
    }
}
