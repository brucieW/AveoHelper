package com.aveo.domain.use_cases

import com.aveo.db.User
import com.aveo.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsers(
    private val userRepository: UserRepository
) {
    operator fun invoke() : Flow<List<User>> {
        return userRepository.getAllUsers()
    }
}