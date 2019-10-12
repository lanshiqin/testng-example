package com.lanshiqin.testngexample.service.impl

import com.lanshiqin.testngexample.service.IExampleService
import org.springframework.stereotype.Service

@Service
class ExampleServiceImpl : IExampleService {

    override fun addFunction(numX: Int, numY: Int): Int {
        return numX + numY
    }

}