package com.ajm.employee

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ajm.employee.database.EmployeeData
import com.ajm.employee.database.employeeItem

/**
 * Created by Anish on 8/22/2020.
 */
class EmployeeViewModel(application: Application): AndroidViewModel(application) {

    var employeeRepository: EmployeeRepository? = null
   // var allemployeeList: LiveData<List<EmployeeData>?>? = null
    var allemployeeList: List<EmployeeData>? = null

    init {
        employeeRepository = EmployeeRepository(application)
        allemployeeList = employeeRepository?.getList()
        Log.d("mAllList",allemployeeList?.get(1).toString())

    }

    fun getAllCompTripList(): List<EmployeeData>? {
        return employeeRepository?.getList()
        //return mAllList
    }
}