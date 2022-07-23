package com.moveitech.dealerpay.ui.authentication

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moveitech.dealerpay.dataModel.response.authentication.LoginResponse
import com.moveitech.dealerpay.databinding.FragmentLoginBinding
import com.moveitech.dealerpay.ui.BaseFragment
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.viewModel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun initViews() {
        Handler(Looper.getMainLooper()).postDelayed({
            setDefaultUi(false, showNavigationDrawer = false)
        },200)

        binding.viewModel=viewModel
    }



    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(layoutInflater, container, false)


    override fun liveDataObserver() {
        with(viewModel)
        {
            setupGeneralViewModel(this)

            userNameError.observe(viewLifecycleOwner)
            {
                setErrorOnFields(it)
            }

            loginResponse.observe(viewLifecycleOwner){

                saveLoginResponseData(it)
            }

        }

    }

    private fun saveLoginResponseData(it: LoginResponse?) {

        lifecycleScope.launch {
            dataStoreHelper.apply {
                saveIsLogin(true)
                it?.let { it1 -> saveToken(it1.refreshToken) }
            }
        }
    }


    override fun btnListener() {

    }

    private fun setErrorOnFields(flag:Boolean) {

        if (flag) {
            binding.emailTextLayout.error = "Enter Valid Username"
            binding.passwordTextLayout.error = "Enter Valid Password"
        } else {
            binding.emailTextLayout.error = null
            binding.passwordTextLayout.error = null
        }
    }


}