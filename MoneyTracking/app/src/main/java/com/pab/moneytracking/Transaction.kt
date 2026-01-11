package com.pab.moneytracking

data class Transaction(
    val id: Int,
    val description: String,
    val amount: Double,
    val type: String // "Income" or "Expense"
)