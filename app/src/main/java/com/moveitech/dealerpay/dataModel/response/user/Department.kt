package com.moveitech.dealerpay.dataModel.response.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "department")
data class Department(
    @PrimaryKey(autoGenerate = true)
    val id_local:Int,
    val defaultTransactionTypeId: String,
    val defaultTransactionTypeLabel: String,
    val departmentId: String,
    val departmentTypeId: String,
    val hasPayShare: Boolean,
    val name: String,
    val paySharePercentage: Double,
): Serializable