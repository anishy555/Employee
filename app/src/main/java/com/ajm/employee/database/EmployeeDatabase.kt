package com.ajm.employee.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


/**
 * Created by Anish on 8/22/2020.
 */
@Database(entities = [EmployeeData::class], version = 1, exportSchema = false)
@TypeConverters(EmployeeTypeConverters::class)
abstract class EmployeeDatabase: RoomDatabase()
{
abstract fun employeeListDao(): EmployeeListDao

    companion object {
        @Volatile private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }



        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            EmployeeDatabase::class.java, EmployeeDatabase::class.java.name
        )
            .allowMainThreadQueries()
            .build()
    }



    open fun destroyInstance() {
        instance = null
    }
}