package com.ajm.employee.database

/**
 * Created by Anish on 6/10/2020.
 */
interface ConstantClass {
    companion object {
        //url
        var BaseUrl: String = "https://www.mocky.io/"

        //other constant values
        var TIMEOUT_VALUE: Long = 120000
        var API_SUCCESS_RESPONSE_CODE = 200


    }
}