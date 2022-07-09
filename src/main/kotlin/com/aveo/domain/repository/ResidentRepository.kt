package com.aveo.domain.repository

import com.aveo.db.Resident
import kotlinx.coroutines.flow.Flow

interface ResidentRepository {

    suspend fun getResidentById(id: Long) : Resident?

    fun getAllResidents() : Flow<List<Resident>>

    suspend fun getResidentByName(lastName: String) : Resident?

    suspend fun getResidentByUnitNumber(unitNumber: Long) : Resident?

    suspend fun insertResident(
        unitNumber: Long,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String
    )

    suspend fun deleteResidentById(id: Long)
}