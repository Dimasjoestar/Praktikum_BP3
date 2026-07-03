package com.pab.aplikasibersihin.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pab.aplikasibersihin.data.model.OrderStatus

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val serviceId: Long,
    val weight: Double, // in kg
    val subtotal: Double,
    val discount: Double,
    val total: Double,
    val status: OrderStatus = OrderStatus.PENDING,
    val pickupType: String, // "ANTAR_SENDIRI" or "PICKUP"
    val pickupAddress: String,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
