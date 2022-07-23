package com.moveitech.dealerpay.repository


import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.network.Api
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.network.RetrofitClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ApiDataRepository @Inject constructor(val api:Api){

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun login(loginRequest: LoginRequest): ResultWrapper<LoginResponse> {
        return safeApiCall(dispatcher) { api.login(loginRequest) }
    }
   suspend fun getTransactions(): ResultWrapper<List<TransactionResponse>> {
        return safeApiCall(dispatcher) {api.getTransactions() }
    }



}