package com.moveitech.dealerpay.dataModel.response.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class UserResponse(
    val id_local:Int,
    val email: String,
    val enterprises: List<Enterprise>,
    val firstName: String,
    val groupId: String,
    val groupName: String,
    val lastName: String,
    val transactionLimit: Any,
    val userId: String
):Serializable