package com.pab.aplikasibersihin.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val pricePerKg: Double,
    val estimationDays: Int,
    val isActive: Boolean = true,
    val category: String = "Regular"
)
