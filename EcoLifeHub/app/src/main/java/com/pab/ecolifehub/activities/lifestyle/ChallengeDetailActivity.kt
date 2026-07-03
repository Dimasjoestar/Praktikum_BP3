package com.pab.ecolifehub.activities.lifestyle

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.pab.ecolifehub.R
import com.pab.ecolifehub.utils.PreferencesHelper

class ChallengeDetailActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var ivChallengeIcon: ImageView
    private lateinit var tvChallengeTitle: TextView
    private lateinit var tvDifficulty: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvBenefit: TextView
    private lateinit var btnStartChallenge: MaterialButton

    private var challengeId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_challenge_detail)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        loadChallengeData()
        updateButtonState()
        setupListeners()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        ivChallengeIcon = findViewById(R.id.ivChallengeIcon)
        tvChallengeTitle = findViewById(R.id.tvChallengeTitle)
        tvDifficulty = findViewById(R.id.tvDifficulty)
        tvDescription = findViewById(R.id.tvDescription)
        tvBenefit = findViewById(R.id.tvBenefit)
        btnStartChallenge = findViewById(R.id.btnStartChallenge)
    }

    private fun loadChallengeData() {
        // Get data from Intent
        challengeId = intent.getIntExtra("CHALLENGE_ID", 0)
        val title = intent.getStringExtra("CHALLENGE_TITLE") ?: "Challenge"
        val description = intent.getStringExtra("CHALLENGE_DESCRIPTION") ?: ""
        val benefit = intent.getStringExtra("CHALLENGE_BENEFIT") ?: ""
        val difficulty = intent.getStringExtra("CHALLENGE_DIFFICULTY") ?: "Menengah"
        val iconResId = intent.getIntExtra("CHALLENGE_ICON", R.drawable.ic_challenge)

        tvChallengeTitle.text = title
        tvDescription.text = description
        tvBenefit.text = benefit
        tvDifficulty.text = difficulty
        ivChallengeIcon.setImageResource(iconResId)

        // Set difficulty color
        val difficultyColor = when (difficulty) {
            "Mudah" -> getColor(R.color.success)
            "Menengah" -> getColor(R.color.warning)
            "Sulit" -> getColor(R.color.error)
            else -> getColor(R.color.text_secondary)
        }
        tvDifficulty.setTextColor(difficultyColor)
    }

    private fun updateButtonState() {
        val isCompleted = PreferencesHelper.isChallengeCompleted(this, challengeId)
        if (isCompleted) {
            btnStartChallenge.text = "✅ Sudah Selesai"
            btnStartChallenge.isEnabled = false
            btnStartChallenge.alpha = 0.7f
        } else {
            btnStartChallenge.text = "Selesaikan Challenge"
            btnStartChallenge.isEnabled = true
            btnStartChallenge.alpha = 1.0f
        }
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        btnStartChallenge.setOnClickListener {
            // Mark challenge as completed
            PreferencesHelper.addCompletedChallenge(this, challengeId)
            updateButtonState()
            Toast.makeText(this, "🎉 Selamat! Challenge berhasil diselesaikan!", Toast.LENGTH_SHORT).show()
        }
    }
}

