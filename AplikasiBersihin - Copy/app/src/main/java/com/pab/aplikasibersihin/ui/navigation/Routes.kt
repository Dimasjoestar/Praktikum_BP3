package com.pab.aplikasibersihin.ui.navigation

object Routes {
    // Auth Routes
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"

    // Customer Routes
    const val CUSTOMER_MAIN = "customer_main"
    const val CUSTOMER_NEW_ORDER = "customer_new_order"
    const val CUSTOMER_ORDER_DETAIL = "customer_order_detail/{orderId}"
    const val CUSTOMER_PROMO = "customer_promo"
    const val CUSTOMER_REWARD_CLAIM = "customer_reward_claim"

    fun customerOrderDetail(orderId: Long): String {
        return "customer_order_detail/$orderId"
    }

    // Admin Routes
    const val ADMIN_MAIN = "admin_main"
    const val ADMIN_MANAGE_SERVICES = "admin_manage_services"
    const val ADMIN_MANAGE_PROMOS = "admin_manage_promos"
    const val ADMIN_MANAGE_REWARDS = "admin_manage_rewards"
    const val ADMIN_MANAGE_USERS = "admin_manage_users"
    const val ADMIN_REPORTS = "admin_reports"
    const val ADMIN_ORDER_DETAIL = "admin_order_detail/{orderId}"

    fun adminOrderDetail(orderId: Long): String {
        return "admin_order_detail/$orderId"
    }

    // Officer Routes
    const val OFFICER_MAIN = "officer_main"
    const val OFFICER_PICKUP_DETAIL = "officer_pickup_detail/{pickupId}"

    fun officerPickupDetail(pickupId: Long): String {
        return "officer_pickup_detail/$pickupId"
    }
}
