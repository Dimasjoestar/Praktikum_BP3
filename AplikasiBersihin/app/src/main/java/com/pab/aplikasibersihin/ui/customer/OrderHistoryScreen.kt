package com.pab.aplikasibersihin.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.utils.CurrencyFormatter
import com.pab.aplikasibersihin.utils.DateFormatter
import com.pab.aplikasibersihin.viewmodel.CustomerViewModel

@Composable
fun OrderHistoryScreen(
    user: UserEntity,
    customerViewModel: CustomerViewModel,
    onNavigateToOrderDetail: (Long) -> Unit
) {
    val orders by customerViewModel.getOrdersByUserId(user.id).collectAsState(initial = emptyList())
    val activeServices by customerViewModel.activeServices.collectAsState()

    var selectedFilter by remember { mutableStateOf("Semua") }
    val filters = listOf("Semua", "Aktif", "Selesai", "Dibatalkan")

    val filteredOrders = orders.filter { order ->
        when (selectedFilter) {
            "Aktif" -> order.status.name != "DONE" && order.status.name != "CANCELLED"
            "Selesai" -> order.status.name == "DONE"
            "Dibatalkan" -> order.status.name == "CANCELLED"
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Filters
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
                    text = { Text(filter, fontWeight = FontWeight.Bold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredOrders.isEmpty()) {
            EmptyState(
                message = "Tidak ada riwayat laundry untuk filter '${selectedFilter}'",
                icon = Icons.Default.History,
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredOrders) { order ->
                    val matchingService = activeServices.find { it.id == order.serviceId }
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToOrderDetail(order.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = matchingService?.name ?: "Layanan Laundry",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = DateFormatter.formatDateOnly(order.createdAt),
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Berat: ${order.weight} kg  •  ${CurrencyFormatter.formatIdr(order.total)}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = order.status.displayName,
                                    color = PrimaryTeal,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
