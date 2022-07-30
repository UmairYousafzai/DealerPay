package com.moveitech.dealerpay.dataModel.response.user

import java.io.Serializable

data class UserResponse(
    val email: String,
    val enterprises: List<Enterprise>,
    val firstName: String,
    val groupId: String,
    val groupName: String,
    val lastName: String,
    val transactionLimit: Any,
    val userId: String
):Serializable