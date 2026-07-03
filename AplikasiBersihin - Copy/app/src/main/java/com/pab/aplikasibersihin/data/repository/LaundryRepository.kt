package com.pab.aplikasibersihin.data.repository

import com.pab.aplikasibersihin.data.database.dao.*
import com.pab.aplikasibersihin.data.database.entity.*
import com.pab.aplikasibersihin.data.model.MemberLevel
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.data.model.PaymentMethod
import com.pab.aplikasibersihin.data.model.PickupStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class LaundryRepository(
    private val userDao: UserDao,
    private val serviceDao: ServiceDao,
    private val orderDao: OrderDao,
    private val transactionDao: TransactionDao,
    private val rewardDao: RewardDao,
    private val promoDao: PromoDao,
    private val pickupDao: PickupDao
) {

    // --- Services ---
    fun getActiveServicesFlow(): Flow<List<ServiceEntity>> = serviceDao.getActiveServicesFlow()
    fun getAllServicesFlow(): Flow<List<ServiceEntity>> = serviceDao.getAllServicesFlow()
    suspend fun getServiceById(id: Long) = serviceDao.getServiceById(id)
    suspend fun insertService(service: ServiceEntity) = serviceDao.insertService(service)
    suspend fun updateService(service: ServiceEntity) = serviceDao.updateService(service)
    suspend fun deleteService(service: ServiceEntity) = serviceDao.deleteService(service)

    // --- Orders ---
    fun getAllOrdersFlow(): Flow<List<OrderEntity>> = orderDao.getAllOrdersFlow()
    fun getOrdersByUserIdFlow(userId: Long): Flow<List<OrderEntity>> = orderDao.getOrdersByUserIdFlow(userId)
    suspend fun getOrderById(id: Long) = orderDao.getOrderById(id)

    suspend fun createOrder(
        userId: Long,
        serviceId: Long,
        weight: Double,
        pickupType: String,
        pickupAddress: String,
        notes: String,
        promoDiscountPercent: Double = 0.0
    ): Result<Long> {
        val service = serviceDao.getServiceById(serviceId) ?: return Result.failure(Exception("Layanan tidak ditemukan"))
        val user = userDao.getUserById(userId) ?: return Result.failure(Exception("Pengguna tidak ditemukan"))

        // Member discount
        val memberLevel = MemberLevel.getLevelFromXp(user.xp)
        val memberDiscountPercent = memberLevel.discount

        val subtotal = service.pricePerKg * weight
        // Combine member level discount + selected promo discount
        val totalDiscountPercent = memberDiscountPercent + promoDiscountPercent
        val discountAmount = subtotal * totalDiscountPercent
        val total = (subtotal - discountAmount).coerceAtLeast(0.0)

        val order = OrderEntity(
            userId = userId,
            serviceId = serviceId,
            weight = weight,
            subtotal = subtotal,
            discount = discountAmount,
            total = total,
            status = OrderStatus.PENDING,
            pickupType = pickupType,
            pickupAddress = pickupAddress,
            notes = notes
        )

        return try {
            val orderId = orderDao.insertOrder(order)
            
            // Create default transaction record
            val transaction = TransactionEntity(
                orderId = orderId,
                amount = total,
                paymentMethod = PaymentMethod.CASH,
                status = "PENDING"
            )
            transactionDao.insertTransaction(transaction)

            // If pickup requested, create pickup job with ASSIGNED status but unassigned officer (officerId = 0 or assigned later by Admin)
            if (pickupType == "PICKUP") {
                val pickup = PickupEntity(
                    orderId = orderId,
                    officerId = 0L, // 0 means unassigned
                    status = PickupStatus.ASSIGNED
                )
                pickupDao.insertPickup(pickup)
            }

            Result.success(orderId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateOrderStatus(orderId: Long, status: OrderStatus): Boolean {
        val order = orderDao.getOrderById(orderId) ?: return false
        orderDao.updateOrderStatus(orderId, status)

        // Business Logic: If order becomes DELIVERED (or DONE for non-pickup), award XP to user!
        // XP Formula: Rp 1.000 = 1 XP
        val isCompleted = status == OrderStatus.DELIVERED || 
                (status == OrderStatus.DONE && order.pickupType == "ANTAR_SENDIRI")

        if (isCompleted) {
            val user = userDao.getUserById(order.userId)
            if (user != null) {
                val xpEarned = (order.total / 1000).toInt().coerceAtLeast(5) // minimum 5 XP
                val newXp = user.xp + xpEarned
                val newLevel = MemberLevel.getLevelFromXp(newXp).name
                userDao.updateUserXpAndLevel(user.id, newXp, newLevel)
            }
        }
        return true
    }

    // --- Statistics (Admin) ---
    fun getOrderCountFlow(): Flow<Int> = orderDao.getOrderCountFlow()
    fun getOrderCountByStatusFlow(status: OrderStatus): Flow<Int> = orderDao.getOrderCountByStatusFlow(status)
    fun getTotalRevenueFlow(): Flow<Double?> = orderDao.getTotalRevenueFlow()
    fun getAllUsersFlow(): Flow<List<UserEntity>> = userDao.getAllUsersFlow()

    // --- Promos ---
    fun getActivePromosFlow(): Flow<List<PromoEntity>> = promoDao.getActivePromosFlow(System.currentTimeMillis())
    fun getAllPromosFlow(): Flow<List<PromoEntity>> = promoDao.getAllPromosFlow()
    suspend fun getPromoById(id: Long) = promoDao.getPromoById(id)
    suspend fun insertPromo(promo: PromoEntity) = promoDao.insertPromo(promo)
    suspend fun updatePromo(promo: PromoEntity) = promoDao.updatePromo(promo)
    suspend fun deletePromo(promo: PromoEntity) = promoDao.deletePromo(promo)

    // --- Rewards ---
    fun getActiveRewardsFlow(): Flow<List<RewardEntity>> = rewardDao.getActiveRewardsFlow()
    fun getAllRewardsFlow(): Flow<List<RewardEntity>> = rewardDao.getAllRewardsFlow()
    suspend fun getRewardById(id: Long) = rewardDao.getRewardById(id)
    suspend fun insertReward(reward: RewardEntity) = rewardDao.insertReward(reward)
    suspend fun updateReward(reward: RewardEntity) = rewardDao.updateReward(reward)
    suspend fun deleteReward(reward: RewardEntity) = rewardDao.deleteReward(reward)

    suspend fun claimReward(userId: Long, rewardId: Long): Result<Boolean> {
        val user = userDao.getUserById(userId) ?: return Result.failure(Exception("Pengguna tidak ditemukan"))
        val reward = rewardDao.getRewardById(rewardId) ?: return Result.failure(Exception("Reward tidak ditemukan"))

        if (user.xp < reward.xpCost) {
            return Result.failure(Exception("Poin XP tidak mencukupi untuk klaim reward ini"))
        }

        val newXp = user.xp - reward.xpCost
        val newLevel = MemberLevel.getLevelFromXp(newXp).name
        userDao.updateUserXpAndLevel(userId, newXp, newLevel)
        return Result.success(true)
    }

    // --- Pickups ---
    fun getAllPickupsFlow(): Flow<List<PickupEntity>> = pickupDao.getAllPickupsFlow()
    fun getPickupsByOfficerFlow(officerId: Long): Flow<List<PickupEntity>> = pickupDao.getPickupsByOfficerFlow(officerId)
    suspend fun getPickupById(id: Long) = pickupDao.getPickupById(id)
    suspend fun getPickupByOrderId(orderId: Long) = pickupDao.getPickupByOrderId(orderId)

    suspend fun assignOfficerToPickup(pickupId: Long, officerId: Long): Boolean {
        val pickup = pickupDao.getPickupById(pickupId) ?: return false
        val updatedPickup = pickup.copy(officerId = officerId)
        pickupDao.updatePickup(updatedPickup)

        // Also update order status to PICKUP
        val order = orderDao.getOrderById(pickup.orderId)
        if (order != null) {
            orderDao.updateOrderStatus(order.id, OrderStatus.PICKUP)
        }
        return true
    }

    suspend fun updatePickupStatus(pickupId: Long, status: PickupStatus, notes: String): Boolean {
        val pickup = pickupDao.getPickupById(pickupId) ?: return false
        val completedAt = if (status == PickupStatus.DELIVERED || status == PickupStatus.PICKED_UP) System.currentTimeMillis() else null
        pickupDao.updatePickupStatus(pickupId, status, completedAt, notes)

        // Propagate status change back to the main order
        val orderStatus = when (status) {
            PickupStatus.ASSIGNED -> OrderStatus.PICKUP
            PickupStatus.ON_THE_WAY -> OrderStatus.PICKUP
            PickupStatus.PICKED_UP -> OrderStatus.PROCESSING
            PickupStatus.DELIVERING -> OrderStatus.DONE
            PickupStatus.DELIVERED -> OrderStatus.DELIVERED
        }
        updateOrderStatus(pickup.orderId, orderStatus)
        return true
    }

    // --- Transactions & Payments ---
    fun getAllTransactionsFlow(): Flow<List<TransactionEntity>> = transactionDao.getAllTransactionsFlow()
    suspend fun getTransactionByOrderId(orderId: Long) = transactionDao.getTransactionByOrderId(orderId)
    suspend fun completePayment(orderId: Long, method: PaymentMethod): Boolean {
        val tx = transactionDao.getTransactionByOrderId(orderId) ?: return false
        val updatedTx = tx.copy(
            paymentMethod = method,
            status = "PAID",
            paidAt = System.currentTimeMillis()
        )
        transactionDao.updateTransaction(updatedTx)

        // If order was pending, confirm it
        val order = orderDao.getOrderById(orderId)
        if (order != null && order.status == OrderStatus.PENDING) {
            orderDao.updateOrderStatus(orderId, OrderStatus.CONFIRMED)
        }
        return true
    }
}
