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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.ServiceEntity
import com.pab.aplikasibersihin.ui.components.ConfirmDialog
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.utils.CurrencyFormatter
import com.pab.aplikasibersihin.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageServicesScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val allServices by adminViewModel.allServices.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var serviceToEdit by remember { mutableStateOf<ServiceEntity?>(null) }
    var serviceToDelete by remember { mutableStateOf<ServiceEntity?>(null) }

    // Input States
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var estimation by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Kiloan") }

    fun openDialog(service: ServiceEntity? = null) {
        serviceToEdit = service
        if (service != null) {
            name = service.name
            desc = service.description
            price = service.pricePerKg.toString()
            estimation = service.estimationDays.toString()
            category = service.category
        } else {
            name = ""
            desc = ""
            price = ""
            estimation = ""
            category = "Kiloan"
        }
        showDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kelola Layanan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { openDialog() }) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Layanan")
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
            if (allServices.isEmpty()) {
                EmptyState(
                    message = "Belum ada layanan laundry. Tambah sekarang!",
                    icon = Icons.Default.Layers,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allServices) { service ->
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
                                    Text(service.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(service.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${CurrencyFormatter.formatIdr(service.pricePerKg)} / kg  •  ${service.estimationDays} hari",
                                        fontWeight = FontWeight.SemiBold,
                                        color = PrimaryTeal,
                                        fontSize = 13.sp
                                    )
                                }
                                Row {
                                    IconButton(onClick = { openDialog(service) }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryTeal)
                                    }
                                    IconButton(onClick = { serviceToDelete = service }) {
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
                title = { Text(if (serviceToEdit == null) "Tambah Layanan" else "Edit Layanan") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nama Layanan") },
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
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Harga per kg") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = estimation,
                            onValueChange = { estimation = it },
                            label = { Text("Estimasi Waktu (Hari)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Kategori:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row {
                            RadioButton(selected = category == "Kiloan", onClick = { category = "Kiloan" })
                            Text("Kiloan", modifier = Modifier.align(Alignment.CenterVertically))
                            Spacer(modifier = Modifier.width(16.dp))
                            RadioButton(selected = category == "Satuan", onClick = { category = "Satuan" })
                            Text("Satuan", modifier = Modifier.align(Alignment.CenterVertically))
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val priceVal = price.toDoubleOrNull()
                            val estVal = estimation.toIntOrNull()
                            if (name.isBlank() || priceVal == null || estVal == null) {
                                Toast.makeText(context, "Harap isi data dengan benar!", Toast.LENGTH_SHORT).show()
                            } else {
                                adminViewModel.saveService(
                                    id = serviceToEdit?.id ?: 0L,
                                    name = name,
                                    description = desc,
                                    price = priceVal,
                                    estimation = estVal,
                                    category = category
                                )
                                showDialog = false
                                Toast.makeText(context, "Layanan disimpan", Toast.LENGTH_SHORT).show()
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
            show = serviceToDelete != null,
            title = "Hapus Layanan?",
            message = "Apakah Anda yakin ingin menghapus layanan '${serviceToDelete?.name}'?",
            onConfirm = {
                serviceToDelete?.let {
                    adminViewModel.deleteService(it)
                    Toast.makeText(context, "Layanan dihapus", Toast.LENGTH_SHORT).show()
                }
                serviceToDelete = null
            },
            onDismiss = { serviceToDelete = null }
        )
    }
}
