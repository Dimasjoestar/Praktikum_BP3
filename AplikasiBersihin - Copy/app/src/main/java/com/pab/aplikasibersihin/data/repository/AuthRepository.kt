package com.pab.aplikasibersihin.data.repository

import com.pab.aplikasibersihin.data.database.dao.UserDao
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import com.pab.aplikasibersihin.data.model.UserRole
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val userDao: UserDao) {

    suspend fun login(email: String, passwordHash: String): UserEntity? {
        val user = userDao.getUserByEmail(email) ?: return null
        return if (user.passwordHash == passwordHash) user else null
    }

    suspend fun register(
        name: String,
        email: String,
        passwordHash: String,
        phone: String,
        address: String
    ): Result<Long> {
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) {
            return Result.failure(Exception("Email sudah terdaftar"))
        }

        val newUser = UserEntity(
            name = name,
            email = email,
            passwordHash = passwordHash,
            phone = phone,
            address = address,
            role = UserRole.CUSTOMER
        )
        return try {
            val id = userDao.insertUser(newUser)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getUserFlow(userId: Long): Flow<UserEntity?> {
        return userDao.getUserByIdFlow(userId)
    }

    suspend fun getUserById(userId: Long): UserEntity? {
        return userDao.getUserById(userId)
    }
}
