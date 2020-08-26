package com.ajm.employee

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
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
class EmployeeRepo(application: Application)  {

    private var executor: Executor = Executors.newSingleThreadExecutor();

    var application: Application? = null
    var retrofitInstanceClass: RetrofitInstanceClass? = null
    private var employeeListDao: EmployeeListDao
    var dataRoombase: EmployeeDatabase
   /* private val mutableLiveData: MutableLiveData<List<employeeItem>> =
        MutableLiveData<List<employeeItem>>()*/
    private val mutableLiveData: MutableLiveData<String> =
        MutableLiveData<String>()
    var shiftIems = ArrayList<String>()
    var shift: List<EmployeeData> = ArrayList()
    private var item: EmployeeData? = null
    private val itemList: MutableList<String> = ArrayList<String>()
    init {

        this.application = application
        this.retrofitInstanceClass = RetrofitInstanceClass()
        dataRoombase = EmployeeDatabase.invoke(this.application!!)
        this.employeeListDao = dataRoombase.employeeListDao()
    }



   /* fun getList(): MutableLiveData<String> {


                *//** Create handle for the RetrofitInstance interface *//*
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



                                for (i in 0 until responseProduct?.size!!) {
                                    shiftIems.add(responseProduct.get(i).id.toString());
                                    shiftIems.add(responseProduct.get(i).company.name.toString());
                                    shiftIems.add(responseProduct.get(i).email.toString());
                                    shiftIems.add(responseProduct.get(i).name.toString());
                                    shiftIems.addAll(shiftIems)

                                    val employeeData = EmployeeData(responseProduct.get(i).id,
                                        responseProduct.get(i).company.name,
                                        responseProduct.get(i).email,
                                        responseProduct.get(i).name
                                    )
                                    println("executorHello = ${shiftIems}")
                                    //mutableLiveData.postValue(employeeData)
                                    insertAsyncTask(employeeListDao).execute(employeeData)
                                }
                                for (i in 0 until responseProduct.size) {
                                    itemList.add(responseProduct.get(i).id.toString())
                                    itemList.add(responseProduct.get(i).company.name.toString());
                                    itemList.add(responseProduct.get(i).email.toString());
                                    itemList.add(responseProduct.get(i).name.toString());
                                    itemList.addAll(itemList)
                                }
                                println("executorHello = ${shiftIems}")
                                //mutableLiveData.postValue(employeeData)

                                println("executorHello = ${response.body()}")
                                //mutableLiveData.postValue(responseProduct)

                                //insertAsyncTask(employeeListDao).execute(responseProduct)

                            }
                        }

                    }

                })
        return itemList
    }
*/
    private class insertAsyncTask internal constructor(dao: EmployeeListDao?) :
        AsyncTask<EmployeeData, Void?, Void?>() {
        private val mAsyncTaskDao: EmployeeListDao? = dao
        override fun doInBackground(vararg params: EmployeeData): Void? {
            Log.d("gvhgvhg", params[0].toString())
            mAsyncTaskDao?.insert(params[0])
            return null
        }

    }

}