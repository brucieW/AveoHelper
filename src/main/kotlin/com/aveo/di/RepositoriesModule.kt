package com.aveo.di

import com.aveo.data.repository.ResidentRepositoryImpl
import com.aveo.data.repository.UserRepositoryImpl
import com.aveo.db.AveoDatabase
import com.aveo.domain.repository.ResidentRepository
import com.aveo.domain.repository.UserRepository
import com.aveo.presentation.di
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

var dbDriver: JdbcSqliteDriver? = null
var tablesCreated: Boolean = false

val repositoriesModule = DI.Module("Repositories") {
//    bind<JdbcSqliteDriver> {
//        singleton { JdbcSqliteDriver("jdbc:sqlite:c:/AveoDatabase/aveo.db") }
//    }

    bind<AveoDatabase> {
        singleton {
            dbDriver = JdbcSqliteDriver("jdbc:sqlite:c:/AveoDatabase/aveo.db")
            AveoDatabase.invoke(dbDriver!!)
        }
    }

    bind<UserRepository> { singleton { UserRepositoryImpl(instance()) }}
    bind<ResidentRepository> { singleton { ResidentRepositoryImpl(instance()) }}
}

fun initTables() {
    if (!tablesCreated) {
        tablesCreated = true
        dbDriver?.execute(
            null, "CREATE TABLE IF NOT EXISTS user (\n" +
                    "    userName TEXT NOT NULL UNIQUE PRIMARY KEY,\n" +
                    "    password TEXT NOT NULL\n" +
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

        runBlocking {
            launch {
                val repository: UserRepository by di.instance()

                val user = repository.getUser("admin")

                if (user == null) {
                    dbDriver?.execute(null, "INSERT INTO user (userName, password) VALUES ('admin', 'admin')", 0, null)
                }
            }
        }
    }
}
