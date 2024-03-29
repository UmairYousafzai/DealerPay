package com.moveitech.dealerpay.ui.requestPayment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moveitech.dealerpay.dataModel.request.payment.PaymentRequest
import com.moveitech.dealerpay.databinding.FragmentPaymentRequestTwoBinding
import com.moveitech.dealerpay.ui.BaseFragment
import com.moveitech.dealerpay.ui.PaymentInte.PaymentInteActivity
import com.moveitech.dealerpay.viewModel.PaymentRequestViewModel
import com.moveitech.dealerpay.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentReqTwoFragment : BaseFragment<FragmentPaymentRequestTwoBinding>() {

    private val paymentRequest by lazy { arguments?.let { PaymentReqTwoFragmentArgs.fromBundle(it).paymentRequestModel } }
    private val viewModel: PaymentRequestViewModel by viewModels()
    override fun initViews() {
        setDefaultUi(showNavigationDrawer = false, showProfilePic = false, showToolbar = true)
        if (paymentRequest!=null)
        {
            binding.viewModel= viewModel
        }

    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaymentRequestTwoBinding.inflate(layoutInflater, container, false)


    override fun liveDataObserver() {
        with(viewModel)
        {
            setupGeneralViewModel(this)
            customerLiveData.observe(viewLifecycleOwner){
                paymentRequest?.customer =it
                paymentRequest?.let { it1 -> paymentRequest(it1) }
            }
        }
    }

    override fun btnListener() {


    }

}