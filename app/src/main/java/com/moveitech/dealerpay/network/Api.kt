package com.moveitech.dealerpay.network

import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.util.Constants.Companion.LOGIN
import com.moveitech.dealerpay.util.Constants.Companion.REFRESH_TOKEN
import com.moveitech.dealerpay.util.Constants.Companion.TRANSACTIONS
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Api {

    @POST(REFRESH_TOKEN)
    fun refreshToken(token:String):Call<LoginResponse>


    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest):LoginResponse

    @GET(TRANSACTIONS)
    suspend fun getTransactions():ArrayList<TransactionResponse>


}