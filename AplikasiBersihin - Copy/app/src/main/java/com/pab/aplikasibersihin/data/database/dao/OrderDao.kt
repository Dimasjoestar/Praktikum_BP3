package com.pab.aplikasibersihin.data.database.dao

import androidx.room.*
import com.pab.aplikasibersihin.data.database.entity.OrderEntity
import com.pab.aplikasibersihin.data.model.OrderStatus
import kotlinx.coroutines.flow.Flow

data class OrderDetails(
    val order: OrderEntity,
    val serviceName: String,
    val customerName: String
)

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    fun getAllOrdersFlow(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY createdAt DESC")
    fun getOrdersByUserIdFlow(userId: Long): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE id = :id LIMIT 1")
    suspend fun getOrderById(id: Long): OrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("UPDATE orders SET status = :status, updatedAt = :updatedAt WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: Long, status: OrderStatus, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM orders")
    fun getOrderCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM orders WHERE status = :status")
    fun getOrderCountByStatusFlow(status: OrderStatus): Flow<Int>

    @Query("SELECT SUM(total) FROM orders WHERE status != 'CANCELLED'")
    fun getTotalRevenueFlow(): Flow<Double?>
}
