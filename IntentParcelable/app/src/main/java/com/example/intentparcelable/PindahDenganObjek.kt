package com.example.intentparcelable

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PindahDenganObjek : AppCompatActivity() {

    companion object {
        const val EXTRA_MAHASISWA = "extra_mahasiswa"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pindah_dengan_objek)

        val tvObjNama: TextView = findViewById(R.id.tv_object_nama)
        val tvObjNim: TextView = findViewById(R.id.tv_object_nim)
        val tvObjEmail: TextView = findViewById(R.id.tv_object_email)
        val tvObjKota: TextView = findViewById(R.id.tv_object_kota)
        val tvObjUmur: TextView = findViewById(R.id.tv_object_usia)

        // Ambil data Parcelable dari Intent
        val mahasiswa = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_MAHASISWA, Mahasiswa::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Mahasiswa>(EXTRA_MAHASISWA)
        }

        // Jika data tidak null, tampilkan di TextView
        if (mahasiswa != null) {
            tvObjNama.text = "Nama: ${mahasiswa.name}"
            tvObjNim.text = "NIM: ${mahasiswa.nim}"
            tvObjEmail.text = "Email: ${mahasiswa.email}"
            tvObjUmur.text = "Umur: ${mahasiswa.umur}"
            tvObjKota.text = "Kota: ${mahasiswa.kota}"
        }
    }
}
