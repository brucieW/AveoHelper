package com.aveo.domain.excel

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream

object ExcelHelper {
    fun readExcelFile(fileName: String): Workbook? {
        val file = File(fileName)

        file.let {
            return WorkbookFactory.create(FileInputStream(it))
        }
    }

    fun getFirstSheet(workbook: Workbook) : Sheet? {
        if (workbook.numberOfSheets > 0) {
            return workbook.getSheetAt(0)
        }

        return null
    }
}

