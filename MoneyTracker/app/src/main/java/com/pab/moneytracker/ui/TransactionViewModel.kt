package com.pab.moneytracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pab.moneytracker.data.AppDatabase
import com.pab.moneytracker.data.Transaction
import com.pab.moneytracker.data.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository

    val allTransactions: StateFlow<List<Transaction>>
    val totalIncome: StateFlow<Double>
    val totalExpense: StateFlow<Double>
    val balance: StateFlow<Double>

    init {
        val dao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.allTransactions.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        totalIncome = repository.totalIncome.map { it ?: 0.0 }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )
        totalExpense = repository.totalExpense.map { it ?: 0.0 }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )
        balance = totalIncome.map { income ->
            income - (totalExpense.value)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )
    }

    fun addTransaction(title: String, amount: Double, type: String) {
        viewModelScope.launch {
            repository.insert(
                Transaction(
                    title = title,
                    amount = amount,
                    type = type,
                    date = System.currentTimeMillis()
                )
            )
        }
    }
}
