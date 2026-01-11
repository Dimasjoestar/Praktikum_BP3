package com.pab.modul7

import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KAMPUS = "extra_kampus"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imgDetailPhoto: ImageView = findViewById(R.id.img_detail_photo)
        val tvDetailName: TextView = findViewById(R.id.tv_detail_name)
        val tvDetailLocation: TextView = findViewById(R.id.tv_detail_location)
        val tvDetailDescription: TextView = findViewById(R.id.tv_detail_description)

        tvDetailDescription.movementMethod = ScrollingMovementMethod()

        val kampus = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_KAMPUS, Kampus::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_KAMPUS)
        }

        if (kampus != null) {
            imgDetailPhoto.setImageResource(kampus.photo)
            tvDetailName.text = kampus.name
            tvDetailLocation.text = kampus.lokasi
            tvDetailDescription.text = kampus.sejarah
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}