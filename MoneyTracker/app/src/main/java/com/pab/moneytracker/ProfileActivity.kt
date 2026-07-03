package com.pab.moneytracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pab.moneytracker.database.DatabaseHelper
import com.pab.moneytracker.databinding.ActivityProfileBinding
import com.pab.moneytracker.utils.SessionManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                // Some providers don't support persistable permissions
            }
            
            sessionManager.profilePhotoUri = it.toString()
            loadProfilePhoto()
            Toast.makeText(this, getString(R.string.photo_updated), Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        loadUserData()
        loadProfilePhoto()
        setupClickListeners()
        setupAutoSave()
    }

    private fun loadUserData() {
        binding.tvEmail.text = sessionManager.userEmail
        binding.tvUserName.text = "${sessionManager.userFirstName} ${sessionManager.userLastName}"
        binding.etFirstName.setText(sessionManager.userFirstName)
        binding.etLastName.setText(sessionManager.userLastName)
    }

    private fun loadProfilePhoto() {
        val photoUri = sessionManager.profilePhotoUri
        if (!photoUri.isNullOrEmpty()) {
            try {
                binding.ivProfilePhoto.setImageURI(Uri.parse(photoUri))
            } catch (e: Exception) {
                binding.ivProfilePhoto.setImageResource(R.drawable.ic_default_avatar)
            }
        } else {
            binding.ivProfilePhoto.setImageResource(R.drawable.ic_default_avatar)
        }
    }

    private fun setupClickListeners() {
        binding.btnChangePhoto.setOnClickListener {
            openGallery()
        }

        binding.ivProfilePhoto.setOnClickListener {
            openGallery()
        }

        binding.btnDeletePhoto.setOnClickListener {
            deleteProfilePhoto()
        }

        binding.btnDeleteData.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    // Auto-save when text changes (removed "Perbarui Profil" button)
    private fun setupAutoSave() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                saveProfile()
            }
        }

        binding.etFirstName.addTextChangedListener(textWatcher)
        binding.etLastName.addTextChangedListener(textWatcher)
    }

    private fun saveProfile() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()

        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            val result = databaseHelper.updateUserName(sessionManager.userId, firstName, lastName)
            if (result > 0) {
                sessionManager.userFirstName = firstName
                sessionManager.userLastName = lastName
                binding.tvUserName.text = "$firstName $lastName"
                setResult(RESULT_OK)
            }
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun deleteProfilePhoto() {
        if (sessionManager.profilePhotoUri.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.no_photo_to_delete), Toast.LENGTH_SHORT).show()
            return
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete_photo))
            .setMessage("Apakah Anda yakin ingin menghapus foto profil?")
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                sessionManager.profilePhotoUri = null
                binding.ivProfilePhoto.setImageResource(R.drawable.ic_default_avatar)
                Toast.makeText(this, getString(R.string.photo_deleted), Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
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
