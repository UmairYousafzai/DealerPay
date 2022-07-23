package com.moveitech.dealerpay.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.dealerpay.adapter.TransactionAdapter
import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.repository.ApiDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    BaseViewModel() {

    var adapter:TransactionAdapter = TransactionAdapter()
    val transactionsResponse: MutableLiveData<ArrayList<TransactionResponse>> = MutableLiveData()

    fun onClick(key: Int) {

    }

     fun getTransactions() {
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.getTransactions()
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            adapter.setList(response.value)
                        }
                        else -> {
                            handleErrorType(response)
                        }
                    }
                }
        }
    }
}