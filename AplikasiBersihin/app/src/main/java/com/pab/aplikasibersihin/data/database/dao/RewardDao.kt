package com.pab.aplikasibersihin.data.database.dao

import androidx.room.*
import com.pab.aplikasibersihin.data.database.entity.RewardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RewardDao {
    @Query("SELECT * FROM rewards WHERE isActive = 1")
    fun getActiveRewardsFlow(): Flow<List<RewardEntity>>

    @Query("SELECT * FROM rewards")
    fun getAllRewardsFlow(): Flow<List<RewardEntity>>

    @Query("SELECT * FROM rewards WHERE id = :id LIMIT 1")
    suspend fun getRewardById(id: Long): RewardEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReward(reward: RewardEntity): Long

    @Update
    suspend fun updateReward(reward: RewardEntity)

    @Delete
    suspend fun deleteReward(reward: RewardEntity)
}
