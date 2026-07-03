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
import androidx.compose.material.icons.filled.CardGiftcard
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
import com.pab.aplikasibersihin.data.database.entity.RewardEntity
import com.pab.aplikasibersihin.ui.components.ConfirmDialog
import com.pab.aplikasibersihin.ui.components.EmptyState
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageRewardsScreen(
    adminViewModel: AdminViewModel,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val allRewards by adminViewModel.allRewards.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var rewardToEdit by remember { mutableStateOf<RewardEntity?>(null) }
    var rewardToDelete by remember { mutableStateOf<RewardEntity?>(null) }

    // Input States
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var xpCost by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("DISCOUNT") } // DISCOUNT, FREE_SHIPPING, FREE_ITEM

    fun openDialog(reward: RewardEntity? = null) {
        rewardToEdit = reward
        if (reward != null) {
            name = reward.name
            desc = reward.description
            xpCost = reward.xpCost.toString()
            type = reward.type
        } else {
            name = ""
            desc = ""
            xpCost = ""
            type = "DISCOUNT"
        }
        showDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kelola Rewards", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { openDialog() }) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Reward")
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
            if (allRewards.isEmpty()) {
                EmptyState(
                    message = "Belum ada item reward aktif. Tambah sekarang!",
                    icon = Icons.Default.CardGiftcard,
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(allRewards) { reward ->
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
                                    Text(reward.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(reward.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${reward.xpCost} XP  •  Tipe: ${reward.type}",
                                        fontWeight = FontWeight.SemiBold,
                                        color = PrimaryTeal,
                                        fontSize = 13.sp
                                    )
                                }
                                Row {
                                    IconButton(onClick = { openDialog(reward) }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryTeal)
                                    }
                                    IconButton(onClick = { rewardToDelete = reward }) {
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
                title = { Text(if (rewardToEdit == null) "Tambah Reward" else "Edit Reward") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Nama Reward") },
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
                            value = xpCost,
                            onValueChange = { xpCost = it },
                            label = { Text("Kebutuhan Poin XP") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Tipe Voucher:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row {
                            RadioButton(selected = type == "DISCOUNT", onClick = { type = "DISCOUNT" })
                            Text("Diskon", modifier = Modifier.align(Alignment.CenterVertically))
                            Spacer(modifier = Modifier.width(8.dp))
                            RadioButton(selected = type == "FREE_SHIPPING", onClick = { type = "FREE_SHIPPING" })
                            Text("Free Ongkir", modifier = Modifier.align(Alignment.CenterVertically))
                            Spacer(modifier = Modifier.width(8.dp))
                            RadioButton(selected = type == "FREE_ITEM", onClick = { type = "FREE_ITEM" })
                            Text("Free Item", modifier = Modifier.align(Alignment.CenterVertically))
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val costVal = xpCost.toIntOrNull()
                            if (name.isBlank() || costVal == null) {
                                Toast.makeText(context, "Harap isi data dengan benar!", Toast.LENGTH_SHORT).show()
                            } else {
                                adminViewModel.saveReward(
                                    id = rewardToEdit?.id ?: 0L,
                                    name = name,
                                    description = desc,
                                    xpCost = costVal,
                                    type = type
                                )
                                showDialog = false
                                Toast.makeText(context, "Reward disimpan", Toast.LENGTH_SHORT).show()
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
            show = rewardToDelete != null,
            title = "Hapus Reward?",
            message = "Apakah Anda yakin ingin menghapus reward '${rewardToDelete?.name}'?",
            onConfirm = {
                rewardToDelete?.let {
                    adminViewModel.deleteReward(it)
                    Toast.makeText(context, "Reward dihapus", Toast.LENGTH_SHORT).show()
                }
                rewardToDelete = null
            },
            onDismiss = { rewardToDelete = null }
        )
    }
}
