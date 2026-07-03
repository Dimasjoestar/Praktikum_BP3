package com.pab.aplikasibersihin.data.model

enum class UserRole {
    CUSTOMER, ADMIN, OFFICER
}

enum class MemberLevel(val minXp: Int, val discount: Double) {
    BRONZE(0, 0.05),
    SILVER(500, 0.10),
    GOLD(1500, 0.15),
    PLATINUM(3000, 0.20);

    companion object {
        fun getLevelFromXp(xp: Int): MemberLevel {
            return when {
                xp >= PLATINUM.minXp -> PLATINUM
                xp >= GOLD.minXp -> GOLD
                xp >= SILVER.minXp -> SILVER
                else -> BRONZE
            }
        }
    }
}

enum class OrderStatus(val displayName: String) {
    PENDING("Menunggu Konfirmasi"),
    CONFIRMED("Diterima"),
    PROCESSING("Diproses"),
    DELIVERING("Diantar"),
    DONE("Selesai"),
    CANCELLED("Dibatalkan")
}

enum class PaymentMethod(val displayName: String) {
    CASH("Tunai"),
    TRANSFER("Transfer Bank"),
    EWALLET("E-Wallet")
}

enum class PickupStatus(val displayName: String) {
    ASSIGNED("Ditugaskan"),
    ON_THE_WAY("Dalam Perjalanan"),
    PICKED_UP("Barang Diambil"),
    DELIVERING("Sedang Diantar"),
    DELIVERED("Selesai Diantar")
}
