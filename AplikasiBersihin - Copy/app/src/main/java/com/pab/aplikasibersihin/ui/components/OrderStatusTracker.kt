package com.pab.aplikasibersihin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal

@Composable
fun OrderStatusTracker(currentStatus: OrderStatus, modifier: Modifier = Modifier) {
    val steps = listOf(
        OrderStatus.PENDING to "Dibuat",
        OrderStatus.CONFIRMED to "Diterima",
        OrderStatus.PROCESSING to "Diproses",
        OrderStatus.DONE to "Selesai",
        OrderStatus.DELIVERED to "Diantar"
    )

    // Calculate current step index
    val currentStepIndex = when (currentStatus) {
        OrderStatus.PENDING -> 0
        OrderStatus.CONFIRMED -> 1
        OrderStatus.PICKUP, OrderStatus.PROCESSING, OrderStatus.WASHING, OrderStatus.DRYING, OrderStatus.IRONING -> 2
        OrderStatus.DONE -> 3
        OrderStatus.DELIVERED -> 4
        OrderStatus.CANCELLED -> -1
    }

    if (currentStatus == OrderStatus.CANCELLED) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Red.copy(alpha = 0.1f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Order Ini Telah Dibatalkan",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        return
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, pair ->
            val isActive = index <= currentStepIndex
            val isCurrent = index == currentStepIndex

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                // Node
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (isActive) PrimaryTeal else Color.LightGray.copy(alpha = 0.5f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (index < currentStepIndex) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selesai",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text(
                            text = "${index + 1}",
                            color = if (isActive) Color.White else Color.Gray,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Label
                Text(
                    text = pair.second,
                    fontSize = 10.sp,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isActive) PrimaryTeal else Color.Gray,
                    maxLines = 1
                )
            }
        }
    }
}
