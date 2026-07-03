package com.pab.aplikasibersihin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pab.aplikasibersihin.ui.navigation.NavGraph
import com.pab.aplikasibersihin.ui.theme.AplikasiBersihinTheme
import com.pab.aplikasibersihin.utils.ThemePreference
import com.pab.aplikasibersihin.data.database.AppDatabase
import com.pab.aplikasibersihin.utils.NotificationHelper
import com.pab.aplikasibersihin.data.repository.LaundryRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val themePreference = remember { ThemePreference.getInstance(this@MainActivity) }
            val themeMode by themePreference.themeMode.collectAsState()

            AplikasiBersihinTheme(themeMode = themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}