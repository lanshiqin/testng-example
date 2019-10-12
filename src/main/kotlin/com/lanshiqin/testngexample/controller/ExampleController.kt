package com.lanshiqin.testngexample.controller

import com.alibaba.fastjson.JSON
import com.lanshiqin.testngexample.base.ApiResponse
import com.lanshiqin.testngexample.service.IExampleService
import com.lanshiqin.testngexample.vm.AddModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
class ExampleController {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ExampleController::class.java)
    }

    @Autowired
    lateinit var exampleService: IExampleService

    @PostMapping("/addFunction")
    fun addFunction(@RequestBody addModel: AddModel) :ApiResponse<Int>{
        logger.info("addFunction 入参:{}",JSON.toJSONString(addModel))
        if (ObjectUtils.isEmpty(addModel.numX)){
            return ApiResponse.fail(ApiResponse.BUSINESS_EXCEPTION,"参数numX不能为空")
        }
        if (ObjectUtils.isEmpty(addModel.numY)){
            return ApiResponse.fail(ApiResponse.BUSINESS_EXCEPTION,"参数numY不能为空")
        }
        return try {
            val result = exampleService.addFunction(addModel.numX!!,addModel.numY!!)
            ApiResponse.success("计算结果",result)
        }catch (e: Exception){
            e.printStackTrace()
            ApiResponse.fail(ApiResponse.SYSTEM_EXCEPTION,"系统异常")
        }
    }

}
