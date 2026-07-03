package com.pab.aplikasibersihin.ui.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.viewmodel.AdminViewModel
import com.pab.aplikasibersihin.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext
import com.pab.aplikasibersihin.utils.ThemePreference
import com.pab.aplikasibersihin.utils.ThemeMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.DarkMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMainScreen(
    adminViewModel: AdminViewModel,
    authViewModel: AuthViewModel,
    onNavigateToServices: () -> Unit,
    onNavigateToPromos: () -> Unit,
    onNavigateToRewards: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val themePreference = remember { ThemePreference.getInstance(context) }
    val currentTheme by themePreference.themeMode.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Portal Admin Bersih.in", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { themePreference.toggleTheme() }) {
                        Icon(
                            imageVector = if (currentTheme == ThemeMode.LIGHT) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Ganti Tema",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(
                        onClick = {
                            authViewModel.logout()
                            onLogout()
                        }
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Keluar", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryTeal,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface, contentColor = PrimaryTeal) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                    label = { Text("Dashboard") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = PrimaryTeal.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Receipt, contentDescription = "Orders") },
                    label = { Text("Orders") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = PrimaryTeal.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.PeopleAlt, contentDescription = "Pengguna") },
                    label = { Text("Pengguna") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = PrimaryTeal.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Assessment, contentDescription = "Laporan") },
                    label = { Text("Laporan") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = PrimaryTeal.copy(alpha = 0.1f)
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> AdminDashboardScreen(
                    adminViewModel = adminViewModel,
                    onNavigateToServices = onNavigateToServices,
                    onNavigateToPromos = onNavigateToPromos,
                    onNavigateToRewards = onNavigateToRewards
                )
                1 -> ManageOrdersScreen(adminViewModel = adminViewModel)
                2 -> ManageUsersScreen(adminViewModel = adminViewModel)
                3 -> ReportsScreen(adminViewModel = adminViewModel)
            }
        }
    }
}
