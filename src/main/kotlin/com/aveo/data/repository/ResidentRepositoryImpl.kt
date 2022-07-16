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

    override suspend fun getResidentByUnitNumber(unitNumber: Long): Resident? {
        return withContext(Dispatchers.Main) {
            queries.getResidentByUnitNumber(unitNumber).executeAsOneOrNull()
        }
    }

    override fun getResidentsByUnit(): Flow<List<Resident>> {
        return queries.getResidentsByUnit().asFlow().mapToList()
    }

    override fun getResidentsByName(): Flow<List<Resident>> {
        return queries.getResidentsByName().asFlow().mapToList()
    }

    override suspend fun getResidentByName(lastName: String): Resident? {
        return withContext(Dispatchers.Main) {
            queries.getResidentByName(lastName).executeAsOneOrNull()
        }
    }

    override fun getResidentsBelowUnit(unitNumber: Long): Flow<List<Resident>> {
        return queries.getResidentsBelowUnit(unitNumber).asFlow().mapToList()
    }

    override fun getResidentsAboveUnit(unitNumber: Long): Flow<List<Resident>> {
        return queries.getResidentsAboveUnit(unitNumber).asFlow().mapToList()
    }

    override suspend fun insertResident(
        unitNumber: Long,
        firstName1: String,
        firstName2: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber1: String,
        mobileNumber2: String,
        mobileNumberId: Long,
        onResidentsCommittee: Boolean,
        isCommissionerForDeclarations: Boolean
    ) {
        return withContext(Dispatchers.Main) {
            queries.insertResident( unitNumber, firstName1, firstName2, lastName,
                                    phoneNumber, mobileNumber1, mobileNumber2, mobileNumberId,
                                    onResidentsCommittee, isCommissionerForDeclarations)
        }
    }

    override suspend fun deleteResidentByUnitNumber(id: Long) {
        return withContext(Dispatchers.Main) {
            queries.deleteResidentByUnitNumber(id)
        }
    }
}