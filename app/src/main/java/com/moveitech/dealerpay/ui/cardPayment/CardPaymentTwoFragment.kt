package com.moveitech.dealerpay.ui.cardPayment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.moveitech.dealerpay.databinding.FragmentPaymentTwoBinding
import com.moveitech.dealerpay.ui.BaseFragment
import com.moveitech.dealerpay.ui.PaymentInte.PaymentInteActivity

class CardPaymentTwoFragment: BaseFragment<FragmentPaymentTwoBinding>(){
    override fun initViews() {
        setDefaultUi(true, showNavigationDrawer = false,false)
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaymentTwoBinding.inflate(layoutInflater, container, false)

    override fun liveDataObserver() {
    }

    override fun btnListener() {
        binding.btnProcessSale.setOnClickListener {
            val intent = Intent(requireActivity(), PaymentInteActivity::class.java)
            startActivity(intent)
        }
    }
}