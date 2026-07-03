package com.pab.moneytracker.model

enum class Category(val displayName: String, val iconRes: String) {
    FOOD_DRINK("Makanan & Minuman", "ic_food"),
    SHOPPING("Shopping", "ic_shopping"),
    HOME("Rumah", "ic_home_category"),
    TRANSPORT("Transportasi", "ic_transport"),
    VEHICLE("Kendaraan", "ic_vehicle"),
    INVESTMENT("Investasi", "ic_investment"),
    OTHER("Lainnya", "ic_other")
}
