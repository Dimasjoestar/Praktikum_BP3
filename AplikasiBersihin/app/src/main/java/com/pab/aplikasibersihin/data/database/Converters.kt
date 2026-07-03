package com.pab.aplikasibersihin.data.database

import androidx.room.TypeConverter
import com.pab.aplikasibersihin.data.model.UserRole
import com.pab.aplikasibersihin.data.model.OrderStatus
import com.pab.aplikasibersihin.data.model.PaymentMethod
import com.pab.aplikasibersihin.data.model.PickupStatus

class Converters {
    @TypeConverter
    fun fromUserRole(value: UserRole): String = value.name

    @TypeConverter
    fun toUserRole(value: String): UserRole = UserRole.valueOf(value)

    @TypeConverter
    fun fromOrderStatus(value: OrderStatus): String = value.name

    @TypeConverter
    fun toOrderStatus(value: String): OrderStatus = OrderStatus.valueOf(value)

    @TypeConverter
    fun fromPaymentMethod(value: PaymentMethod): String = value.name

    @TypeConverter
    fun toPaymentMethod(value: String): PaymentMethod = PaymentMethod.valueOf(value)

    @TypeConverter
    fun fromPickupStatus(value: PickupStatus): String = value.name

    @TypeConverter
    fun toPickupStatus(value: String): PickupStatus = PickupStatus.valueOf(value)
}
