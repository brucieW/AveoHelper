package com.aveo.domain.repository

import com.aveo.db.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(userName: String) : User?

    fun getAllUsers() : Flow<List<User>>

    suspend fun insertUser(
        userName: String,
        password: String,
        loggedIn: Long
    )

    suspend fun deleteUser(userName: String)

    suspend fun getLoggedInUser() : User?

    suspend fun setLoggedInUser(userName: String)

    suspend fun deleteLoggedInUser(userName: String)
}
