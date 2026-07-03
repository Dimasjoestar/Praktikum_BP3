package com.pab.aplikasibersihin.data.database.dao

import androidx.room.*
import com.pab.aplikasibersihin.data.database.entity.PromoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PromoDao {
    @Query("SELECT * FROM promos WHERE isActive = 1 AND validUntil >= :currentTime")
    fun getActivePromosFlow(currentTime: Long): Flow<List<PromoEntity>>

    @Query("SELECT * FROM promos")
    fun getAllPromosFlow(): Flow<List<PromoEntity>>

    @Query("SELECT * FROM promos WHERE id = :id LIMIT 1")
    suspend fun getPromoById(id: Long): PromoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromo(promo: PromoEntity): Long

    @Update
    suspend fun updatePromo(promo: PromoEntity)

    @Delete
    suspend fun deletePromo(promo: PromoEntity)
}
