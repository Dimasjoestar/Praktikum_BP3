package com.pab.modul8_navigation_drawer

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup drawer layout
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Setup drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Load default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
            R.id.nav_gallery -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, GalleryFragment())
                    .commit()
            }
            R.id.nav_slideshow -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SlideshowFragment())
                    .commit()
            }
            R.id.nav_inbox -> {
                Toast.makeText(this, "Inbox clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_unread -> {
                Toast.makeText(this, "Unread clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_send -> {
                // Navigate to SendActivity
                val intent = Intent(this, SendActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_draft -> {
                // Navigate to DraftFragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DraftFragment())
                    .commit()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}