package com.moveitech.dealerpay.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.repository.ApiDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val dataRepository: ApiDataRepository) :
    BaseViewModel() {


    val email: ObservableField<String> = ObservableField("")
    val passWord: ObservableField<String> = ObservableField("")
    val loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val userNameError: MutableLiveData<Boolean> = MutableLiveData()
    val passwordError: MutableLiveData<Boolean> = MutableLiveData()
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"



    fun onClick(key: Int) {

        if (validateFields()) {
            login(LoginRequest(email.get(), passWord.get()?.trim()))
            userNameError.value = false
            passwordError.value = false
        } else {
            userNameError.value = true
            passwordError.value = true
        }
    }

    private fun validateFields(): Boolean {
        var flag = true
        if (email.get()?.length ?: 0 == 0) {
            flag = false
        }

        if (passWord.get()?.length ?: 0 == 0) {
            flag = false
        }
        if (!email.get()?.trim()?.matches(emailPattern.toRegex())!!)
        {
            flag=false
        }
        return flag
    }

    private fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.login(loginRequest)
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                                loginResponse.value = response.value

                        }
                        is ResultWrapper.GenericError ->
                        {
                            userNameError.value = true
                            userNameError.value = true

                        }
                        else -> {
                            handleErrorType(response)
                        }
                    }
                }
        }
    }
}