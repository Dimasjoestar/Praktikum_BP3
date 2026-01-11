package com.pab.ecoscanlite

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * RegisterActivity - Halaman registrasi untuk user baru
 * Features:
 * - Input username, email, dan password
 * - Validasi semua field
 * - Setelah register berhasil, navigasi ke DashboardActivity
 * - Link kembali ke LoginActivity
 */
class RegisterActivity : AppCompatActivity() {

    // UI Components
    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        
        setupWindowInsets()
        initViews()
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
        tilUsername = findViewById(R.id.tilUsername)
        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
    }

    private fun setupClickListeners() {
        // Register button click
        btnRegister.setOnClickListener {
            performRegister()
        }

        // Login link click
        findViewById<android.widget.TextView>(R.id.tvLogin).setOnClickListener {
            navigateToLogin()
        }
    }

    private fun performRegister() {
        val username = etUsername.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Reset errors
        tilUsername.error = null
        tilEmail.error = null
        tilPassword.error = null

        // Validate username
        if (username.isEmpty()) {
            tilUsername.error = getString(R.string.error_empty_username)
            return
        }

        // Validate email
        if (email.isEmpty()) {
            tilEmail.error = getString(R.string.error_empty_email)
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = getString(R.string.error_invalid_email)
            return
        }

        // Validate password
        if (password.isEmpty()) {
            tilPassword.error = getString(R.string.error_empty_password)
            return
        }

        if (password.length < 6) {
            tilPassword.error = "Password minimal 6 karakter"
            return
        }

        // Registration success (tanpa database, langsung navigasi)
        Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
        navigateToDashboard(username)
    }

    private fun navigateToDashboard(username: String) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("EXTRA_USERNAME", username)
        // Clear back stack
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        finish() // Kembali ke LoginActivity
    }
}
