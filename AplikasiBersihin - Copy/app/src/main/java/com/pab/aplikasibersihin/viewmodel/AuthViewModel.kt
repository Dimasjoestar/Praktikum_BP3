package com.pab.aplikasibersihin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pab.aplikasibersihin.data.database.AppDatabase
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.data.repository.AuthRepository
import com.pab.aplikasibersihin.utils.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application, viewModelScope)
    private val authRepository = AuthRepository(database.userDao())
    private val sessionManager = SessionManager(application)

    private val _loginState = MutableStateFlow<Result<UserEntity>?>(null)
    val loginState: StateFlow<Result<UserEntity>?> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<Result<Long>?>(null)
    val registerState: StateFlow<Result<Long>?> = _registerState.asStateFlow()

    val isLoggedIn = sessionManager.isLoggedInFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val currentUserId = sessionManager.userIdFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val currentUserRole = sessionManager.userRoleFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            _loginState.value = null
            val user = authRepository.login(email, passwordHash)
            if (user != null) {
                sessionManager.saveSession(user.id, user.email, user.role.name)
                _loginState.value = Result.success(user)
            } else {
                _loginState.value = Result.failure(Exception("Email atau password salah"))
            }
        }
    }

    fun register(name: String, email: String, passwordHash: String, phone: String, address: String) {
        viewModelScope.launch {
            _registerState.value = null
            val result = authRepository.register(name, email, passwordHash, phone, address)
            _registerState.value = result
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _loginState.value = null
            _registerState.value = null
        }
    }

    fun resetState() {
        _loginState.value = null
        _registerState.value = null
    }

    fun getUserFlow(userId: Long): Flow<UserEntity?> {
        return authRepository.getUserFlow(userId)
    }
}
