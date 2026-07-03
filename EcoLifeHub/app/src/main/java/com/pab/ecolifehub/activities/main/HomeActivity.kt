package com.pab.ecolifehub.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.adapters.MenuAdapter
import com.pab.ecolifehub.data.DataSource
import com.pab.ecolifehub.models.MenuItem
import com.pab.ecolifehub.activities.waste.WasteCategoryActivity
import com.pab.ecolifehub.activities.lifestyle.GreenTipsActivity
import com.pab.ecolifehub.activities.lifestyle.EcoChallengeActivity
import com.pab.ecolifehub.activities.lifestyle.DailyHabitActivity
import com.pab.ecolifehub.utils.PreferencesHelper

class HomeActivity : AppCompatActivity() {

    private lateinit var rvMenu: RecyclerView
    private lateinit var tvWelcome: TextView
    private lateinit var ivProfile: ImageView
    private lateinit var menuAdapter: MenuAdapter

    private var username: String = "User"
    private var email: String = "user@ecolife.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Track days active
        PreferencesHelper.checkAndUpdateDaysActive(this)

        // Get data from Intent
        username = intent.getStringExtra("EXTRA_USERNAME") ?: "User"
        email = intent.getStringExtra("EXTRA_EMAIL") ?: "user@ecolife.com"

        initViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun initViews() {
        rvMenu = findViewById(R.id.rvMenu)
        tvWelcome = findViewById(R.id.tvWelcome)
        ivProfile = findViewById(R.id.ivProfile)
        
        // Update welcome message
        tvWelcome.text = getString(R.string.welcome_user, username)
    }

    private fun setupRecyclerView() {
        val menuItems = DataSource.getMenuItems()
        
        menuAdapter = MenuAdapter(menuItems) { menuItem ->
            handleMenuClick(menuItem)
        }

        rvMenu.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = menuAdapter
        }
    }

    private fun setupListeners() {
        ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtra("EXTRA_USERNAME", username)
                putExtra("EXTRA_EMAIL", email)
            }
            startActivity(intent)
        }
    }

    private fun handleMenuClick(menuItem: MenuItem) {
        val intent = when (menuItem.id) {
            1 -> Intent(this, WasteCategoryActivity::class.java)
            2 -> Intent(this, GreenTipsActivity::class.java)
            3 -> Intent(this, EcoChallengeActivity::class.java)
            4 -> Intent(this, DailyHabitActivity::class.java)
            5 -> Intent(this, AboutActivity::class.java)
            else -> return
        }
        startActivity(intent)
    }
}
