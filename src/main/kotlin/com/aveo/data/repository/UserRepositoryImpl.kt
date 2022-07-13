package com.aveo.data.repository

import com.aveo.domain.repository.UserRepository
import com.aveo.db.AveoDatabase
import com.aveo.db.User
import com.aveo.di.dbDriver
import com.aveo.presentation.di
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.kodein.di.instance

class UserRepositoryImpl(
    private val db: AveoDatabase
) : UserRepository {

    private val queries = db.usersQueries

    init {
        CoroutineScope(Dispatchers.Main).launch {
            dbDriver?.execute(
                null, "CREATE TABLE IF NOT EXISTS user (\n" +
                        "    userName TEXT NOT NULL UNIQUE PRIMARY KEY,\n" +
                        "    password TEXT NOT NULL,\n" +
                        "    loggedIn INTEGER DEFAULT 0\n" +
                        ");\n" +
                        "\n", 0, null
            )
            dbDriver?.execute(
                null, "CREATE TABLE IF NOT EXISTS resident (\n" +
                        "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                        "    unitNumber INTEGER NOT NULL,\n" +
                        "    firstName TEXT NOT NULL,\n" +
                        "    lastName TEXT NOT NULL,\n" +
                        "    phoneNumber TEXT,\n" +
                        "    mobileNumber TEXT\n" +
                        ");", 0, null
            )

            val repository: UserRepository by di.instance()

            val user = repository.getUser("admin")

            if (user == null) {
                dbDriver?.execute(
                    null,
                    "INSERT INTO user (userName, password) VALUES ('admin', 'admin')",
                    0,
                    null
                )
            }
        }
    }

    override suspend fun getUser(userName: String): User? {
        return withContext(Dispatchers.Main) {
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
        return withContext(Dispatchers.Main) {
            queries.deleteUser(userName)
        }
    }

    override suspend fun getLoggedInUser() : User? {
        return withContext(Dispatchers.Main) {
            queries.getLoggedInUser().executeAsOneOrNull()
        }
    }

    override suspend fun setLoggedInUser(userName: String) {
        return withContext(Dispatchers.Main) {
            queries.setLoggedInUser(userName)
        }
    }

    override suspend fun deleteLoggedInUser(userName: String) {
        return withContext(Dispatchers.Main) {
            queries.deleteLoggedInUser(userName)
        }
    }
}