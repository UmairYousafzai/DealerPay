package com.moveitech.dealerpay.ui.cardPayment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.moveitech.dealerpay.R
import com.moveitech.dealerpay.databinding.FragmentPaymentTwoBinding
import com.moveitech.dealerpay.ui.BaseFragment
import com.moveitech.dealerpay.ui.PaymentInte.PaymentInteActivity
import com.moveitech.dealerpay.ui.requestPayment.PaymentReqTwoFragmentArgs

class CardPaymentTwoFragment: BaseFragment<FragmentPaymentTwoBinding>(){
    private val cardPayment by lazy { arguments?.let { CardPaymentTwoFragmentArgs.fromBundle(it).model } }

    override fun initViews() {
        setDefaultUi(true, showNavigationDrawer = false,false)
        binding.model= cardPayment
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaymentTwoBinding.inflate(layoutInflater, container, false)

    override fun liveDataObserver() {
    }

    override fun btnListener() {
        binding.btnProcessSale.setOnClickListener {
            val bundle = bundleOf("cardPayment" to cardPayment)
            view?.findNavController()?.navigate(R.id.paymentInteFragment, bundle)
//            moveToNextScreen(CardPaymentTwoFragmentDirections.actionCardPaymentFragmentTwoToPaymentInteFragment(cardPayment!!))
        }
    }
}