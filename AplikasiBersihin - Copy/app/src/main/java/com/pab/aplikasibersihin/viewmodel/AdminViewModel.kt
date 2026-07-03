package com.pab.aplikasibersihin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pab.aplikasibersihin.data.database.AppDatabase
import com.pab.aplikasibersihin.data.database.entity.*
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.data.model.UserRole
import com.pab.aplikasibersihin.data.repository.LaundryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val laundryRepository = LaundryRepository(
        database.userDao(),
        database.serviceDao(),
        database.orderDao(),
        database.transactionDao(),
        database.rewardDao(),
        database.promoDao(),
        database.pickupDao()
    )

    // Exposed lists
    val allOrders: StateFlow<List<OrderEntity>> = laundryRepository.getAllOrdersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allServices: StateFlow<List<ServiceEntity>> = laundryRepository.getAllServicesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allPromos: StateFlow<List<PromoEntity>> = laundryRepository.getAllPromosFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allRewards: StateFlow<List<RewardEntity>> = laundryRepository.getAllRewardsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allUsers: StateFlow<List<UserEntity>> = laundryRepository.getAllUsersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allPickups: StateFlow<List<PickupEntity>> = laundryRepository.getAllPickupsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Statistics ---
    val totalOrdersCount: StateFlow<Int> = laundryRepository.getOrderCountFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalRevenue: StateFlow<Double> = laundryRepository.getTotalRevenueFlow()
        .map { it ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val pendingOrdersCount: StateFlow<Int> = laundryRepository.getOrderCountByStatusFlow(OrderStatus.PENDING)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val processingOrdersCount: StateFlow<Int> = laundryRepository.getOrderCountByStatusFlow(OrderStatus.PROCESSING)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val completedOrdersCount: StateFlow<Int> = laundryRepository.getOrderCountByStatusFlow(OrderStatus.DELIVERED)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // --- Service Operations ---
    fun saveService(id: Long, name: String, description: String, price: Double, estimation: Int, category: String) {
        viewModelScope.launch {
            val service = ServiceEntity(
                id = id,
                name = name,
                description = description,
                pricePerKg = price,
                estimationDays = estimation,
                category = category,
                isActive = true
            )
            if (id == 0L) {
                laundryRepository.insertService(service)
            } else {
                laundryRepository.updateService(service)
            }
        }
    }

    fun deleteService(service: ServiceEntity) {
        viewModelScope.launch {
            laundryRepository.deleteService(service)
        }
    }

    // --- Promo Operations ---
    fun savePromo(id: Long, name: String, description: String, discountPercent: Double, minOrder: Double, validDays: Int) {
        viewModelScope.launch {
            val promo = PromoEntity(
                id = id,
                name = name,
                description = description,
                discountPercent = discountPercent,
                minOrderAmount = minOrder,
                validUntil = System.currentTimeMillis() + (validDays * 24L * 60L * 60L * 1000L),
                isActive = true
            )
            if (id == 0L) {
                laundryRepository.insertPromo(promo)
            } else {
                laundryRepository.updatePromo(promo)
            }
        }
    }

    fun deletePromo(promo: PromoEntity) {
        viewModelScope.launch {
            laundryRepository.deletePromo(promo)
        }
    }

    // --- Reward Operations ---
    fun saveReward(id: Long, name: String, description: String, xpCost: Int, type: String) {
        viewModelScope.launch {
            val reward = RewardEntity(
                id = id,
                name = name,
                description = description,
                xpCost = xpCost,
                type = type,
                isActive = true
            )
            if (id == 0L) {
                laundryRepository.insertReward(reward)
            } else {
                laundryRepository.updateReward(reward)
            }
        }
    }

    fun deleteReward(reward: RewardEntity) {
        viewModelScope.launch {
            laundryRepository.deleteReward(reward)
        }
    }

    // --- Order & Status Updates ---
    fun updateOrderStatus(orderId: Long, status: OrderStatus) {
        viewModelScope.launch {
            laundryRepository.updateOrderStatus(orderId, status)
        }
    }

    fun assignOfficerToPickup(pickupId: Long, officerId: Long) {
        viewModelScope.launch {
            laundryRepository.assignOfficerToPickup(pickupId, officerId)
        }
    }

    // Helpers
    fun getOfficers(): Flow<List<UserEntity>> {
        return allUsers.map { list -> list.filter { it.role == UserRole.OFFICER } }
    }
}
