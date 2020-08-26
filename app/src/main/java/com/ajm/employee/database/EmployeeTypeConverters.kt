package com.ajm.employee.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by Anish on 6/12/2020.
 */
class EmployeeTypeConverters {

    @TypeConverter
    fun stringToMeasurements(json: String?): List<employeeItem>? {
        if (json == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<employeeItem?>?>() {}.type
        return gson.fromJson<List<employeeItem>>(json, type)
    }

    @TypeConverter
    fun measurementsToString(list: List<employeeItem?>?): String? {
        if (list == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<employeeItem?>?>() {}.type
        return gson.toJson(list, type)
    }


}