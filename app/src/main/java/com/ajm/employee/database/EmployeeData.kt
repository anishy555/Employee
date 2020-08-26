package com.ajm.employee.database

import androidx.room.*

/**
 * Created by Anish on 8/22/2020.
 */
 class employee : ArrayList<employeeItem>()




data class employeeItem(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @Embedded
    val address: Address?,
    @Embedded
    val company: Company?,
    val email: String?,
    val name: String?,
    val phone: String?,
    val profile_image: String?,
    val username: String?,
    val website: String?
)
data class Address(
    val city: String?,
    @Embedded
    val geo: Geo,
    val street: String?,
    val suite: String?,
    val zipcode: String?
)
data class Company(
    val bs: String?,
    val catchPhrase: String?,
    @ColumnInfo(name = "company_name")
    val name: String?
)

data class Geo(
    val lat: String?,
    val lng: String?
)

@Entity(tableName = "employee_list_table")
@TypeConverters(EmployeeTypeConverters::class)
data class EmployeeData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
     val company_name: String?,
    val email: String?,
    val name: String?

)