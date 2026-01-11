package com.pab.ecolifehub.activities.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.pab.ecolifehub.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var switchDarkMode: SwitchMaterial
    private lateinit var switchNotifications: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        switchNotifications = findViewById(R.id.switchNotifications)
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        // Dummy switches - just show a toast
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Dark Mode diaktifkan" else "Dark Mode dinonaktifkan"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Notifikasi diaktifkan" else "Notifikasi dinonaktifkan"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
