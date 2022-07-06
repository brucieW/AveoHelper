package com.aveo.data

import com.aveo.db.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    suspend fun getUserById(id: Long) : User?

    fun getAllUsers() : Flow<List<User>>

    suspend fun getUserByName(userName: String) : User?

    suspend fun insertUser(
        userName: String,
        password: String,
    )

    suspend fun deleteUserById(id: Long)
}