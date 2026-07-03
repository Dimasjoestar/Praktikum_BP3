package com.pab.aplikasibersihin.ui.officer

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.model.PickupStatus
import com.pab.aplikasibersihin.ui.components.GradientButton
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.viewmodel.OfficerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupDetailScreen(
    pickupId: Long,
    officerViewModel: OfficerViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val pickupDetails by officerViewModel.getPickupDetails(pickupId).collectAsState(initial = null)

    var notes by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<PickupStatus?>(null) }
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(pickupDetails) {
        pickupDetails?.let {
            notes = it.pickup.notes
            selectedStatus = it.pickup.status
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas #${pickupId}", fontWeight = FontWeight.Bold) },
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
        if (pickupDetails == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryTeal)
            }
        } else {
            val details = pickupDetails!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                // Customer Profile Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Informasi Pelanggan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryTeal
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Nama: ${details.customerName}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Layanan: ${details.serviceName} (${details.weight} kg)", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Alamat: ${details.customerAddress}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${details.customerPhone}"))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryTeal),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Telepon", fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    // Open Maps
                                    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${Uri.encode(details.customerAddress)}"))
                                    mapIntent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(mapIntent)
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Directions, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Rute", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Actions Form Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Kelola Status Pekerjaan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Status dropdown
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(
                                onClick = { isExpanded = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(selectedStatus?.displayName ?: "Pilih Status")
                            }
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                PickupStatus.entries.forEach { status ->
                                    DropdownMenuItem(
                                        text = { Text(status.displayName) },
                                        onClick = {
                                            selectedStatus = status
                                            isExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = notes,
                            onValueChange = { notes = it },
                            label = { Text("Catatan Kendala / Detail") },
                            placeholder = { Text("Contoh: Hujan lebat, barang basah aman dilindungi terpal.") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        GradientButton(
                            text = "Simpan Perubahan",
                            onClick = {
                                selectedStatus?.let { status ->
                                    officerViewModel.updatePickupStatus(pickupId, status, notes)
                                    Toast.makeText(context, "Perubahan status berhasil disimpan!", Toast.LENGTH_SHORT).show()
                                    onNavigateBack()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
