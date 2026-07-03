package com.pab.aplikasibersihin.ui.customer

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.database.entity.PromoEntity
import com.pab.aplikasibersihin.data.database.entity.ServiceEntity
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.data.model.MemberLevel
import com.pab.aplikasibersihin.ui.components.GradientButton
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.ui.theme.AccentMint
import com.pab.aplikasibersihin.utils.CurrencyFormatter
import com.pab.aplikasibersihin.viewmodel.CustomerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    user: UserEntity,
    customerViewModel: CustomerViewModel,
    onNavigateBack: () -> Unit,
    onOrderPlaced: (Long) -> Unit
) {
    val context = LocalContext.current
    val activeServices by customerViewModel.activeServices.collectAsState()
    val activePromos by customerViewModel.activePromos.collectAsState()
    val orderResult by customerViewModel.orderResult.collectAsState()

    var currentStep by remember { mutableStateOf(1) } // Steps 1 to 4

    // Order State variables
    var selectedService by remember { mutableStateOf<ServiceEntity?>(null) }
    var weightInput by remember { mutableStateOf("") }
    var notesInput by remember { mutableStateOf("") }
    var pickupType by remember { mutableStateOf("ANTAR_SENDIRI") } // "ANTAR_SENDIRI" or "PICKUP"
    var pickupAddress by remember { mutableStateOf(user.address) }
    var selectedPromo by remember { mutableStateOf<PromoEntity?>(null) }

    LaunchedEffect(orderResult) {
        orderResult?.let { result ->
            if (result.isSuccess) {
                val orderId = result.getOrNull()
                if (orderId != null) {
                    Toast.makeText(context, "Order berhasil dibuat!", Toast.LENGTH_SHORT).show()
                    onOrderPlaced(orderId)
                    customerViewModel.resetOrderResult()
                }
            } else {
                Toast.makeText(context, result.exceptionOrNull()?.message ?: "Gagal membuat order", Toast.LENGTH_LONG).show()
                customerViewModel.resetOrderResult()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Laundry Baru", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentStep > 1) currentStep-- else onNavigateBack()
                    }) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Step indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepIndicatorItem(step = 1, label = "Layanan", currentStep = currentStep)
                StepLine(active = currentStep >= 2)
                StepIndicatorItem(step = 2, label = "Detail", currentStep = currentStep)
                StepLine(active = currentStep >= 3)
                StepIndicatorItem(step = 3, label = "Pengantaran", currentStep = currentStep)
                StepLine(active = currentStep >= 4)
                StepIndicatorItem(step = 4, label = "Konfirmasi", currentStep = currentStep)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Step Content
            when (currentStep) {
                1 -> {
                    // Choose Service
                    Text("Pilih Layanan Laundry:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (activeServices.isEmpty()) {
                        Text("Memuat daftar layanan...", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        activeServices.forEach { service ->
                            val isSelected = selectedService?.id == service.id
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
                                    .clickable { selectedService = service },
                                shape = RoundedCornerShape(16.dp),
                                border = if (isSelected) BorderStroke(2.dp, PrimaryTeal) else null,
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) PrimaryTeal.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                if (isSelected) PrimaryTeal else MaterialTheme.colorScheme.surfaceVariant,
                                                RoundedCornerShape(8.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocalLaundryService,
                                            contentDescription = null,
                                            tint = if (isSelected) Color.White else PrimaryTeal
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(service.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                        Text(service.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "${CurrencyFormatter.formatIdr(service.pricePerKg)} / kg",
                                            fontWeight = FontWeight.SemiBold,
                                            color = PrimaryTeal,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    GradientButton(
                        text = "Lanjut",
                        onClick = { currentStep = 2 },
                        enabled = selectedService != null
                    )
                }

                2 -> {
                    // Input Detail weight
                    Text("Detail Berat & Catatan:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    selectedService?.let {
                        Text(
                            text = "Layanan Terpilih: ${it.name}",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryTeal
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    OutlinedTextField(
                        value = weightInput,
                        onValueChange = { weightInput = it },
                        label = { Text("Estimasi Berat (kg)") },
                        leadingIcon = { Icon(Icons.Default.Scale, contentDescription = null, tint = PrimaryTeal) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, focusedLabelColor = PrimaryTeal)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = notesInput,
                        onValueChange = { notesInput = it },
                        label = { Text("Catatan Khusus (Opsional)") },
                        leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null, tint = PrimaryTeal) },
                        placeholder = { Text("Contoh: Noda minyak di kemeja biru") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, focusedLabelColor = PrimaryTeal)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    GradientButton(
                        text = "Lanjut",
                        onClick = {
                            val w = weightInput.toDoubleOrNull()
                            if (w == null || w <= 0.0) {
                                Toast.makeText(context, "Harap masukkan estimasi berat yang valid!", Toast.LENGTH_SHORT).show()
                            } else {
                                currentStep = 3
                            }
                        }
                    )
                }

                3 -> {
                    // Pickup scheduling
                    Text("Opsi Penjemputan Pakaian:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { pickupType = "ANTAR_SENDIRI" },
                            border = if (pickupType == "ANTAR_SENDIRI") BorderStroke(2.dp, PrimaryTeal) else null,
                            colors = CardDefaults.cardColors(
                                containerColor = if (pickupType == "ANTAR_SENDIRI") PrimaryTeal.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.Storefront, contentDescription = null, tint = PrimaryTeal)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Antar Sendiri", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("Ke Toko", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { pickupType = "PICKUP" },
                            border = if (pickupType == "PICKUP") BorderStroke(2.dp, PrimaryTeal) else null,
                            colors = CardDefaults.cardColors(
                                containerColor = if (pickupType == "PICKUP") PrimaryTeal.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.LocalShipping, contentDescription = null, tint = PrimaryTeal)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Jemput Paket", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("Di Rumah", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    AnimatedVisibility(visible = pickupType == "PICKUP") {
                        OutlinedTextField(
                            value = pickupAddress,
                            onValueChange = { pickupAddress = it },
                            label = { Text("Alamat Penjemputan") },
                            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = PrimaryTeal) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, focusedLabelColor = PrimaryTeal)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    GradientButton(
                        text = "Lanjut",
                        onClick = {
                            if (pickupType == "PICKUP" && pickupAddress.isBlank()) {
                                Toast.makeText(context, "Harap isi alamat penjemputan!", Toast.LENGTH_SHORT).show()
                            } else {
                                currentStep = 4
                            }
                        }
                    )
                }

                4 -> {
                    // Checkout Summary & Promo selection
                    Text("Ringkasan Pemesanan:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    val pricePerKg = selectedService?.pricePerKg ?: 0.0
                    val weight = weightInput.toDoubleOrNull() ?: 0.0
                    val subtotal = pricePerKg * weight

                    val memberLevel = MemberLevel.getLevelFromXp(user.xp)
                    val memberDiscountVal = memberLevel.discount
                    val promoDiscountVal = selectedPromo?.discountPercent ?: 0.0
                    val combinedDiscountPercent = memberDiscountVal + promoDiscountVal

                    val discountVal = subtotal * combinedDiscountPercent
                    val total = (subtotal - discountVal).coerceAtLeast(0.0)

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Layanan:", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(selectedService?.name ?: "-", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Harga / kg:", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(CurrencyFormatter.formatIdr(pricePerKg), fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Estimasi Berat:", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$weight kg", fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Subtotal:", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(CurrencyFormatter.formatIdr(subtotal), fontSize = 13.sp)
                            }
                            
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                            // Level Discount Info
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Diskon Member (${user.level}):", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("- ${CurrencyFormatter.formatIdr(subtotal * memberDiscountVal)} (${(memberDiscountVal * 100).toInt()}%)", color = PrimaryTeal, fontSize = 13.sp)
                            }

                            // Promo Selector Dropdown
                            if (activePromos.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Pilih Promo Tambahan:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(6.dp))

                                var expanded by remember { mutableStateOf(false) }
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedButton(
                                        onClick = { expanded = true },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryTeal)
                                    ) {
                                        Text(selectedPromo?.let { "${it.name} (${(it.discountPercent * 100).toInt()}%)" } ?: "Pilih Kode Promo")
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                    }
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Tanpa Promo") },
                                            onClick = {
                                                selectedPromo = null
                                                expanded = false
                                            }
                                        )
                                        activePromos.forEach { promo ->
                                            val isEligible = subtotal >= promo.minOrderAmount
                                            DropdownMenuItem(
                                                text = { 
                                                    Text(
                                                        text = "${promo.name} (${(promo.discountPercent * 100).toInt()}%)" + if (!isEligible) " - Min ${CurrencyFormatter.formatIdr(promo.minOrderAmount)}" else "",
                                                        color = if (isEligible) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outlineVariant
                                                    ) 
                                                },
                                                onClick = {
                                                    if (isEligible) {
                                                        selectedPromo = promo
                                                    } else {
                                                        Toast.makeText(context, "Pesanan tidak mencapai syarat minimal promo ini", Toast.LENGTH_SHORT).show()
                                                    }
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            if (selectedPromo != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Diskon Promo (${selectedPromo?.name}):", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("- ${CurrencyFormatter.formatIdr(subtotal * promoDiscountVal)} (${(promoDiscountVal * 100).toInt()}%)", color = PrimaryTeal, fontSize = 13.sp)
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total Tagihan:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(
                                    text = CurrencyFormatter.formatIdr(total),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = PrimaryTeal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    GradientButton(
                        text = "Buat Order Sekarang",
                        onClick = {
                            selectedService?.let { service ->
                                val discountPercent = selectedPromo?.discountPercent ?: 0.0
                                customerViewModel.createOrder(
                                    userId = user.id,
                                    serviceId = service.id,
                                    weight = weightInput.toDoubleOrNull() ?: 1.0,
                                    pickupType = pickupType,
                                    pickupAddress = if (pickupType == "PICKUP") pickupAddress else "",
                                    notes = notesInput,
                                    promoDiscountPercent = discountPercent
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StepIndicatorItem(step: Int, label: String, currentStep: Int) {
    val isActive = step <= currentStep
    val isCurrent = step == currentStep

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isActive) PrimaryTeal else MaterialTheme.colorScheme.surfaceVariant
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$step",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
            color = if (isCurrent) PrimaryTeal else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun RowScope.StepLine(active: Boolean) {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(2.dp)
            .background(if (active) PrimaryTeal else MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 4.dp)
    )
}
