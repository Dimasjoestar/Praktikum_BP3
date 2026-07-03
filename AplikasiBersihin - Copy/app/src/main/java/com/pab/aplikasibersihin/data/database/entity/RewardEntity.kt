package com.pab.aplikasibersihin.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class RewardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val xpCost: Int,
    val type: String, // "DISCOUNT", "FREE_ITEM", "OTHER"
    val isActive: Boolean = true
)
