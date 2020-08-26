package com.ajm.employee

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajm.employee.database.*
import com.ajm.testapp.apis.RetrofitApiService
import com.ajm.testapp.apis.RetrofitInstanceClass
import retrofit2.Call
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    var employee_recycler: RecyclerView? = null
    var employeeViewModel: EmployeeViewModel? = null
    private var employeeListDao: EmployeeListDao? = null
    var dataRoombase: EmployeeDatabase? = null
    var retrofitInstanceClass: RetrofitInstanceClass? = null
    var employeeData: List<EmployeeData>? = null

    private var executor: Executor = Executors.newSingleThreadExecutor();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataRoombase = EmployeeDatabase.invoke(this.application!!)
        this.employeeListDao = dataRoombase?.employeeListDao()
        this.retrofitInstanceClass = RetrofitInstanceClass()
        //view ui declaration
        initViewControls()

        //employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

    }

    fun initViewControls() {

        /*employeeViewModel?.getAllCompTripList()?.observe(this,
            Observer<List<EmployeeData>?> {
                if(it != null)
                {

                    prepareRecyclerView(it)
                }
                else
                {
                    Log.d("Name", it)


                }
            })*/

        checkDb()
        printHashKey(this)
    }
    @SuppressLint("PackageManagerGetSignatures")
    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("FragmentActivity.TAG", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("MainActivity", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("MainActivity", "printHashKey()", e)
        }
    }

    fun checkDb()
    {
        val userExists = employeeListDao?.getCount()
        if (userExists?.equals(0)!!) {
           getEmployeeList()
        }
        else
        {
            employeeData = employeeListDao?.loadEmployee()
            prepareRecyclerView(employeeData)
        }
    }



    fun getEmployeeList()
    {
        executor.execute {
            val userExists = employeeListDao?.getCount()
            Log.d("userExistsuserExists", userExists.toString())

            if (userExists?.equals(0)!!) {
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

                        if (response.code() == ConstantClass.API_SUCCESS_RESPONSE_CODE) {
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
                                checkDb()
                                // insertAsyncTask(employeeListDao).execute(responseProduct)

                            }
                        }

                    }

                })
            }
           /* else
            {
               employeeData = employeeListDao?.loadEmployee()
                prepareRecyclerView(employeeData)
            }*/
        }
    }

    private class insertAsyncTask internal constructor(dao: EmployeeListDao?) :
        AsyncTask<EmployeeData?, Void?, Void?>() {
        private val mAsyncTaskDao: EmployeeListDao? = dao
        override fun doInBackground(vararg params: EmployeeData?): Void? {
            Log.d("gvhgvhg", params[0].toString())
            mAsyncTaskDao?.insert(params[0])
            return null
        }

    }

    fun prepareRecyclerView(employeeList: List<EmployeeData>?)
    {
        employee_recycler = findViewById(R.id.employee_recycler)
        employee_recycler?.setLayoutManager(
            LinearLayoutManager(
                this, RecyclerView.VERTICAL,
                false
            )
        )
        val employeeListAdapter = EmployeeListAdapter(this, employeeList)
        employee_recycler?.adapter = employeeListAdapter
        employee_recycler?.scheduleLayoutAnimation()
    }

}
