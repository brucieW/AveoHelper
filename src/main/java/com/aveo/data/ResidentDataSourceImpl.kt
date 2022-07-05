package com.aveo.data

import com.aveo.db.AveoDatabase
import com.aveo.db.Resident
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ResidentDataSourceImpl(
    db: AveoDatabase
) : ResidentDataSource {

    private val queries = db.residentsQueries

    override suspend fun getResidentById(id: Long): Resident? {
        return withContext(Dispatchers.IO) {
            queries.getResidentById(id).executeAsOneOrNull()
        }
    }

    override fun getAllResidents(): Flow<List<Resident>> {
        return queries.getAllResidents().asFlow().mapToList()
    }

    override suspend fun getResidentByName(lastName: String): Resident? {
        return withContext(Dispatchers.IO) {
            queries.getResidentByName(lastName).executeAsOneOrNull()
        }
    }

    override suspend fun getResidentByUnitNumber(unitNumber: Long): Resident? {
        return withContext(Dispatchers.IO) {
            queries.getResidentByUnitNumber(unitNumber).executeAsOneOrNull()
        }
    }

    override suspend fun insertResident(
        unitNumber: Long,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        mobileNumber: String
    ) {
        return withContext(Dispatchers.IO) {
            queries.insertResident(null, unitNumber, firstName, lastName, phoneNumber, mobileNumber)
        }
    }

    override suspend fun deleteResidentById(id: Long) {
        return withContext(Dispatchers.IO) {
            queries.deleteResidentById(id)
        }
    }
}