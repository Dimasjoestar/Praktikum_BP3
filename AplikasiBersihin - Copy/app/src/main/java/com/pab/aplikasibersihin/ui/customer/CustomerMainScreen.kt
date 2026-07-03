package com.pab.aplikasibersihin.ui.customer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.viewmodel.AuthViewModel
import com.pab.aplikasibersihin.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerMainScreen(
    user: UserEntity,
    customerViewModel: CustomerViewModel,
    authViewModel: AuthViewModel,
    onNavigateToNewOrder: () -> Unit,
    onNavigateToOrderDetail: (Long) -> Unit,
    onNavigateToReward: () -> Unit,
    onNavigateToPromo: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PrimaryTeal,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = PrimaryTeal.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.ConfirmationNumber, contentDescription = "Promo") },
                    label = { Text("Promo") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = PrimaryTeal.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("Riwayat") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryTeal,
                        selectedTextColor = PrimaryTeal,
                        indicatorColor = PrimaryTeal.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profil") },
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
                0 -> CustomerDashboardScreen(
                    user = user,
                    customerViewModel = customerViewModel,
                    onNavigateToNewOrder = onNavigateToNewOrder,
                    onNavigateToOrderDetail = onNavigateToOrderDetail,
                    onNavigateToReward = onNavigateToReward,
                    onNavigateToPromo = onNavigateToPromo
                )
                1 -> PromoScreen(
                    customerViewModel = customerViewModel,
                    onNavigateBack = { selectedTab = 0 }
                )
                2 -> OrderHistoryScreen(
                    user = user,
                    customerViewModel = customerViewModel,
                    onNavigateToOrderDetail = onNavigateToOrderDetail
                )
                3 -> ProfileScreen(
                    user = user,
                    authViewModel = authViewModel,
                    onLogout = onLogout
                )
            }
        }
    }
}
