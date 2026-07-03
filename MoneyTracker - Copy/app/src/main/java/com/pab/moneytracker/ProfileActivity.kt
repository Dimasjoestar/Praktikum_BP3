package com.pab.moneytracker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pab.moneytracker.database.DatabaseHelper
import com.pab.moneytracker.databinding.ActivityProfileBinding
import com.pab.moneytracker.utils.SessionManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        loadUserData()
        setupClickListeners()
    }

    private fun loadUserData() {
        binding.tvEmail.text = sessionManager.userEmail
        binding.etFirstName.setText(sessionManager.userFirstName)
        binding.etLastName.setText(sessionManager.userLastName)
    }

    private fun setupClickListeners() {
        binding.btnUpdate.setOnClickListener {
            updateProfile()
        }

        binding.btnDeleteData.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun updateProfile() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()

        if (firstName.isEmpty()) {
            binding.tilFirstName.error = getString(R.string.error_empty_first_name)
            return
        } else {
            binding.tilFirstName.error = null
        }

        if (lastName.isEmpty()) {
            binding.tilLastName.error = getString(R.string.error_empty_last_name)
            return
        } else {
            binding.tilLastName.error = null
        }

        val result = databaseHelper.updateUserName(sessionManager.userId, firstName, lastName)
        if (result > 0) {
            sessionManager.userFirstName = firstName
            sessionManager.userLastName = lastName
            Toast.makeText(this, getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
        }
    }

    private fun showDeleteConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete_all_data))
            .setMessage(getString(R.string.confirm_delete_data))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                deleteAllData()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun deleteAllData() {
        databaseHelper.deleteUserAndData(sessionManager.userId)
        sessionManager.clearSession()
        Toast.makeText(this, getString(R.string.data_deleted), Toast.LENGTH_SHORT).show()
        
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}
