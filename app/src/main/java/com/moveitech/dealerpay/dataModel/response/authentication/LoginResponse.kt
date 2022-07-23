package com.moveitech.dealerpay.dataModel.response.authentication

import java.io.Serializable

data class LoginResponse(
    val baseUrl: String,
    val email: String,
    val firstName: String,
    val id: String,
    val jwtToken: String,
    val lastName: String,
    val publicKey: String,
    val refreshToken: String
):Serializable