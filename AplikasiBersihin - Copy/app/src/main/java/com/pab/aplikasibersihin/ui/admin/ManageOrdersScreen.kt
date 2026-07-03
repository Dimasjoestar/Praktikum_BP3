package com.pab.aplikasibersihin.ui.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.OrderEntity
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.utils.CurrencyFormatter
import com.pab.aplikasibersihin.utils.DateFormatter
import com.pab.aplikasibersihin.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageOrdersScreen(
    adminViewModel: AdminViewModel
) {
    val context = LocalContext.current
    val allOrders by adminViewModel.allOrders.collectAsState()
    val allServices by adminViewModel.allServices.collectAsState()
    val allUsers by adminViewModel.allUsers.collectAsState()
    val officers by adminViewModel.getOfficers().collectAsState(initial = emptyList())
    val allPickups by adminViewModel.allPickups.collectAsState()

    var selectedOrderToAssign by remember { mutableStateOf<OrderEntity?>(null) }
    var showAssignDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (allOrders.isEmpty()) {
            EmptyState(
                message = "Belum ada pesanan masuk.",
                icon = Icons.Default.Receipt,
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(allOrders) { order ->
                    val customer = allUsers.find { it.id == order.userId }
                    val service = allServices.find { it.id == order.serviceId }
                    val pickupJob = allPickups.find { it.orderId == order.id }
                    val assignedOfficer = officers.find { it.id == pickupJob?.officerId }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Order #${order.id}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = DateFormatter.formatDateOnly(order.createdAt),
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Customer: ${customer?.name ?: "Unknown"}", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text("Layanan: ${service?.name ?: "Unknown"} (${order.weight} kg)", fontSize = 13.sp, color = Color.DarkGray)
                            Text("Total: ${CurrencyFormatter.formatIdr(order.total)}", fontSize = 13.sp, color = PrimaryTeal, fontWeight = FontWeight.Bold)
                            
                            if (order.pickupType == "PICKUP") {
                                Text(
                                    text = "Antar-Jemput: " + (assignedOfficer?.let { "Petugas: ${it.name}" } ?: "Belum Ditugaskan"),
                                    fontSize = 12.sp,
                                    color = if (assignedOfficer != null) PrimaryTeal else Color.Red,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Status Selector Dropdown
                                var expanded by remember { mutableStateOf(false) }
                                Box {
                                    OutlinedButton(
                                        onClick = { expanded = true },
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(36.dp)
                                    ) {
                                        Text(order.status.displayName, fontSize = 11.sp)
                                    }
                                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                        OrderStatus.entries.forEach { status ->
                                            DropdownMenuItem(
                                                text = { Text(status.displayName, fontSize = 12.sp) },
                                                onClick = {
                                                    adminViewModel.updateOrderStatus(order.id, status)
                                                    expanded = false
                                                    Toast.makeText(context, "Status diupdate ke: ${status.displayName}", Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                        }
                                    }
                                }

                                // Officer Assignment Button
                                if (order.pickupType == "PICKUP" && assignedOfficer == null) {
                                    Button(
                                        onClick = {
                                            selectedOrderToAssign = order
                                            showAssignDialog = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryTeal),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                        modifier = Modifier.height(36.dp),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.AssignmentInd, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Tugaskan", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Officer Assignment Dialog
        if (showAssignDialog && selectedOrderToAssign != null) {
            val order = selectedOrderToAssign!!
            val pickupJob = allPickups.find { it.orderId == order.id }

            AlertDialog(
                onDismissRequest = { showAssignDialog = false },
                title = { Text("Tugaskan Petugas Lapangan") },
                text = {
                    Column {
                        if (officers.isEmpty()) {
                            Text("Tidak ada petugas lapangan terdaftar.", color = Color.Red)
                        } else {
                            Text("Pilih petugas untuk menjemput pakaian:", fontSize = 13.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(12.dp))
                            officers.forEach { officer ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            if (pickupJob != null) {
                                                adminViewModel.assignOfficerToPickup(pickupJob.id, officer.id)
                                                Toast.makeText(context, "Petugas ditugaskan: ${officer.name}", Toast.LENGTH_SHORT).show()
                                            } else {
                                                Toast.makeText(context, "Gagal: Data penjemputan belum dibuat di database", Toast.LENGTH_LONG).show()
                                            }
                                            showAssignDialog = false
                                        },
                                    colors = CardDefaults.cardColors(containerColor = PrimaryTeal.copy(alpha = 0.05f))
                                ) {
                                    Text(
                                        text = officer.name,
                                        modifier = Modifier.padding(12.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAssignDialog = false }) {
                        Text("Tutup", color = Color.Gray)
                    }
                },
                dismissButton = null
            )
        }
    }
}

