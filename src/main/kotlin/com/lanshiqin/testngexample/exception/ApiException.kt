package com.lanshiqin.testngexample.exception

class ApiException(code: String, message: String) : RuntimeException(message) {
    var code = code
}