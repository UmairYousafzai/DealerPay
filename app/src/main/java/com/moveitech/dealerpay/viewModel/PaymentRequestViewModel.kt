package com.moveitech.dealerpay.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.dealerpay.adapter.TransactionAdapter
import com.moveitech.dealerpay.dataModel.general.Customer
import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.request.payment.PaymentRequest
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.repository.ApiDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentRequestViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    BaseViewModel() {

    val paymentRequestResponse: MutableLiveData<String> = MutableLiveData()
    val customerLiveData: MutableLiveData<Customer> = MutableLiveData()
    val firstName:ObservableField<String> = ObservableField()
    var customer=Customer()

    fun onClick() {
        customerLiveData.value=customer
    }

     fun paymentRequest(paymentRequest: PaymentRequest) {
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.paymentRequest(paymentRequest)
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            paymentRequestResponse.value=response.value
                        }
                        else -> {
                            handleErrorType(response)
                        }
                    }
                }
        }
    }
}