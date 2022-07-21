package com.aveo.data.repository

import com.aveo.db.AveoDatabase
import com.aveo.db.Resident
import com.aveo.domain.repository.ResidentRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ResidentRepositoryImpl(
    db: AveoDatabase
) : ResidentRepository {

    private val queries = db.residentsQueries

    override fun getResidentsByLastName(): Flow<List<Resident>> {
        return queries.getResidentsByLastName().asFlow().mapToList()
    }

    override fun getResidentsBelowUnit(unitNumber: String): Flow<List<Resident>> {
        return queries.getResidentsBelowUnit(unitNumber).asFlow().mapToList()
    }

    override fun getResidentsAboveUnit(unitNumber: String): Flow<List<Resident>> {
        return queries.getResidentsAboveUnit(unitNumber).asFlow().mapToList()
    }

    override suspend fun getResidentByUnitNumber(unitNumber: String): Resident? {
        return withContext(Dispatchers.Main) {
            queries.getResidentByUnitNumber(unitNumber).executeAsOneOrNull()
        }
    }

    override suspend fun getResidentByName(lastName: String): Resident? {
        return withContext(Dispatchers.Main) {
            queries.getResidentByName(lastName).executeAsOneOrNull()
        }
    }

    override suspend fun insertResident(
        unitNumber: String,
        firstName1: String,
        firstName2: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String,
        phoneNumberId: String,
        email: String,
        isIndependentLiving: Boolean,
        onResidentsCommittee: Boolean,
        isCommissionerForDeclarations: Boolean
    ) {
        return withContext(Dispatchers.Main) {
            queries.insertResident(
                unitNumber, firstName1, firstName2, lastName,
                phoneNumber, mobileNumber, phoneNumberId, email,
                isIndependentLiving, onResidentsCommittee, isCommissionerForDeclarations
            )
        }
    }
}
