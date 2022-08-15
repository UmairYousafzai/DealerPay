package com.moveitech.dealerpay.repository

import com.moveitech.dealerpay.dataModel.response.user.Department
import com.moveitech.dealerpay.database.AppDao
import javax.inject.Inject

class DBDataRepository @Inject constructor(private var dao: AppDao) {


//    fun getLocations():LiveData<List<Location>>
//    {
//        return  dao.getLocations().asLiveData()
//    }

    suspend fun saveDepartment(department: ArrayList<Department>)=dao.insertDepartment( department )
    suspend fun deleteDepartment()=dao.deleteDepartment()
     fun getDepartments()=dao.getDepartments()
}