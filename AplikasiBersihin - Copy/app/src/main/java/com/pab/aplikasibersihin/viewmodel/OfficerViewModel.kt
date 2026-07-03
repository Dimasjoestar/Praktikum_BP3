package com.pab.aplikasibersihin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pab.aplikasibersihin.data.database.AppDatabase
import com.pab.aplikasibersihin.data.database.dao.PickupWithDetails
import com.pab.aplikasibersihin.data.database.entity.PickupEntity
import com.pab.aplikasibersihin.data.model.PickupStatus
import com.pab.aplikasibersihin.data.repository.LaundryRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OfficerViewModel(application: Application) : AndroidViewModel(application) {

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

    fun getPickupsForOfficer(officerId: Long): Flow<List<PickupEntity>> {
        return laundryRepository.getPickupsByOfficerFlow(officerId)
    }

    fun getPickupDetails(pickupId: Long): Flow<PickupWithDetails?> = flow {
        val pickup = laundryRepository.getPickupById(pickupId)
        if (pickup != null) {
            val order = laundryRepository.getOrderById(pickup.orderId)
            val service = order?.let { laundryRepository.getServiceById(it.serviceId) }
            val customer = order?.let { database.userDao().getUserById(it.userId) }

            if (order != null && service != null && customer != null) {
                emit(
                    PickupWithDetails(
                        pickup = pickup,
                        customerName = customer.name,
                        customerPhone = customer.phone,
                        customerAddress = order.pickupAddress,
                        serviceName = service.name,
                        weight = order.weight
                    )
                )
            } else {
                emit(null)
            }
        } else {
            emit(null)
        }
    }

    fun updatePickupStatus(pickupId: Long, status: PickupStatus, notes: String) {
        viewModelScope.launch {
            laundryRepository.updatePickupStatus(pickupId, status, notes)
        }
    }
}
