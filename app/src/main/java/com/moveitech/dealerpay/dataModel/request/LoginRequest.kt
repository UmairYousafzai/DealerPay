package com.moveitech.dealerpay.dataModel.request

import java.io.Serializable

data class LoginRequest(
    val email: String?="",
    val password: String?=""
):Serializable