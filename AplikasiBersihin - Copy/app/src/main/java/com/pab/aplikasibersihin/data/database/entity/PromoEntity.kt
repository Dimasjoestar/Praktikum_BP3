package com.pab.aplikasibersihin.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promos")
data class PromoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val discountPercent: Double, // e.g. 0.10 for 10%
    val minOrderAmount: Double,
    val validUntil: Long,
    val isActive: Boolean = true
)
