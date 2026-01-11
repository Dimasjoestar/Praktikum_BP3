package com.pab.ecolifehub.activities.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pab.ecolifehub.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var ivSettings: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        loadUserData()
        setupListeners()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        ivSettings = findViewById(R.id.ivSettings)
        tvUsername = findViewById(R.id.tvUsername)
        tvEmail = findViewById(R.id.tvEmail)
    }

    private fun loadUserData() {
        val username = intent.getStringExtra("EXTRA_USERNAME") ?: "User"
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: "user@ecolife.com"

        tvUsername.text = username
        tvEmail.text = email
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        ivSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
