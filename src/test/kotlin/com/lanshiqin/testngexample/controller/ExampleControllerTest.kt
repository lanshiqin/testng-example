package com.lanshiqin.testngexample.controller

import com.lanshiqin.testngexample.service.IExampleService
import com.lanshiqin.testngexample.util.ExcelUtil
import com.lanshiqin.testngexample.vm.AddModel
import org.mockito.Mockito
import org.springframework.core.io.ClassPathResource
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import java.util.*
import kotlin.reflect.jvm.internal.impl.resolve.constants.DoubleValue

class ExampleControllerTest {

    private lateinit var exampleController: ExampleController
    private lateinit var exampleService: IExampleService

    @BeforeMethod
    fun setUp() {
        exampleController = ExampleController()
        exampleService = Mockito.mock(IExampleService::class.java)
        exampleController.exampleService = exampleService
    }

    @AfterMethod
    fun tearDown() {
    }

    @DataProvider(name = "addFunctionData")
    fun addFunctionData(): Array<Array<HashMap<Any, Any>?>>? {
        return ExcelUtil().loadData(ClassPathResource("controller/addFunctionData.xlsx").file.path)
    }

    @Test(dataProvider = "addFunctionData")
    fun testAddFunction(data: HashMap<String, Any>) {

        val addModel = AddModel()
        val numX = data["numX"]
        val numY = data["numY"]
        if (numX!=null){
            addModel.numX = numX.toString().toDouble().toInt()
        }
        if (numY!=null){
            addModel.numY = numY.toString().toDouble().toInt()
        }
        val mockReturn = data["mockReturn"]!!.toString().toDouble().toInt()
        Mockito.`when`(exampleService.addFunction(addModel.numX!!,addModel.numY!!)).thenReturn(mockReturn)
        val result = exampleController.addFunction(addModel)
        Assert.assertEquals(result.data!!.toInt(),data["expectedResult"]!!.toString().toDouble().toInt())

    }
}