package com.pab.modul5_view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val cardProfile = findViewById<CardView>(R.id.card_profile)
        val cardInformasi = findViewById<CardView>(R.id.card_informasi)
        val cardGaleri = findViewById<CardView>(R.id.card_galeri)
        val cardWeb = findViewById<CardView>(R.id.card_web)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        cardProfile.setOnClickListener {
            Toast.makeText(this, "Menu Profil diklik", Toast.LENGTH_SHORT).show()
        }

        cardInformasi.setOnClickListener {
            Toast.makeText(this, "Menu Informasi diklik", Toast.LENGTH_SHORT).show()
        }

        cardGaleri.setOnClickListener {
            Toast.makeText(this, "Menu Galeri diklik", Toast.LENGTH_SHORT).show()
        }

        cardWeb.setOnClickListener {
            Toast.makeText(this, "Menu Web diklik", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
