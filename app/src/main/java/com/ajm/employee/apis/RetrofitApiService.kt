package com.ajm.testapp.apis

import com.ajm.employee.database.employee
import com.ajm.employee.database.employeeItem
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Anish on 6/10/2020.
 */
interface RetrofitApiService {

    @GET("v2/5d565297300000680030a986")
     fun employeeList(
    ):  Call<employee>
}