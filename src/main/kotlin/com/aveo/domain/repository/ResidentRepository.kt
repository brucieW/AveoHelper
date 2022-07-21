package com.aveo.domain.repository

import com.aveo.db.Resident
import kotlinx.coroutines.flow.Flow

interface ResidentRepository {

    fun getResidentsByLastName() : Flow<List<Resident>>

    fun getResidentsBelowUnit(unitNumber: String) : Flow<List<Resident>>

    fun getResidentsAboveUnit(unitNumber: String) : Flow<List<Resident>>

    suspend fun getResidentByUnitNumber(unitNumber: String) : Resident?

    suspend fun getResidentByName(lastName: String) : Resident?

    suspend fun insertResident(
        unitNumber: String,
        firstName1: String = "",
        firstName2: String = "",
        lastName: String = "",
        phoneNumber: String = "",
        mobileNumber: String = "",
        phoneNumberId: String = "",
        email: String = "",
        isIndependentLiving: Boolean = true,
        onResidentsCommittee: Boolean = false,
        isCommissionerForDeclarations: Boolean = false
    )
}