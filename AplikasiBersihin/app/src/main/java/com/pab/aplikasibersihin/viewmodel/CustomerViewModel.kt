package com.pab.aplikasibersihin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pab.aplikasibersihin.data.database.AppDatabase
import com.pab.aplikasibersihin.data.database.entity.*
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.data.model.PaymentMethod
import com.pab.aplikasibersihin.data.repository.LaundryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CustomerViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val laundryRepository = LaundryRepository(
        database.userDao(),
        database.serviceDao(),
        database.orderDao(),
        database.transactionDao(),
        database.rewardDao(),
        database.promoDao(),
        database.pickupDao(),
        com.pab.aplikasibersihin.utils.NotificationHelper(application)
    )

    private val _orderResult = MutableStateFlow<Result<Long>?>(null)
    val orderResult: StateFlow<Result<Long>?> = _orderResult.asStateFlow()

    private val _claimResult = MutableStateFlow<Result<Boolean>?>(null)
    val claimResult: StateFlow<Result<Boolean>?> = _claimResult.asStateFlow()

    // Exposed Flows
    val activeServices: StateFlow<List<ServiceEntity>> = laundryRepository.getActiveServicesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activePromos: StateFlow<List<PromoEntity>> = laundryRepository.getActivePromosFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeRewards: StateFlow<List<RewardEntity>> = laundryRepository.getActiveRewardsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getOrdersByUserId(userId: Long): Flow<List<OrderEntity>> {
        return laundryRepository.getOrdersByUserIdFlow(userId)
    }

    fun getQueueCountBeforeOrder(createdAt: Long): Flow<Int> {
        return laundryRepository.getQueueCountBeforeOrderFlow(createdAt)
    }

    fun getOrderById(orderId: Long): Flow<OrderEntity?> = flow {
        emit(laundryRepository.getOrderById(orderId))
    }

    fun getTransactionByOrderId(orderId: Long): Flow<TransactionEntity?> = flow {
        emit(laundryRepository.getTransactionByOrderId(orderId))
    }

    fun createOrder(
        userId: Long,
        serviceId: Long,
        weight: Double,
        pickupType: String,
        pickupAddress: String,
        notes: String,
        promoDiscountPercent: Double = 0.0
    ) {
        viewModelScope.launch {
            _orderResult.value = null
            val result = laundryRepository.createOrder(
                userId, serviceId, weight, pickupType, pickupAddress, notes, promoDiscountPercent
            )
            _orderResult.value = result
        }
    }

    fun payOrder(orderId: Long, method: PaymentMethod, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val success = laundryRepository.completePayment(orderId, method)
            if (success) {
                onSuccess()
            }
        }
    }

    fun cancelOrder(orderId: Long) {
        viewModelScope.launch {
            laundryRepository.updateOrderStatus(orderId, OrderStatus.CANCELLED)
        }
    }

    fun claimReward(userId: Long, rewardId: Long) {
        viewModelScope.launch {
            _claimResult.value = null
            val result = laundryRepository.claimReward(userId, rewardId)
            _claimResult.value = result
        }
    }

    fun resetOrderResult() {
        _orderResult.value = null
    }

    fun resetClaimResult() {
        _claimResult.value = null
    }
}
