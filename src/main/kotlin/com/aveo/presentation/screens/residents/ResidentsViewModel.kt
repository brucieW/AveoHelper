package com.aveo.presentation.screens.residents

import androidx.compose.runtime.*
import com.aveo.domain.excel.ExcelHelper
import com.aveo.domain.repository.ResidentRepository
import com.aveo.presentation.common.RESIDENTS_LISTING_FILE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Row

class ResidentsViewModel(
    private val residentRepository: ResidentRepository
) {
    val radioOptions = listOf("Unit Order", "Name Order")

    var selectedOrderValue = mutableStateOf(radioOptions[0])

    var residents1 = residentRepository.getResidentsBelowUnit("70")
    var residents2 = residentRepository.getResidentsAboveUnit("69")

    var residents3 = residentRepository.getResidentsByLastName()

    var loadDataEnabled = mutableStateOf(true)

    fun onOrderChange(order: String) {
        selectedOrderValue.value = order
    }

    fun setLoadDataEnabled(show: Boolean) {
        loadDataEnabled.value = show
    }

    fun loadResidentsFile() {
        setLoadDataEnabled(false)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val workbook = ExcelHelper.readExcelFile(RESIDENTS_LISTING_FILE)
                val sheet = workbook?.let { ExcelHelper.getFirstSheet(it) }
                val totalRows = sheet?.physicalNumberOfRows

                if (totalRows != null) {
                    for (i in 0..totalRows - 1) {
                        val row = sheet.getRow(i)

                        fillData(row, 0)
                        fillData(row, 4)
                    }
                }

                setLoadDataEnabled(true)
            }
        }

    }

    private suspend fun fillData(row: Row, offset: Int) {
        val unitNumber: String

        try {
            val item = row.getCell(offset).numericCellValue
            unitNumber = item.toString()
        } catch (e: IllegalStateException) {
            return
        }

        if (unitNumber.isEmpty()) {
            return
        }

        val name = row.getCell(offset + 1).stringCellValue

        if (name.isBlank() || name == "RECEPTION" || name == "KITCHEN" || name == "HAIRDRESSER") {
            return
        }

        val onResidentsCommittee = name.contains(" (RC)")

        if (onResidentsCommittee) {
            name.replace(" (RC)", "")
        }

        val cfd = name.contains(" **")

        if (cfd) {
            name.replace(" **", "")
        }

        val names = name.split(",")
        val lastName = names[0]
        val firstNames = if (names.size == 1) listOf("") else names[1].split("&")
        val phoneNumber = row.getCell(2).stringCellValue

        var phoneId: String

        try {
            val item = row.getCell(3).numericCellValue
            phoneId = item.toString()
        } catch (e: IllegalStateException) {
            phoneId = row.getCell(3).stringCellValue
        }

        val landLine: String
        val mobile: String

        if (phoneNumber.length == 9) {
            landLine = phoneNumber
            mobile = ""
        } else {
            landLine = ""
            mobile = phoneNumber
        }

        residentRepository.insertResident(
            unitNumber,
            firstName1 = firstNames[0],
            firstName2 = if (firstNames.size == 2) firstNames[1] else "",
            lastName = lastName,
            phoneNumber = landLine,
            mobileNumber = mobile,
            phoneNumberId = phoneId,
            onResidentsCommittee = onResidentsCommittee,
            isCommissionerForDeclarations = cfd
        )
    }
}