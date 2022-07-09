package com.aveo.domain.use_cases

import com.aveo.db.User

data class AveoUseCases(
    val getUsers: GetUsers,
    val getUser: GetUser
)
