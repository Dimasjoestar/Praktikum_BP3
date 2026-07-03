package com.pab.aplikasibersihin.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pab.aplikasibersihin.data.model.PickupStatus

@Entity(tableName = "pickups")
data class PickupEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderId: Long,
    val officerId: Long,
    val status: PickupStatus = PickupStatus.ASSIGNED,
    val notes: String = "",
    val scheduledAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)
