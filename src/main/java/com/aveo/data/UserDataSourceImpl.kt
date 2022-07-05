package com.aveo.data

import com.aveo.db.AveoDatabase
import com.aveo.db.User
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserDataSourceImpl(
    db: AveoDatabase
) : UserDataSource {

    private val queries = db.usersQueries

    override suspend fun getUserById(id: Long): User? {
        return withContext(Dispatchers.IO) {
            queries.getUserById(id).executeAsOneOrNull()
        }
    }

    override suspend fun getUserByName(userName: String): User? {
        return withContext(Dispatchers.IO) {
            queries.getUserByName(userName).executeAsOneOrNull()
        }
    }

    override fun getAllUsers(): Flow<List<User>> {
        return queries.getAllUsers().asFlow().mapToList()
    }

    override suspend fun insertUser(userName: String, password: String) {
        insertUser(userName, password)
    }

    override suspend fun deleteUserById(id: Long) {
        return withContext(Dispatchers.IO) {
            queries.deleteUserById(id)
        }
    }
}