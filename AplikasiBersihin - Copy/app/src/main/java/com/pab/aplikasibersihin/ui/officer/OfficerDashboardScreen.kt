package com.pab.aplikasibersihin.ui.officer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.data.model.PickupStatus
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.*
import com.pab.aplikasibersihin.viewmodel.OfficerViewModel

@Composable
fun OfficerDashboardScreen(
    officer: UserEntity,
    officerViewModel: OfficerViewModel,
    onNavigateToPickupDetail: (Long) -> Unit
) {
    val pickups by officerViewModel.getPickupsForOfficer(officer.id).collectAsState(initial = emptyList())

    val pendingCount = pickups.count { it.completedAt == null }
    val completedCount = pickups.count { it.completedAt != null }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // ─── Gradient Header ──────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(PrimaryTeal, DarkTeal)
                    ),
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Portal Lapangan",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Halo, ${officer.name}",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Stats row inside header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OfficerStatChip(
                        label = "Total",
                        value = "${pickups.size}",
                        color = Color.White.copy(alpha = 0.2f),
                        textColor = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    OfficerStatChip(
                        label = "Pending",
                        value = "$pendingCount",
                        color = StatusCancelled.copy(alpha = 0.3f),
                        textColor = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    OfficerStatChip(
                        label = "Selesai",
                        value = "$completedCount",
                        color = StatusDone.copy(alpha = 0.3f),
                        textColor = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ─── Task List ────────────────────────────────────────────────
        Text(
            "Daftar Tugas Anda",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (pickups.isEmpty()) {
            EmptyState(
                message = "Tidak ada tugas penjemputan/pengantaran yang ditugaskan kepada Anda.",
                icon = Icons.Default.LocalShipping,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(pickups) { pickup ->
                    val statusColor = when (pickup.status) {
                        PickupStatus.ASSIGNED -> StatusPending
                        PickupStatus.ON_THE_WAY -> StatusConfirmed
                        PickupStatus.PICKED_UP -> StatusProcess
                        PickupStatus.DELIVERING -> ElectricBlue
                        PickupStatus.DELIVERED -> StatusDone
                    }

                    val statusIcon = when (pickup.status) {
                        PickupStatus.ASSIGNED -> Icons.Default.Assignment
                        PickupStatus.ON_THE_WAY -> Icons.Default.DirectionsCar
                        PickupStatus.PICKED_UP -> Icons.Default.Inventory2
                        PickupStatus.DELIVERING -> Icons.Default.LocalShipping
                        PickupStatus.DELIVERED -> Icons.Default.CheckCircle
                    }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToPickupDetail(pickup.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Status icon circle
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(statusColor.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = statusIcon,
                                    contentDescription = null,
                                    tint = statusColor,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Tugas #${pickup.id}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    "Order Ref: #${pickup.orderId}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                // Status badge
                                Box(
                                    modifier = Modifier
                                        .background(
                                            statusColor.copy(alpha = 0.1f),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = pickup.status.displayName,
                                        color = statusColor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OfficerStatChip(
    label: String,
    value: String,
    color: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = textColor.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
