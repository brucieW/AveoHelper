package com.aveo.domain.use_cases

import com.aveo.db.User
import com.aveo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetUser(
    private val userRepository: UserRepository
) {
    operator suspend fun invoke(userName: String) : User? {
        return withContext(Dispatchers.IO) {
            userRepository.getUser(userName)
        }
    }
}