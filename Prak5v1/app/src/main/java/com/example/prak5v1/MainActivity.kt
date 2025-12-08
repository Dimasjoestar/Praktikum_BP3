package com.example.prak5v1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        etEmail = findViewById(R.id.editTextTextEmailAddress)
        etPassword = findViewById(R.id.editTextTextPassword4)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        // Check if user is already logged in
        checkLoginStatus()

        // Login button click
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            when {
                email.isEmpty() -> {
                    Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Verify credentials
                    val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
                    val savedEmail = sharedPref.getString("user_email", "")
                    val savedPassword = sharedPref.getString("user_password", "")
                    val isRegistered = sharedPref.getBoolean("is_registered", false)

                    if (!isRegistered) {
                        Toast.makeText(this, "Belum ada akun terdaftar. Silakan daftar terlebih dahulu!", Toast.LENGTH_LONG).show()
                    } else if (email == savedEmail && password == savedPassword) {
                        // Save login status
                        sharedPref.edit().apply {
                            putBoolean("is_logged_in", true)
                            apply()
                        }

                        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                        // Navigate to Dashboard
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish() // Close login activity
                    } else {
                        Toast.makeText(this, "Email atau password salah!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Register button click - navigate to RegisterActivity
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkLoginStatus() {
        val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            // User already logged in, go to dashboard
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}