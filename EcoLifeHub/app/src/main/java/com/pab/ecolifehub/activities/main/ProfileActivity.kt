package com.pab.ecolifehub.activities.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pab.ecolifehub.R
import com.pab.ecolifehub.utils.PreferencesHelper

class ProfileActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var ivSettings: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvChallengesCount: TextView
    private lateinit var tvHabitsCount: TextView
    private lateinit var tvDaysActive: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        loadUserData()
        loadStats()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        // Refresh stats when returning to this activity
        loadStats()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        ivSettings = findViewById(R.id.ivSettings)
        tvUsername = findViewById(R.id.tvUsername)
        tvEmail = findViewById(R.id.tvEmail)
        tvChallengesCount = findViewById(R.id.tvChallengesCount)
        tvHabitsCount = findViewById(R.id.tvHabitsCount)
        tvDaysActive = findViewById(R.id.tvDaysActive)
    }

    private fun loadUserData() {
        val username = intent.getStringExtra("EXTRA_USERNAME") ?: "User"
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: "user@ecolife.com"

        tvUsername.text = username
        tvEmail.text = email
    }

    private fun loadStats() {
        // Load stats from SharedPreferences
        val challengesCompleted = PreferencesHelper.getCompletedChallengesCount(this)
        val habitsCompleted = PreferencesHelper.getTotalHabitsCompleted(this)
        val daysActive = PreferencesHelper.getDaysActive(this)

        tvChallengesCount.text = challengesCompleted.toString()
        tvHabitsCount.text = habitsCompleted.toString()
        tvDaysActive.text = daysActive.toString()
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        ivSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}

