package com.aveo.domain.repository

import com.aveo.db.Resident
import kotlinx.coroutines.flow.Flow

interface ResidentRepository {

    suspend fun getResidentByUnitNumber(unitNumber: Long) : Resident?

    fun getAllResidents() : Flow<List<Resident>>

    suspend fun getResidentByName(lastName: String) : Resident?

    suspend fun insertResident(
        unitNumber: Long,
        firstName1: String,
        firstName2: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String,
        mobileNumberId: Long
    )

    suspend fun deleteResidentByUnitNumber(id: Long)
}