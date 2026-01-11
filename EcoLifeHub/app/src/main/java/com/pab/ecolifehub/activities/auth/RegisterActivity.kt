package com.pab.ecolifehub.activities.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.pab.ecolifehub.R
import com.pab.ecolifehub.activities.main.HomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var btnLogin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun setupListeners() {
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (validateInput(username, email, password)) {
                navigateToHome(username, email)
            }
        }

        btnLogin.setOnClickListener {
            finish() // Go back to LoginActivity
        }
    }

    private fun validateInput(username: String, email: String, password: String): Boolean {
        if (username.isEmpty()) {
            etUsername.error = getString(R.string.error_empty_field)
            etUsername.requestFocus()
            return false
        }
        if (email.isEmpty()) {
            etEmail.error = getString(R.string.error_empty_field)
            etEmail.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            etPassword.error = getString(R.string.error_empty_field)
            etPassword.requestFocus()
            return false
        }
        if (password.length < 4) {
            Toast.makeText(this, "Password minimal 4 karakter", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun navigateToHome(username: String, email: String) {
        Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
        
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("EXTRA_USERNAME", username)
            putExtra("EXTRA_EMAIL", email)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}
