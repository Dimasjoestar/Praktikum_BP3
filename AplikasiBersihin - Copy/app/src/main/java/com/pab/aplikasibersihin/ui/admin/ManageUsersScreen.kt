package com.pab.aplikasibersihin.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.data.model.MemberLevel
import com.pab.aplikasibersihin.data.model.UserRole
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.components.LevelBadge
import com.pab.aplikasibersihin.ui.theme.*
import com.pab.aplikasibersihin.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUsersScreen(
    adminViewModel: AdminViewModel
) {
    val allUsers by adminViewModel.allUsers.collectAsState()

    var selectedFilter by remember { mutableStateOf("Semua") }
    val filters = listOf("Semua", "Customer", "Officer", "Admin")

    val filteredUsers = allUsers.filter { user ->
        when (selectedFilter) {
            "Customer" -> user.role == UserRole.CUSTOMER
            "Officer" -> user.role == UserRole.OFFICER
            "Admin" -> user.role == UserRole.ADMIN
            else -> true
        }
    }

    // Stats
    val totalCustomers = allUsers.count { it.role == UserRole.CUSTOMER }
    val totalOfficers = allUsers.count { it.role == UserRole.OFFICER }
    val bronzeCount = allUsers.count { it.role == UserRole.CUSTOMER && it.level.uppercase() == "BRONZE" }
    val silverCount = allUsers.count { it.role == UserRole.CUSTOMER && it.level.uppercase() == "SILVER" }
    val goldCount = allUsers.count { it.role == UserRole.CUSTOMER && it.level.uppercase() == "GOLD" }
    val platinumCount = allUsers.count { it.role == UserRole.CUSTOMER && it.level.uppercase() == "PLATINUM" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Member Level Summary Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(PrimaryTeal, AccentMint)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Ringkasan Pengguna",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$totalCustomers Pelanggan  •  $totalOfficers Petugas",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Level distribution
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LevelStatChip("Bronze", bronzeCount, ColorBronze)
                        LevelStatChip("Silver", silverCount, ColorSilver)
                        LevelStatChip("Gold", goldCount, ColorGold)
                        LevelStatChip("Platinum", platinumCount, ColorPlatinum)
                    }
                }
            }
        }

        // Filter Tabs
        ScrollableTabRow(
            selectedTabIndex = filters.indexOf(selectedFilter),
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = PrimaryTeal,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[filters.indexOf(selectedFilter)]),
                    color = PrimaryTeal
                )
            }
        ) {
            filters.forEach { filter ->
                Tab(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    text = { Text(filter, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredUsers.isEmpty()) {
            EmptyState(
                message = "Tidak ada pengguna untuk filter '$selectedFilter'",
                icon = Icons.Default.PeopleAlt,
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredUsers) { user ->
                    UserCard(user = user)
                }
            }
        }
    }
}

@Composable
private fun UserCard(user: UserEntity) {
    val roleColor = when (user.role) {
        UserRole.ADMIN -> Color(0xFFE53935)
        UserRole.OFFICER -> Color(0xFF1E88E5)
        UserRole.CUSTOMER -> PrimaryTeal
    }

    val roleLabel = when (user.role) {
        UserRole.ADMIN -> "Admin"
        UserRole.OFFICER -> "Petugas"
        UserRole.CUSTOMER -> "Pelanggan"
    }

    val avatarInitial = user.name.firstOrNull()?.uppercase() ?: "?"

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
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(roleColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = avatarInitial,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = roleColor
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = user.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    // Role chip
                    Box(
                        modifier = Modifier
                            .background(roleColor.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = roleLabel,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = roleColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = user.email,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                if (user.role == UserRole.CUSTOMER) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LevelBadge(levelName = user.level)
                        Text(
                            text = "${user.xp} XP",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryTeal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = user.phone,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelStatChip(label: String, count: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$count",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.9f),
            fontWeight = FontWeight.Medium
        )
    }
}
