package com.pab.aplikasibersihin.ui.officer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.viewmodel.AuthViewModel
import com.pab.aplikasibersihin.viewmodel.OfficerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficerMainScreen(
    officer: UserEntity,
    officerViewModel: OfficerViewModel,
    authViewModel: AuthViewModel,
    onNavigateToPickupDetail: (Long) -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Portal Petugas Bersih.in", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(
                        onClick = {
                            authViewModel.logout()
                            onLogout()
                        }
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Keluar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryTeal,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            OfficerDashboardScreen(
                officer = officer,
                officerViewModel = officerViewModel,
                onNavigateToPickupDetail = onNavigateToPickupDetail
            )
        }
    }
}
