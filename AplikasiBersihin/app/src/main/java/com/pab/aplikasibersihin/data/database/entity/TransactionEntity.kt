package com.pab.aplikasibersihin.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pab.aplikasibersihin.data.model.PaymentMethod

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderId: Long,
    val amount: Double,
    val paymentMethod: PaymentMethod,
    val status: String = "PENDING", // "PENDING", "PAID", "FAILED"
    val paidAt: Long = System.currentTimeMillis()
)
