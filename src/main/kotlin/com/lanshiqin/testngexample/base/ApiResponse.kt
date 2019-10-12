package com.lanshiqin.testngexample.base

/**
 * @author 蓝士钦
 */
class ApiResponse<T> {

    var code: String ?= null
    var message: String ?= null
    var data: T ?= null

    companion object {
        private const val SUCCESS: String = "2000"
        const val SYSTEM_EXCEPTION: String = "5000"
        const val BUSINESS_EXCEPTION: String = "4000"

        fun <T>success(message: String, data: T): ApiResponse<T>{
            val response = ApiResponse<T>()
            response.code = SUCCESS
            response.message = message
            response.data = data
            return response
        }

        fun <T>fail(code: String, message: String): ApiResponse<T>{
            val response = ApiResponse<T>()
            response.code = code
            response.message = message
            return response
        }
    }

}