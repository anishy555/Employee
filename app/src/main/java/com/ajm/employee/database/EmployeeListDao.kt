package com.ajm.employee.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

/**
 * Created by Anish on 5/23/2020.
 */
@Dao
public interface EmployeeListDao {
   /* @Insert(onConflict = REPLACE)
    fun insert(item: List<employeeItem>?)
*/
    @Insert(onConflict = REPLACE)
    fun insert(employeeData: EmployeeData?)



   /* @Query("SELECT * FROM employee_list_table")
    fun loadAllUsers(): LiveData<List<employeeItem>?>

    @Query("SELECT city, street, suite, zipcode, lat, lng, bs, catchPhrase, company_name, id, email, " +
            "name, phone, profile_image, username, website FROM employee_list_table")
    fun loadAll(): LiveData<List<employeeItem>?>*/

    /*@Query("SELECT * FROM employee_list_table")
    fun loadEmployee(): LiveData<List<EmployeeData>?>*/
    @Query("SELECT * FROM employee_list_table")
    fun loadEmployee(): List<EmployeeData>?

    /*@Query("SELECT * FROM product_list_table")
     fun loadData(): DataSource.Factory<Int, Product>*/

    @Query("SELECT COUNT(*) from employee_list_table")
     fun getCount(): Int
}