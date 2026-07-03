package com.pab.aplikasibersihin.ui.customer

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.ui.theme.StatusCancelled
import com.pab.aplikasibersihin.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardScreen(
    user: UserEntity,
    customerViewModel: CustomerViewModel,
    onNavigateBack: () -> Unit
) {
    val activeRewards by customerViewModel.activeRewards.collectAsState()
    val claimResult by customerViewModel.claimResult.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(claimResult) {
        claimResult?.let { result ->
            if (result.isSuccess) {
                Toast.makeText(context, "Berhasil klaim reward! Voucher telah disimpan.", Toast.LENGTH_SHORT).show()
                customerViewModel.resetClaimResult()
            } else {
                Toast.makeText(context, result.exceptionOrNull()?.message ?: "Gagal klaim", Toast.LENGTH_LONG).show()
                customerViewModel.resetClaimResult()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tukar Reward Poin", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Points header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryTeal.copy(alpha = 0.05f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Poin XP Anda saat ini:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${user.xp} XP", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryTeal)
                    }
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Text(
                text = "Reward yang Tersedia:",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
            )

            if (activeRewards.isEmpty()) {
                EmptyState(
                    message = "Tidak ada reward yang dapat ditukarkan saat ini.",
                    icon = Icons.Default.CardGiftcard,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(activeRewards) { reward ->
                        val isAffordable = user.xp >= reward.xpCost
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(reward.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(reward.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "${reward.xpCost} XP Poin",
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isAffordable) PrimaryTeal else StatusCancelled,
                                        fontSize = 13.sp
                                    )
                                }
                                Button(
                                    onClick = { customerViewModel.claimReward(user.id, reward.id) },
                                    enabled = isAffordable,
                                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryTeal),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Tukar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
