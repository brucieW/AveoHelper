package com.aveo.data.repository

import com.aveo.domain.repository.UserRepository
import com.aveo.db.AveoDatabase
import com.aveo.db.User
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    db: AveoDatabase
) : UserRepository {

    private val queries = db.usersQueries

    override suspend fun getUser(userName: String): User? {
        return withContext(Dispatchers.IO) {
            queries.getUser(userName).executeAsOneOrNull()
        }
    }

    override fun getAllUsers(): Flow<List<User>> {
        return queries.getAllUsers().asFlow().mapToList()
    }

    override suspend fun insertUser(userName: String, password: String) {
        insertUser(userName, password)
    }

    override suspend fun deleteUser(userName: String) {
        return withContext(Dispatchers.IO) {
            queries.deleteUser(userName)
        }
    }
}