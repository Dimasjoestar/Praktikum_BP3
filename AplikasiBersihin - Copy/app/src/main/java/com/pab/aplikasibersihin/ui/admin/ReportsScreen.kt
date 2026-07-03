package com.pab.aplikasibersihin.ui.admin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.*
import com.pab.aplikasibersihin.utils.CurrencyFormatter
import com.pab.aplikasibersihin.utils.DateFormatter
import com.pab.aplikasibersihin.viewmodel.AdminViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    adminViewModel: AdminViewModel
) {
    val allOrders by adminViewModel.allOrders.collectAsState()
    val allUsers by adminViewModel.allUsers.collectAsState()
    val totalRevenue by adminViewModel.totalRevenue.collectAsState()
    val totalOrdersCount by adminViewModel.totalOrdersCount.collectAsState()
    val pendingOrders by adminViewModel.pendingOrdersCount.collectAsState()
    val completedOrders by adminViewModel.completedOrdersCount.collectAsState()

    // Calculate this month's revenue
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    val monthStart = calendar.timeInMillis

    val thisMonthOrders = allOrders.filter { it.createdAt >= monthStart }
    val thisMonthRevenue = thisMonthOrders
        .filter { it.status == OrderStatus.DELIVERED || it.status == OrderStatus.DONE }
        .sumOf { it.total }

    val completedOrdersList = allOrders.filter {
        it.status == OrderStatus.DELIVERED || it.status == OrderStatus.DONE
    }.sortedByDescending { it.updatedAt }

    // Order status distribution
    val statusCounts = OrderStatus.entries.map { status ->
        status to allOrders.count { it.status == status }
    }.filter { it.second > 0 }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Revenue Summary Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(PrimaryTeal, DarkTeal)
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "Laporan Pendapatan",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = CurrencyFormatter.formatIdr(totalRevenue),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Total Pendapatan Keseluruhan",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Bulan Ini",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = CurrencyFormatter.formatIdr(thisMonthRevenue),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Order Bulan Ini",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = "${thisMonthOrders.size} order",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        // Statistics Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatMiniCard(
                    label = "Total Order",
                    value = "$totalOrdersCount",
                    icon = Icons.Default.Receipt,
                    iconColor = PrimaryTeal,
                    modifier = Modifier.weight(1f)
                )
                StatMiniCard(
                    label = "Menunggu",
                    value = "$pendingOrders",
                    icon = Icons.Default.PendingActions,
                    iconColor = StatusPending,
                    modifier = Modifier.weight(1f)
                )
                StatMiniCard(
                    label = "Selesai",
                    value = "$completedOrders",
                    icon = Icons.Default.CheckCircle,
                    iconColor = StatusDone,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Order Status Distribution
        if (statusCounts.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Distribusi Status Order",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        statusCounts.forEach { (status, count) ->
                            val fraction = if (totalOrdersCount > 0) count.toFloat() / totalOrdersCount else 0f
                            val statusColor = when (status) {
                                OrderStatus.PENDING -> StatusPending
                                OrderStatus.CONFIRMED -> StatusConfirmed
                                OrderStatus.PICKUP, OrderStatus.PROCESSING,
                                OrderStatus.WASHING, OrderStatus.DRYING,
                                OrderStatus.IRONING -> StatusProcess
                                OrderStatus.DONE, OrderStatus.DELIVERED -> StatusDone
                                OrderStatus.CANCELLED -> StatusCancelled
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(statusColor)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = status.displayName,
                                    fontSize = 12.sp,
                                    modifier = Modifier.weight(1f),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "$count",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                // Mini progress bar
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(Color.LightGray.copy(alpha = 0.3f))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(fraction)
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(statusColor)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Recent Completed Transactions Header
        item {
            Text(
                text = "Riwayat Transaksi Selesai",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (completedOrdersList.isEmpty()) {
            item {
                EmptyState(
                    message = "Belum ada laporan transaksi selesai.",
                    icon = Icons.Default.Assessment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        } else {
            items(completedOrdersList) { order ->
                val customer = allUsers.find { it.id == order.userId }
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(StatusDone.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = StatusDone,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "Order #${order.id}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    "Pelanggan: ${customer?.name ?: "Unknown"}",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    DateFormatter.formatDateOnly(order.updatedAt),
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        Text(
                            text = CurrencyFormatter.formatIdr(order.total),
                            fontWeight = FontWeight.Bold,
                            color = PrimaryTeal,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun StatMiniCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}
