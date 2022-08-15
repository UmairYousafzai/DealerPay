package com.moveitech.dealerpay.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moveitech.dealerpay.dataModel.response.user.Department
import com.moveitech.dealerpay.dataModel.response.user.Enterprise
import com.moveitech.dealerpay.dataModel.response.user.UserResponse
import com.moveitech.dealerpay.database.AppDao

@Database(entities = [Department::class], version = 1)
abstract class DealerPayDB : RoomDatabase() {
    abstract fun dbDao(): AppDao

}