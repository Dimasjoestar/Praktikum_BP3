package com.pab.moneytracker.model

data class Transaction(
    val id: Long = 0,
    val userId: Long = 0,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: Category = Category.OTHER,
    val date: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis()
)

enum class TransactionType {
    INCOME,
    EXPENSE
}
