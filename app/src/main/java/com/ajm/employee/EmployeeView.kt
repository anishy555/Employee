package com.ajm.employee

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ajm.employee.database.EmployeeData
import com.ajm.employee.database.employee
import com.ajm.employee.database.employeeItem

/**
 * Created by Anish on 8/22/2020.
 */
class EmployeeView(application: Application): AndroidViewModel(application) {

   /* var employeeRepository: EmployeeRepo? = null
   // var allemployeeList: LiveData<List<employeeItem>>? = null
    var allemployeeList: LiveData<String>? = null

    init {
        employeeRepository = EmployeeRepo(application)
        allemployeeList = employeeRepository?.getList()
        Log.d("mAllList",allemployeeList?.value?.get(1).toString())

    }

    fun getAllCompTripList(): LiveData<String>? {
        return employeeRepository?.getList()
        //return mAllList
    }*/
}