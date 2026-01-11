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

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        
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
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (validateInput(username, password)) {
                navigateToHome(username)
            }
        }

        btnRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            etUsername.error = getString(R.string.error_empty_field)
            etUsername.requestFocus()
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

    private fun navigateToHome(username: String) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("EXTRA_USERNAME", username)
            putExtra("EXTRA_EMAIL", "$username@ecolife.com")
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
