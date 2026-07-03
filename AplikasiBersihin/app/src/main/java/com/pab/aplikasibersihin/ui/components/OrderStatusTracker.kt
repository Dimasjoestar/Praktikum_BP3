package com.pab.aplikasibersihin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.ui.theme.StatusCancelled

@Composable
fun OrderStatusTracker(
    currentStatus: OrderStatus,
    pickupType: String,
    modifier: Modifier = Modifier
) {
    if (currentStatus == OrderStatus.CANCELLED) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(StatusCancelled.copy(alpha = 0.1f))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Order Ini Telah Dibatalkan",
                color = StatusCancelled,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        return
    }

    val isDelivery = pickupType == "PICKUP"
    val steps = if (isDelivery) {
        listOf("Diterima", "Diproses", "Diantar", "Selesai")
    } else {
        listOf("Diterima", "Diproses", "Selesai")
    }

    val currentStepIndex = when (currentStatus) {
        OrderStatus.PENDING, OrderStatus.CONFIRMED -> 0
        OrderStatus.PROCESSING -> 1
        OrderStatus.DELIVERING -> 2
        OrderStatus.DONE -> if (isDelivery) 3 else 2
        else -> -1
    }

    val inactiveColor = MaterialTheme.colorScheme.surfaceVariant
    val activeColor = PrimaryTeal

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .drawBehind {
                val stepWidth = size.width / steps.size
                val startX = stepWidth / 2
                val endX = size.width - stepWidth / 2
                val y = 16.dp.toPx() // Half of the 32.dp circle size

                // Draw inactive background line
                drawLine(
                    color = inactiveColor,
                    start = androidx.compose.ui.geometry.Offset(startX, y),
                    end = androidx.compose.ui.geometry.Offset(endX, y),
                    strokeWidth = 2.dp.toPx()
                )

                // Draw active progress line
                if (currentStepIndex > 0) {
                    val activeStepCount = currentStepIndex.coerceAtMost(steps.size - 1)
                    val activeEndX = startX + (activeStepCount * stepWidth)
                    drawLine(
                        color = activeColor,
                        start = androidx.compose.ui.geometry.Offset(startX, y),
                        end = androidx.compose.ui.geometry.Offset(activeEndX, y),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, title ->
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
                            if (isActive) PrimaryTeal else MaterialTheme.colorScheme.surfaceVariant
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
                            color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Minimal Label
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isActive) PrimaryTeal else MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}
