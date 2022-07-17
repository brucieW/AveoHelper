package com.aveo.domain.repository

import com.aveo.db.Resident
import kotlinx.coroutines.flow.Flow

interface ResidentRepository {

    fun getResidentsByLastName() : Flow<List<Resident>>

    fun getResidentsBelowUnit(unitNumber: Long) : Flow<List<Resident>>

    fun getResidentsAboveUnit(unitNumber: Long) : Flow<List<Resident>>

    suspend fun getResidentByUnitNumber(unitNumber: Long) : Resident?

    suspend fun getResidentByName(lastName: String) : Resident?

    suspend fun insertResident(
        unitNumber: Long,
        firstName1: String = "",
        firstName2: String = "",
        lastName: String = "",
        phoneNumber: String = "",
        mobileNumber1: String = "",
        mobileNumber2: String = "",
        mobileNumberId: Long = 0,
        onResidentsCommittee: Boolean = false,
        isCommissionerForDeclarations: Boolean = false
    )

    suspend fun deleteResidentByUnitNumber(id: Long)
}