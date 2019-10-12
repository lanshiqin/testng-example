package com.lanshiqin.testngexample.util

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

/**
 * Excel工具类
 * @author 蓝士钦
 */
class ExcelUtil {

    companion object{
        val logger: Logger = LoggerFactory.getLogger(ExcelUtil::class.java)
        const val EXCEL_OLD_SUFFIX = ".xls"
        const val EXCEL_NEW_SUFFIX = ".xlsx"
    }

    /**
     * 打开工作簿
     * @param filePath 工作簿文件路径
     * @return Workbook 工作簿对象
     */
    private fun openWorkbook(filePath: String): Workbook? {
        var workbook: Workbook? = null
        try {
            val file = File(filePath)
            val `is` = FileInputStream(file)
            when {
                filePath.endsWith(EXCEL_OLD_SUFFIX) -> workbook = HSSFWorkbook(`is`)
                filePath.endsWith(EXCEL_NEW_SUFFIX) -> workbook = XSSFWorkbook(`is`)
                else -> logger.error("不支持的文件格式")
            }
        } catch (e: IOException) {
            logger.error("加载工作簿异常：{}",e.printStackTrace())
            e.printStackTrace()
        }
        return workbook
    }

    /**
     * 获取指定工作表的单元格的值
     * @param sheet 工作表
     * @param rowNum 行号
     * @param cellNum 列号
     * @return 单元格值
     */
    private fun getCellValue(sheet: Sheet, rowNum: Int, cellNum: Int): Any? {
        val cell = sheet.getRow(rowNum).getCell(cellNum)
        var value: Any ?= null
        if (cell == null) {
            return null
        }
        when (cell.cellType) {
            CellType.STRING -> {
                value = cell.richStringCellValue.toString()
                return value
            }
            CellType.NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.dateCellValue
                    return value
                }
                value = cell.numericCellValue
                return value
            }
            CellType.BOOLEAN -> {
                value = cell.booleanCellValue
                return value
            }
            CellType.FORMULA -> {
                value = cell.cellFormula
                return value
            }
            CellType.ERROR -> {
                value = cell.errorCellValue
                return value
            }
            else -> return value
        }
    }

    /**
     * 加载数据
     * @param file Excel文件路径
     * @return 文件数据
     */
    fun loadData(file: String): Array<Array<HashMap<Any, Any>?>>? {
        val header = ArrayList<Any>()
        val workbook = openWorkbook(file)
        val sheet = workbook!!.getSheetAt(workbook.activeSheetIndex)
        val totalRowNum = sheet.lastRowNum + 1
        val totalColumnNum = sheet.getRow(0).physicalNumberOfCells
        val dataMap = Array<Array<HashMap<Any, Any>?>>(totalRowNum - 1) { arrayOfNulls(1) }
        if (totalRowNum > 1) {
            for (i in 0 until totalRowNum - 1) {
                dataMap[i][0] = HashMap()
            }
        }
        for (c in 0 until totalColumnNum) {
            val cellValue = getCellValue(sheet, 0, c)
            if (cellValue == null){
                logger.error("表头数据作为key，不允许null值")
                return null
            }
            header.add(cellValue)
        }
        for (row in 1 until totalRowNum) {
            for (column in 0 until totalColumnNum) {
                val cellValue = getCellValue(sheet, row, column)
                dataMap[row-1][0]?.set(header[column],cellValue!!)
            }
        }
        return dataMap
    }
}