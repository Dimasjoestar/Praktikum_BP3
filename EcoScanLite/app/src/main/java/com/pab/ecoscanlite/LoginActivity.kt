package com.pab.ecoscanlite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * LoginActivity - Halaman login untuk user
 * Features:
 * - Input username dan password
 * - Validasi credential hardcoded
 * - Navigasi ke DashboardActivity dengan Intent
 * - Link ke RegisterActivity
 */
class LoginActivity : AppCompatActivity() {

    // UI Components
    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton

    // Hardcoded credentials untuk demo
    private val validUsername = "admin"
    private val validPassword = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        
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
        tilPassword = findViewById(R.id.tilPassword)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun setupClickListeners() {
        // Login button click
        btnLogin.setOnClickListener {
            performLogin()
        }

        // Register link click
        findViewById<android.widget.TextView>(R.id.tvRegister).setOnClickListener {
            navigateToRegister()
        }
    }

    private fun performLogin() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Reset errors
        tilUsername.error = null
        tilPassword.error = null

        // Validate inputs
        if (username.isEmpty()) {
            tilUsername.error = getString(R.string.error_empty_username)
            return
        }

        if (password.isEmpty()) {
            tilPassword.error = getString(R.string.error_empty_password)
            return
        }

        // Check credentials (hardcoded)
        if (username == validUsername && password == validPassword) {
            // Login success
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
            navigateToDashboard(username)
        } else {
            // Login failed
            Toast.makeText(this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToDashboard(username: String) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("EXTRA_USERNAME", username)
        startActivity(intent)
        finish() // Close login activity
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
