package com.aveo.di

import com.aveo.data.repository.ResidentRepositoryImpl
import com.aveo.data.repository.UserRepositoryImpl
import com.aveo.db.AveoDatabase
import com.aveo.domain.repository.ResidentRepository
import com.aveo.domain.repository.UserRepository
import dagger.Provides
import javax.inject.Singleton

@dagger.Module
class AppModule {

    @Singleton
    @Provides
    fun provideResidentRepository(aveoDatabase: AveoDatabase) : ResidentRepository  {
        return ResidentRepositoryImpl(aveoDatabase)
    }

    @Singleton
    @Provides
    fun provideUserRepository(aveoDatabase: AveoDatabase) : UserRepository {
        return UserRepositoryImpl(aveoDatabase)
    }
}