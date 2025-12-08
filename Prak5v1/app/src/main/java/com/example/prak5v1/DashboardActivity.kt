package com.example.prak5v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    private lateinit var tvWelcomeMessage: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage)
        tvUserName = findViewById(R.id.tvUserName)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        btnLogout = findViewById(R.id.btnLogout)

        // Load user data
        loadUserData()

        // Logout button click
        btnLogout.setOnClickListener {
            logout()
        }

        // Handle back button press
        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(this@DashboardActivity, "Gunakan tombol Logout untuk keluar", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadUserData() {
        val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "User")
        val userEmail = sharedPref.getString("user_email", "user@example.com")

        // Set user data to TextViews
        tvWelcomeMessage.text = getString(R.string.welcome_message_with_name, userName)
        tvUserName.text = userName
        tvUserEmail.text = userEmail
    }

    private fun logout() {
        // Clear login status
        val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
        sharedPref.edit().apply {
            putBoolean("is_logged_in", false)
            apply()
        }

        Toast.makeText(this, "Logout berhasil!", Toast.LENGTH_SHORT).show()

        // Navigate back to login
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

