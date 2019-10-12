package com.lanshiqin.testngexample.service

interface IExampleService {

    /**
     * 计算两个数相加的结果
     * @param numX 整数X
     * @param numY 整数Y
     * @return numX + numY
     */
    fun addFunction(numX: Int, numY: Int): Int
}