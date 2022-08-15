package com.moveitech.dealerpay.database

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.moveitech.dealerpay.dataModel.response.user.Department
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

     //====================================== Location =================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDepartment(department:ArrayList<Department>)

    @Delete
    suspend fun deleteDepartment(department: Department)

    @Query("Delete from department")
    suspend fun deleteDepartment()

    @Query("select *from department")
    fun getDepartments(): LiveData<List<Department>>




}