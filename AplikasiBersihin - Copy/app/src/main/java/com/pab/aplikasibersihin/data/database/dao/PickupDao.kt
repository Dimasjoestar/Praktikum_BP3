package com.pab.aplikasibersihin.data.database.dao

import androidx.room.*
import com.pab.aplikasibersihin.data.database.entity.PickupEntity
import com.pab.aplikasibersihin.data.model.PickupStatus
import kotlinx.coroutines.flow.Flow

data class PickupWithDetails(
    val pickup: PickupEntity,
    val customerName: String,
    val customerPhone: String,
    val customerAddress: String,
    val serviceName: String,
    val weight: Double
)

@Dao
interface PickupDao {
    @Query("SELECT * FROM pickups ORDER BY scheduledAt DESC")
    fun getAllPickupsFlow(): Flow<List<PickupEntity>>

    @Query("SELECT * FROM pickups WHERE officerId = :officerId ORDER BY scheduledAt DESC")
    fun getPickupsByOfficerFlow(officerId: Long): Flow<List<PickupEntity>>

    @Query("SELECT * FROM pickups WHERE id = :id LIMIT 1")
    suspend fun getPickupById(id: Long): PickupEntity?

    @Query("SELECT * FROM pickups WHERE orderId = :orderId LIMIT 1")
    suspend fun getPickupByOrderId(orderId: Long): PickupEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPickup(pickup: PickupEntity): Long

    @Update
    suspend fun updatePickup(pickup: PickupEntity)

    @Query("UPDATE pickups SET status = :status, completedAt = :completedAt, notes = :notes WHERE id = :pickupId")
    suspend fun updatePickupStatus(pickupId: Long, status: PickupStatus, completedAt: Long?, notes: String)
}
