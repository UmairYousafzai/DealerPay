package com.moveitech.dealerpay.network

import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.request.payment.PaymentRequest
import com.moveitech.dealerpay.dataModel.response.BaseResponse
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.dataModel.response.user.UserResponse
import com.moveitech.dealerpay.util.Constants.Companion.LOGIN
import com.moveitech.dealerpay.util.Constants.Companion.LOGOUT
import com.moveitech.dealerpay.util.Constants.Companion.PAYMENT_REQUEST
import com.moveitech.dealerpay.util.Constants.Companion.REFRESH_TOKEN
import com.moveitech.dealerpay.util.Constants.Companion.TRANSACTIONS
import com.moveitech.dealerpay.util.Constants.Companion.USER
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Api {

    @POST(REFRESH_TOKEN)
    fun refreshToken(@Body token:String):Call<LoginResponse>


    @POST(LOGIN)
    suspend fun login(@Body loginRequest: LoginRequest):LoginResponse

    @GET(TRANSACTIONS)
    suspend fun getTransactions():ArrayList<TransactionResponse>



    @GET(USER)
    suspend fun getUser():UserResponse

    @POST(LOGOUT)
    suspend fun logout():Unit


    @POST(PAYMENT_REQUEST)
    suspend fun paymentRequest(@Body paymentRequest: PaymentRequest):String


}