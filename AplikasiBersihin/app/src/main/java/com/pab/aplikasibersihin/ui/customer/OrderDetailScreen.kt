package com.pab.aplikasibersihin.ui.customer

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.pab.aplikasibersihin.data.database.entity.ServiceEntity
import com.pab.aplikasibersihin.data.database.entity.TransactionEntity
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.data.model.PaymentMethod
import com.pab.aplikasibersihin.ui.components.ConfirmDialog
import com.pab.aplikasibersihin.ui.components.GradientButton
import com.pab.aplikasibersihin.ui.components.OrderStatusTracker
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.ui.theme.StatusCancelled
import com.pab.aplikasibersihin.utils.CurrencyFormatter
import com.pab.aplikasibersihin.utils.DateFormatter
import com.pab.aplikasibersihin.viewmodel.CustomerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderId: Long,
    customerViewModel: CustomerViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var order by remember { mutableStateOf<OrderEntity?>(null) }
    var service by remember { mutableStateOf<ServiceEntity?>(null) }
    var transaction by remember { mutableStateOf<TransactionEntity?>(null) }
    
    var showCancelDialog by remember { mutableStateOf(false) }
    var showPayDialog by remember { mutableStateOf(false) }
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.CASH) }

    var queueCount by remember { mutableStateOf(0) }
    val activeServices by customerViewModel.activeServices.collectAsState()

    // Load order data
    LaunchedEffect(orderId, activeServices) {
        customerViewModel.getOrderById(orderId).collect {
            order = it
            if (it != null) {
                service = activeServices.find { s -> s.id == it.serviceId }
                launch {
                    customerViewModel.getTransactionByOrderId(it.id).collect { tx ->
                        transaction = tx
                    }
                }
                launch {
                    customerViewModel.getQueueCountBeforeOrder(it.createdAt).collect { count ->
                        queueCount = count
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Order #${orderId}", fontWeight = FontWeight.Bold) },
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
        if (order == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryTeal)
            }
        } else {
            val currentOrder = order!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                // Tracking Status Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Status Laundry",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (queueCount > 0 && currentOrder.status == OrderStatus.PENDING) {
                            Text(
                                text = "Dalam antrian. Ada $queueCount pesanan di depan Anda.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        OrderStatusTracker(
                            currentStatus = currentOrder.status,
                            pickupType = currentOrder.pickupType
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Service Details Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Informasi Layanan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Layanan:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            Text(service?.name ?: "Layanan Laundry", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Estimasi Selesai:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            Text("${service?.estimationDays ?: 3} hari", fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Tanggal Order:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            Text(DateFormatter.formatDate(currentOrder.createdAt), fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Tipe Pengantaran:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            Text(if (currentOrder.pickupType == "PICKUP") "Jemput di Rumah" else "Antar Sendiri", fontSize = 13.sp)
                        }
                        if (currentOrder.pickupType == "PICKUP" && currentOrder.pickupAddress.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Alamat Jemput:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                                Text(currentOrder.pickupAddress, fontSize = 13.sp, maxLines = 2)
                            }
                        }
                        if (currentOrder.notes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Catatan:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                                Text(currentOrder.notes, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Breakdown Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Rincian Biaya",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Subtotal:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            Text(CurrencyFormatter.formatIdr(currentOrder.subtotal), fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Potongan Diskon:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            Text("- ${CurrencyFormatter.formatIdr(currentOrder.discount)}", color = PrimaryTeal, fontSize = 13.sp)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Pembayaran:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(CurrencyFormatter.formatIdr(currentOrder.total), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = PrimaryTeal)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Status Pembayaran:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
                            val isPaid = transaction?.status == "PAID"
                            Text(
                                text = if (isPaid) "LUNAS" else "BELUM BAYAR",
                                color = if (isPaid) PrimaryTeal else StatusCancelled,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                val isPaid = transaction?.status == "PAID"
                
                // Show payment option if unpaid and order is not cancelled
                if (!isPaid && currentOrder.status != OrderStatus.CANCELLED) {
                    GradientButton(
                        text = "Bayar Sekarang",
                        onClick = { showPayDialog = true }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Show cancel button if order is PENDING
                if (currentOrder.status == OrderStatus.PENDING) {
                    OutlinedButton(
                        onClick = { showCancelDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = StatusCancelled),
                        border = BorderStroke(1.dp, StatusCancelled),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Batalkan Order", fontWeight = FontWeight.Bold)
                    }
                }

                // Dialogs
                ConfirmDialog(
                    show = showCancelDialog,
                    title = "Batalkan Pesanan?",
                    message = "Apakah Anda yakin ingin membatalkan order laundry ini?",
                    onConfirm = {
                        customerViewModel.cancelOrder(currentOrder.id)
                        showCancelDialog = false
                        Toast.makeText(context, "Pesanan dibatalkan", Toast.LENGTH_SHORT).show()
                    },
                    onDismiss = { showCancelDialog = false }
                )

                if (showPayDialog) {
                    AlertDialog(
                        onDismissRequest = { showPayDialog = false },
                        title = { Text("Pilih Metode Pembayaran") },
                        text = {
                            Column {
                                PaymentMethod.entries.forEach { method ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedPaymentMethod == method,
                                            onClick = { selectedPaymentMethod = method }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(method.displayName)
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    customerViewModel.payOrder(currentOrder.id, selectedPaymentMethod) {
                                        Toast.makeText(context, "Pembayaran berhasil!", Toast.LENGTH_SHORT).show()
                                        showPayDialog = false
                                        // Force reload
                                        customerViewModel.getTransactionByOrderId(currentOrder.id)
                                    }
                                }
                            ) {
                                Text("Bayar", color = PrimaryTeal)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showPayDialog = false }) {
                                Text("Batal", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    )
                }
            }
        }
    }
}
