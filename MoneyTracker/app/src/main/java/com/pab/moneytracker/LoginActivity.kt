package com.pab.moneytracker

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pab.moneytracker.database.DatabaseHelper
import com.pab.moneytracker.databinding.ActivityLoginBinding
import com.pab.moneytracker.model.AuthProvider
import com.pab.moneytracker.utils.SessionManager

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnGoogle.setOnClickListener {
            // Simulate Google login for demo
            simulateSocialLogin("google_user@gmail.com", "Google", "User", AuthProvider.GOOGLE)
        }

        binding.btnFacebook.setOnClickListener {
            // Simulate Facebook login for demo
            simulateSocialLogin("facebook_user@facebook.com", "Facebook", "User", AuthProvider.FACEBOOK)
        }
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Validation
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_email)
            return
        } else {
            binding.tilEmail.error = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.error_invalid_email)
            return
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_empty_password)
            return
        } else {
            binding.tilPassword.error = null
        }

        // Attempt login
        val user = databaseHelper.loginUser(email, password)
        if (user != null) {
            sessionManager.saveLoginSession(
                user.id,
                user.email,
                user.firstName,
                user.lastName,
                user.authProvider.name
            )
            navigateToMain()
        } else {
            Toast.makeText(this, getString(R.string.error_login_failed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun simulateSocialLogin(email: String, firstName: String, lastName: String, provider: AuthProvider) {
        val user = databaseHelper.loginOrCreateSocialUser(email, firstName, lastName, provider)
        if (user != null) {
            sessionManager.saveLoginSession(
                user.id,
                user.email,
                user.firstName,
                user.lastName,
                user.authProvider.name
            )
            navigateToMain()
        } else {
            Toast.makeText(this, "Social login gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}
