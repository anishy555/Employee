package com.ajm.testapp.apis


import com.ajm.employee.database.ConstantClass
import com.ajm.employee.database.ConstantClass.Companion.BaseUrl
import com.ajm.employee.database.ConstantClass.Companion.TIMEOUT_VALUE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Anish on 6/10/2020.
 */
class RetrofitInstanceClass: ConstantClass {

    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit? {
        val b = OkHttpClient.Builder()
        b.readTimeout(TIMEOUT_VALUE, TimeUnit.MILLISECONDS)
        b.writeTimeout(TIMEOUT_VALUE, TimeUnit.MILLISECONDS)
        if (retrofit == null) {
            val client = b.build()
            retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit
    }
}