package com.aveo.di

import com.aveo.data.repository.ResidentRepositoryImpl
import com.aveo.data.repository.UserRepositoryImpl
import com.aveo.db.AveoDatabase
import com.aveo.domain.repository.ResidentRepository
import com.aveo.domain.repository.UserRepository
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val repositoriesModule = DI.Module("Repositories") {
    bind<AveoDatabase> {
        singleton {
            val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:c:/AveoDatabase/aveo.db")
            AveoDatabase.invoke(driver)
        }
    }

    bind<UserRepository> { singleton { UserRepositoryImpl(instance()) }}
    bind<ResidentRepository> { singleton { ResidentRepositoryImpl(instance()) }}
}