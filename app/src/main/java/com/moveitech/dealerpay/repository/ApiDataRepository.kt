package com.moveitech.dealerpay.repository


import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.request.cardPayment.CardPayment
import com.moveitech.dealerpay.dataModel.request.payment.PaymentRequest
import com.moveitech.dealerpay.dataModel.response.BaseResponse
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.dataModel.response.user.UserResponse
import com.moveitech.dealerpay.network.Api
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.network.RetrofitClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class ApiDataRepository @Inject constructor(val api:Api){

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun login(loginRequest: LoginRequest): ResultWrapper<LoginResponse> {
        return safeApiCall(dispatcher) { api.login(loginRequest) }
    }
   suspend fun getTransactions(): ResultWrapper<List<TransactionResponse>> {
        return safeApiCall(dispatcher) {api.getTransactions() }
    }

   suspend fun getUser(): ResultWrapper<UserResponse> {
        return safeApiCall(dispatcher) {api.getUser() }
    }

  suspend fun logout(): ResultWrapper<Unit> {
        return safeApiCall(dispatcher) {api.logout() }
    }

  suspend fun paymentRequest(paymentRequest: PaymentRequest): ResultWrapper<Unit> {
        return safeApiCall(dispatcher) {api.paymentRequest(paymentRequest) }
    }
  suspend fun cardPayment(cardPayment: CardPayment): ResultWrapper<Unit> {
        return safeApiCall(dispatcher) {api.cardPayment(cardPayment) }
    }



}