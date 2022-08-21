package com.moveitech.dealerpay.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.user.Department
import com.moveitech.dealerpay.dataModel.response.user.UserResponse
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.repository.ApiDataRepository
import com.moveitech.dealerpay.repository.DBDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val apiDataRepository: ApiDataRepository,private val dbDataRepository: DBDataRepository) :
    BaseViewModel() {

    private  val TAG = AuthenticationViewModel::class.java.simpleName

    val email: ObservableField<String> = ObservableField("")
    val passWord: ObservableField<String> = ObservableField("")
    val loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    val userNameError: MutableLiveData<Boolean> = MutableLiveData()
    val passwordError: MutableLiveData<Boolean> = MutableLiveData()
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val userResponse: MutableLiveData<UserResponse> = MutableLiveData()



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
            apiDataRepository.login(loginRequest)
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

    fun getUser() {
        viewModelScope.launch {
            showProgressBar(true)
            apiDataRepository.getUser()
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            userResponse.value=response.value
                        }
                        else -> {
                            handleErrorType(response)
                        }
                    }
                }
        }
    }

    fun saveDepartments(department: ArrayList<Department>)
    {
        viewModelScope.launch{
            Log.e(TAG,"save Department function")
            dbDataRepository.deleteDepartment()
            dbDataRepository.saveDepartment(department)
        }
    }

}