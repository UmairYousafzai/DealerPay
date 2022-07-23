package com.moveitech.dealerpay

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.moveitech.dealerpay.application.DealerPayApplication
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.databinding.ActivityLoginBinding
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.util.DialogUtils
import com.moveitech.dealerpay.util.showSnackBar
import com.moveitech.dealerpay.viewModel.AuthenticationViewModel
import com.moveitech.dealerpay.viewModel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
open class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var dialog: AlertDialog

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    private val viewModel: AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            dataStoreHelper.isLogin.collect {
                if (it) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        dialog = DialogUtils.getProgressDialog(this)

        liveDataObserver()
    }

    fun liveDataObserver() {
        with(viewModel)
        {
            setupGeneralViewModel(this)

            userNameError.observe(this@LoginActivity)
            {
                setErrorOnFields(it)
            }

            loginResponse.observe(this@LoginActivity) {
                saveLoginResponseData(it)
            }

        }

    }

    private fun saveLoginResponseData(it: LoginResponse?) {

        lifecycleScope.launch {
            dataStoreHelper.apply {
                saveIsLogin(true)
                it?.let { loginResponse ->
                    saveToken(loginResponse.jwtToken)
                    DealerPayApplication.Token = loginResponse.jwtToken
                    saveRefreshToken(loginResponse.refreshToken)
                    DealerPayApplication.refreshToken = loginResponse.refreshToken
                }
                runOnUiThread {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setErrorOnFields(flag: Boolean) {

        if (flag) {
            binding.emailTextLayout.error = "Enter Valid Username"
            binding.passwordTextLayout.error = "Enter Valid Password"
        } else {
            binding.emailTextLayout.error = null
            binding.passwordTextLayout.error = null
        }
    }

    private fun setupGeneralViewModel(generalViewModel: BaseViewModel) {
        with(generalViewModel) {
            dialogMessage.observe(this@LoginActivity) {
//               showAlertDialog(it)
                showSnackBar(it)
            }

            progressBar.observe(this@LoginActivity) {
                showProgressDialog(it)

            }
        }
    }

    private fun showProgressDialog(show: Boolean) {

        if (show) {
            if (!dialog.isShowing)
                dialog.apply { show() }
        } else if (!show) {
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }

}