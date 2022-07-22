package com.aveo.data.repository

import com.aveo.db.AveoDatabase
import com.aveo.db.Resident
import com.aveo.di.dbDriver
import com.aveo.domain.repository.ResidentRepository
import com.aveo.domain.repository.UserRepository
import com.aveo.presentation.di
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.instance

class ResidentRepositoryImpl(
    db: AveoDatabase
) : ResidentRepository {

    private val queries = db.residentsQueries

    init {
        createDatabase()
    }

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

private fun createDatabase() {
    CoroutineScope(Dispatchers.Main).launch {
        dbDriver?.execute(
            null, "CREATE TABLE IF NOT EXISTS resident (\n" +
                    "    unitNumber INTEGER NOT NULL PRIMARY KEY,\n" +
                    "    firstName1 TEXT DEFAULT '',\n" +
                    "    firstName2 TEXT  DEFAULT '',\n" +
                    "    lastName TEXT DEFAULT '',\n" +
                    "    phoneNumber TEXT DEFAULT '',\n" +
                    "    mobileNumber TEXT DEFAULT '',\n" +
                    "    phoneNumberId TEXT DEFAULT '',\n" +
                    "    email TEXT DEFAULT '',\n" +
                    "    isIndependentLiving INTEGER DEFAULT 0,\n" +
                    "    onResidentsCommittee INTEGER DEFAULT 0,\n" +
                    "    isCommissionerForDeclarations INTEGER DEFAULT 0\n" +
                    ")", 0, null
        )

        val repository: UserRepository by di.instance()

        val user = repository.getUser("admin")

        if (user == null) {
            val residentRepository: ResidentRepository by di.instance()

            for (i in 1..88) {
                residentRepository.insertResident(i.toString())
            }

            for (i in 89..124) {
                residentRepository.insertResident(i.toString(), isIndependentLiving = false)
            }

//                residentRepository.insertResident("100A", isIndependentLiving = false)

            for (i in 125..138) {
                residentRepository.insertResident(i.toString())
            }
        }
    }
}

