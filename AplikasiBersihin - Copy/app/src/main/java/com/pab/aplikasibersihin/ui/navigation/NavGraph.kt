package com.pab.aplikasibersihin.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pab.aplikasibersihin.data.model.UserRole
import com.pab.aplikasibersihin.ui.auth.LoginScreen
import com.pab.aplikasibersihin.ui.auth.RegisterScreen
import com.pab.aplikasibersihin.ui.customer.*
import com.pab.aplikasibersihin.ui.admin.*
import com.pab.aplikasibersihin.ui.officer.*
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.ui.theme.AccentMint
import com.pab.aplikasibersihin.viewmodel.AuthViewModel
import com.pab.aplikasibersihin.viewmodel.CustomerViewModel
import com.pab.aplikasibersihin.viewmodel.AdminViewModel
import com.pab.aplikasibersihin.viewmodel.OfficerViewModel
import kotlinx.coroutines.delay

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    customerViewModel: CustomerViewModel = viewModel(),
    adminViewModel: AdminViewModel = viewModel(),
    officerViewModel: OfficerViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        // --- Splash Screen ---
        composable(Routes.SPLASH) {
            val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
            val currentRole by authViewModel.currentUserRole.collectAsState()

            var startAnimation by remember { mutableStateOf(false) }
            val alphaAnim by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 1000)
            )
            val scaleAnim by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0.8f,
                animationSpec = tween(durationMillis = 1000)
            )

            LaunchedEffect(key1 = true) {
                startAnimation = true
                delay(2000) // Beautiful delay
                if (isLoggedIn && currentRole != null) {
                    when (currentRole) {
                        "ADMIN" -> navController.navigate(Routes.ADMIN_MAIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                        "OFFICER" -> navController.navigate(Routes.OFFICER_MAIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                        else -> navController.navigate(Routes.CUSTOMER_MAIN) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    }
                } else {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(PrimaryTeal, AccentMint)
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .scale(scaleAnim)
                        .alpha(alphaAnim),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bersih.in",
                        color = Color.White,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Layanan Laundry Premium",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                }

            }
        }

        // --- Auth Screens ---
        composable(Routes.LOGIN) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onLoginSuccess = { _, role ->
                    when (role) {
                        UserRole.ADMIN -> navController.navigate(Routes.ADMIN_MAIN) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                        UserRole.OFFICER -> navController.navigate(Routes.OFFICER_MAIN) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                        UserRole.CUSTOMER -> navController.navigate(Routes.CUSTOMER_MAIN) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) },
                onRegisterSuccess = { navController.navigate(Routes.LOGIN) }
            )
        }

        // --- Customer Screens ---
        composable(Routes.CUSTOMER_MAIN) {
            val userId by authViewModel.currentUserId.collectAsState()
            
            if (userId != null) {
                val userState by authViewModel.getUserFlow(userId!!).collectAsState(initial = null)
                userState?.let { user ->
                    CustomerMainScreen(
                        user = user,
                        customerViewModel = customerViewModel,
                        authViewModel = authViewModel,
                        onNavigateToNewOrder = { navController.navigate(Routes.CUSTOMER_NEW_ORDER) },
                        onNavigateToOrderDetail = { id -> navController.navigate(Routes.customerOrderDetail(id)) },
                        onNavigateToReward = { navController.navigate(Routes.CUSTOMER_REWARD_CLAIM) },
                        onNavigateToPromo = { navController.navigate(Routes.CUSTOMER_PROMO) },
                        onLogout = {
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(Routes.CUSTOMER_MAIN) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }

        composable(Routes.CUSTOMER_NEW_ORDER) {
            val userId by authViewModel.currentUserId.collectAsState()
            if (userId != null) {
                val userState by authViewModel.getUserFlow(userId!!).collectAsState(initial = null)
                userState?.let { user ->
                    OrderScreen(
                        user = user,
                        customerViewModel = customerViewModel,
                        onNavigateBack = { navController.popBackStack() },
                        onOrderPlaced = { id ->
                            navController.navigate(Routes.customerOrderDetail(id)) {
                                popUpTo(Routes.CUSTOMER_NEW_ORDER) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }

        composable(
            Routes.CUSTOMER_ORDER_DETAIL,
            arguments = listOf(navArgument("orderId") { type = NavType.LongType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getLong("orderId") ?: 0L
            OrderDetailScreen(
                orderId = orderId,
                customerViewModel = customerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CUSTOMER_PROMO) {
            PromoScreen(
                customerViewModel = customerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CUSTOMER_REWARD_CLAIM) {
            val userId by authViewModel.currentUserId.collectAsState()
            if (userId != null) {
                val userState by authViewModel.getUserFlow(userId!!).collectAsState(initial = null)
                userState?.let { user ->
                    RewardScreen(
                        user = user,
                        customerViewModel = customerViewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }

        // --- Admin Screens ---
        composable(Routes.ADMIN_MAIN) {
            AdminMainScreen(
                adminViewModel = adminViewModel,
                authViewModel = authViewModel,
                onNavigateToServices = { navController.navigate(Routes.ADMIN_MANAGE_SERVICES) },
                onNavigateToPromos = { navController.navigate(Routes.ADMIN_MANAGE_PROMOS) },
                onNavigateToRewards = { navController.navigate(Routes.ADMIN_MANAGE_REWARDS) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.ADMIN_MAIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.ADMIN_MANAGE_SERVICES) {
            ManageServicesScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ADMIN_MANAGE_PROMOS) {
            ManagePromosScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ADMIN_MANAGE_REWARDS) {
            ManageRewardsScreen(
                adminViewModel = adminViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // --- Officer Screens ---
        composable(Routes.OFFICER_MAIN) {
            val userId by authViewModel.currentUserId.collectAsState()
            if (userId != null) {
                val userState by authViewModel.getUserFlow(userId!!).collectAsState(initial = null)
                userState?.let { user ->
                    OfficerMainScreen(
                        officer = user,
                        officerViewModel = officerViewModel,
                        authViewModel = authViewModel,
                        onNavigateToPickupDetail = { id -> navController.navigate(Routes.officerPickupDetail(id)) },
                        onLogout = {
                            navController.navigate(Routes.LOGIN) {
                                popUpTo(Routes.OFFICER_MAIN) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }

        composable(
            Routes.OFFICER_PICKUP_DETAIL,
            arguments = listOf(navArgument("pickupId") { type = NavType.LongType })
        ) { backStackEntry ->
            val pickupId = backStackEntry.arguments?.getLong("pickupId") ?: 0L
            PickupDetailScreen(
                pickupId = pickupId,
                officerViewModel = officerViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
