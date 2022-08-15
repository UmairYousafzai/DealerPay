package com.moveitech.dealerpay.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.dealerpay.adapter.TransactionAdapter
import com.moveitech.dealerpay.dataModel.general.Customer
import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.request.cardPayment.CardPayment
import com.moveitech.dealerpay.dataModel.request.payment.PaymentRequest
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.repository.ApiDataRepository
import com.moveitech.dealerpay.repository.DBDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardPaymentViewModel @Inject constructor(private val dataRepository: ApiDataRepository, private  val dbDataRepository: DBDataRepository) :
    BaseViewModel() {

    val paymentRequestResponse: MutableLiveData<String?> = MutableLiveData()


     fun paymentRequest(cardPayment: CardPayment) {
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.cardPayment(cardPayment)
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