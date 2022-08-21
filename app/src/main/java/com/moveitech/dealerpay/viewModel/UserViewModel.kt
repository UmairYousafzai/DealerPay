package com.moveitech.dealerpay.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moveitech.dealerpay.adapter.TransactionAdapter
import com.moveitech.dealerpay.dataModel.request.LoginRequest
import com.moveitech.dealerpay.dataModel.response.BaseResponse
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.dataModel.response.user.Department
import com.moveitech.dealerpay.dataModel.response.user.UserResponse
import com.moveitech.dealerpay.network.ResultWrapper
import com.moveitech.dealerpay.repository.ApiDataRepository
import com.moveitech.dealerpay.repository.DBDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val dataRepository: ApiDataRepository,private val dbDataRepository: DBDataRepository) :
    BaseViewModel() {

    val userResponse: MutableLiveData<UserResponse> = MutableLiveData()
    val departments: MutableLiveData<List<Department>> = MutableLiveData()
     val logoutResponse: MutableLiveData<Boolean> = MutableLiveData()


    fun onClick(key: Int) {

        logout()
    }

     fun getUser() {
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.getUser()
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

     private fun logout() {
        viewModelScope.launch {
            showProgressBar(true)
            dataRepository.logout()
                .let { response ->
                    showProgressBar(false)

                    when (response) {
                        is ResultWrapper.Success -> {
                            logoutResponse.value=true
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
            dbDataRepository.deleteDepartment()
            Log.e("UserViewMOdel","department===> save department")
            dbDataRepository.saveDepartment(department)
        }
    }

    fun getDepartmentList()=dbDataRepository.getDepartments()
}