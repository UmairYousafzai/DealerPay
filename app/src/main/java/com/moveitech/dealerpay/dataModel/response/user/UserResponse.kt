package com.moveitech.dealerpay.dataModel.response.user

import java.io.Serializable

data class UserResponse(
    val id_local:Int=0,
    val email: String="",
    val enterprises: ArrayList<Enterprise> = ArrayList(),
    val firstName: String="",
    val groupId: String="",
    val groupName: String="",
    val lastName: String="",
    val userId: String=""
):Serializable