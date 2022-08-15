package com.moveitech.dealerpay.ui.requestPayment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.moveitech.dealerpay.dataModel.request.payment.PaymentRequest
import com.moveitech.dealerpay.dataModel.request.payment.SendPayment
import com.moveitech.dealerpay.dataModel.response.user.Department
import com.moveitech.dealerpay.databinding.FragmentPaymentRequestOneBinding
import com.moveitech.dealerpay.ui.BaseFragment
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.util.setupAdapter
import com.moveitech.dealerpay.viewModel.PaymentRequestViewModel
import com.moveitech.dealerpay.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class PaymentRequestOne : BaseFragment<FragmentPaymentRequestOneBinding>() {
    private val viewModel: UserViewModel by viewModels()
    private val departmentIDMap: MutableMap<String, String> = mutableMapOf()
    private val transactionTypeMap: MutableMap<String, String> = mutableMapOf()

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    override fun initViews() {
        setDefaultUi(showProfilePic = true)

        settingAutoCompleteText()
    }

    private fun settingAutoCompleteText() {

        viewModel.getDepartmentList().observe(viewLifecycleOwner) {
            val departmentList = it as ArrayList<Department>
            val nameList = ArrayList<String>()

            for (model in departmentList) {
                nameList.add(model.name)
                departmentIDMap[model.name] = model.departmentId
                transactionTypeMap[model.name] = model.defaultTransactionTypeId
            }
            binding.selectItemSpinner.setText(nameList[0])
            binding.selectItemSpinner.setupAdapter(nameList)
        }

        val list = ArrayList<String>()

        list.add("Email")
        list.add("SMS")
        binding.etEmail.setText("Email")
        binding.etEmail.setupAdapter(list)
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaymentRequestOneBinding.inflate(layoutInflater, container, false)


    override fun liveDataObserver() {


//        with(viewModel){
//            getDepartmentList()
//            departments.observe(viewLifecycleOwner)
//            {
//                val list:ArrayList<Department> = it as ArrayList<Department>
//                val nameList = ArrayList<String>()
//
//                for (model in list) {
//                    nameList.add(model.name)
//                    departmentIDMap[model.name] = model.departmentId;
//                }
//                binding.selectItemSpinner?.setupAdapter(nameList)
//            }        }

    }

    override fun btnListener() {
        binding.btnNext.setOnClickListener {

            setupData()
        }
    }

    private fun setupData() {

        val sendPayment = PaymentRequest()
        val sendVia = binding.etEmail.text.toString()
        if (sendVia == "Email") {
            sendPayment.sendEmail = true
        } else {
            sendPayment.sendSMS = true
        }

        sendPayment.departmentId =
            departmentIDMap[binding.selectItemSpinner.text.toString()].toString()
        sendPayment.saleAmount = binding.etSaleAmount.text.toString().toLong()
        if (!binding.payShareAmount.text.isNullOrEmpty()) {
            sendPayment.hasPayShare = true
            sendPayment.payShareAmount = binding.payShareAmount.text.toString().toLong()
        }
        sendPayment.transactionTypeId =
            transactionTypeMap[binding.selectItemSpinner.text.toString()].toString()
        sendPayment.paymentId = UUID.randomUUID().toString()

        getDataFromDataStore(sendPayment)

    }

    private fun getDataFromDataStore(sendPayment: PaymentRequest) {

        lifecycleScope.launch {
            dataStoreHelper.apply {
                dealerID.collect {
                    sendPayment.dealerId = it
                    requireActivity().runOnUiThread {
                        moveToNextScreen(
                            PaymentRequestOneDirections.actionPaymentRequestOneToPaymentReqTwoFragment(
                                sendPayment
                            )
                        )
                    }
                }

            }

        }
    }

}