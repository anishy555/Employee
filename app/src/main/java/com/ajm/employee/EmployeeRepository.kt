package com.ajm.employee

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.ajm.employee.database.*
import com.ajm.employee.database.ConstantClass.Companion.API_SUCCESS_RESPONSE_CODE
import com.ajm.testapp.apis.RetrofitApiService
import com.ajm.testapp.apis.RetrofitInstanceClass
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Anish on 8/22/2020.
 */
class EmployeeRepository(application: Application)  {

    private var executor: Executor = Executors.newSingleThreadExecutor();

    var application: Application? = null
    var retrofitInstanceClass: RetrofitInstanceClass? = null
    private var employeeListDao: EmployeeListDao
    var dataRoombase: EmployeeDatabase

    init {

        this.application = application
        this.retrofitInstanceClass = RetrofitInstanceClass()
        dataRoombase = EmployeeDatabase.invoke(this.application!!)
        this.employeeListDao = dataRoombase.employeeListDao()
    }

    /*fun getList() : LiveData<List<EmployeeData>?> {
        refreshUser()
        Log.d("employeeListDao", employeeListDao.loadEmployee()?.get(1)?.name)
        //return employeeListDao.loadAll()
        return employeeListDao.loadEmployee()
        // return mutableLiveData

    }*/

    fun getList() :List<EmployeeData>? {
        refreshUser()
        //Log.d("employeeListDao", employeeListDao.loadEmployee()?.get(1)?.name.toString())
        //return employeeListDao.loadAll()

        return employeeListDao.loadEmployee()
        // return mutableLiveData

    }

fun refreshUser() {
        executor.execute {
            val userExists = employeeListDao.getCount()
            Log.d("userExistsuserExists", userExists.toString())

            if (userExists.equals(0)) {
                Log.d("dfgdfg", "helloooo")
                Log.d("userExists", userExists.toString())

                //** Create handle for the RetrofitInstance interface *//**//**//**//**//**//**//**//*
                val service: RetrofitApiService? =
                    retrofitInstanceClass?.getRetrofitInstance()
                        ?.create(RetrofitApiService::class.java)

                service?.employeeList()?.enqueue(object : retrofit2.Callback<employee> {
                    override fun onFailure(call: Call<employee>, t: Throwable) {
                        Log.d("Error", t.message.toString())
                    }

                    override fun onResponse(call: Call<employee>, response: Response<employee>) {

                        if (response.code() == API_SUCCESS_RESPONSE_CODE) {
                            if (response.isSuccessful) {
                                val responseProduct = response.body()
                                println("executor = ${response.body()}")
                                //mutableLiveData.postValue(responseProduct)
                                //insertAsyncTask(employeeListDao).execute(responseProduct)
                                for (i in 0 until responseProduct?.size!!) {
                                    val employeeData = EmployeeData(responseProduct.get(i).id,
                                        responseProduct.get(i).company?.name,
                                        responseProduct.get(i).email,
                                        responseProduct.get(i).name
                                    )
                                   // println("executorHello = ${shiftIems}")
                                    //mutableLiveData.postValue(employeeData)
                                    insertAsyncTask(employeeListDao).execute(employeeData)
                                }
                               // insertAsyncTask(employeeListDao).execute(responseProduct)

                            }
                        }

                    }

                })
            }
        }
    }

    private class insertAsyncTask internal constructor(dao: EmployeeListDao?) :
        AsyncTask<EmployeeData, Void?, Void?>() {
        private val mAsyncTaskDao: EmployeeListDao? = dao
        override fun doInBackground(vararg params: EmployeeData): Void? {
            Log.d("gvhgvhg", params[0].toString())
            mAsyncTaskDao?.insert(params[0])
            return null
        }

    }
    /*private class insertAsyncTask internal constructor(dao: EmployeeListDao?) :
        AsyncTask<List<employeeItem>?, Void?, Void?>() {
        private val mAsyncTaskDao: EmployeeListDao? = dao
        override fun doInBackground(vararg params: List<employeeItem>?): Void? {
            Log.d("gvhgvhg", params[0].toString())
           // mAsyncTaskDao?.insert(params[0])
            return null
        }

    }*/

}