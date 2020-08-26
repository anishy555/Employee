package com.ajm.employee.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by Anish on 6/12/2020.
 */
class AddressTypeConverters {
    @TypeConverter
    fun stringToAddress(json: String?): List<Address>? {
        if (json == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Address?>?>() {}.type
        return gson.fromJson<List<Address>>(json, type)
    }

    @TypeConverter
    fun AddressToString(list: List<Address?>?): String? {
        if (list == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Address?>?>() {}.type
        return gson.toJson(list, type)
    }
}