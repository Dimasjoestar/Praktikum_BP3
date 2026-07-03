package com.pab.aplikasibersihin.ui.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.PromoEntity
import com.pab.aplikasibersihin.ui.components.ConfirmDialog
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.utils.CurrencyFormatter
import com.pab.aplikasibersihin.utils.DateFormatter
import com.pab.aplikasibersihin.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagePromosScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val allPromos by adminViewModel.allPromos.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var promoToEdit by remember { mutableStateOf<PromoEntity?>(null) }
    var promoToDelete by remember { mutableStateOf<PromoEntity?>(null) }

    // Input States
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var discountPercent by remember { mutableStateOf("") }
    var minOrder by remember { mutableStateOf("") }
    var validDays by remember { mutableStateOf("") }

    fun openDialog(promo: PromoEntity? = null) {
        promoToEdit = promo
        if (promo != null) {
            name = promo.name
            desc = promo.description
            discountPercent = (promo.discountPercent * 100).toInt().toString()
            minOrder = promo.minOrderAmount.toString()
            validDays = "30" // default edit validity reset
        } else {
            name = ""
            desc = ""
            discountPercent = ""
            minOrder = ""
            validDays = "30"
        }
        showDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kelola Promo", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { openDialog() }) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Promo")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryTeal,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
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
            if (allPromos.isEmpty()) {
                EmptyState(
                    message = "Belum ada kupon promo aktif. Tambah sekarang!",
                    icon = Icons.Default.ConfirmationNumber,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allPromos) { promo ->
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
                                    Text(promo.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(promo.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Diskon ${(promo.discountPercent * 100).toInt()}%  •  Min: ${CurrencyFormatter.formatIdr(promo.minOrderAmount)}",
                                        fontWeight = FontWeight.SemiBold,
                                        color = PrimaryTeal,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = "Berlaku sampai: ${DateFormatter.formatDateOnly(promo.validUntil)}",
                                        fontSize = 11.sp,
                                        color = Color.Red
                                    )
                                }
                                Row {
                                    IconButton(onClick = { openDialog(promo) }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryTeal)
                                    }
                                    IconButton(onClick = { promoToDelete = promo }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add/Edit Dialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(if (promoToEdit == null) "Tambah Promo" else "Edit Promo") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nama Promo") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = desc,
                            onValueChange = { desc = it },
                            label = { Text("Deskripsi") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = discountPercent,
                            onValueChange = { discountPercent = it },
                            label = { Text("Persentase Diskon (%)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = minOrder,
                            onValueChange = { minOrder = it },
                            label = { Text("Minimal Nominal Belanja (Rp)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = validDays,
                            onValueChange = { validDays = it },
                            label = { Text("Masa Berlaku (Hari)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val pct = discountPercent.toDoubleOrNull()
                            val minVal = minOrder.toDoubleOrNull()
                            val daysVal = validDays.toIntOrNull()
                            if (name.isBlank() || pct == null || minVal == null || daysVal == null) {
                                Toast.makeText(context, "Harap isi data dengan benar!", Toast.LENGTH_SHORT).show()
                            } else {
                                adminViewModel.savePromo(
                                    id = promoToEdit?.id ?: 0L,
                                    name = name,
                                    description = desc,
                                    discountPercent = pct / 100.0,
                                    minOrder = minVal,
                                    validDays = daysVal
                                )
                                showDialog = false
                                Toast.makeText(context, "Promo disimpan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Simpan", color = PrimaryTeal)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Batal", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            )
        }

        // Delete Dialog
        ConfirmDialog(
            show = promoToDelete != null,
            title = "Hapus Promo?",
            message = "Apakah Anda yakin ingin menghapus promo '${promoToDelete?.name}'?",
            onConfirm = {
                promoToDelete?.let {
                    adminViewModel.deletePromo(it)
                    Toast.makeText(context, "Promo dihapus", Toast.LENGTH_SHORT).show()
                }
                promoToDelete = null
            },
            onDismiss = { promoToDelete = null }
        )
    }
}
