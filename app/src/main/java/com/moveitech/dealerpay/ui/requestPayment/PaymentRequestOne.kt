package com.moveitech.dealerpay.ui.requestPayment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.moveitech.dealerpay.dataModel.request.payment.PaymentRequest
import com.moveitech.dealerpay.dataModel.request.payment.SendPayment
import com.moveitech.dealerpay.databinding.FragmentPaymentRequestOneBinding
import com.moveitech.dealerpay.ui.BaseFragment
import com.moveitech.dealerpay.util.setupAdapter

class PaymentRequestOne : BaseFragment<FragmentPaymentRequestOneBinding>() {
    override fun initViews() {
        setDefaultUi(showProfilePic = true)

        settingAutoCompleteText()
    }

    private fun settingAutoCompleteText() {


        val list=ArrayList<String>()
        list.add("Service")
        list.add("Sales")
        binding.selectItemSpinner?.setupAdapter(list)
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaymentRequestOneBinding.inflate(layoutInflater, container, false)



    override fun liveDataObserver() {
    }

    override fun btnListener() {
        binding.btnNext.setOnClickListener {
            val sendPayment= PaymentRequest(saleAmount = binding.etSaleAmount.text.toString(),
            payShareAmount = binding.payShareAmount.text.toString(),
            sendEmail = binding.etEmail.text.toString())
            moveToNextScreen(PaymentRequestOneDirections.actionPaymentRequestOneToPaymentReqTwoFragment(sendPayment))
        }
    }

}