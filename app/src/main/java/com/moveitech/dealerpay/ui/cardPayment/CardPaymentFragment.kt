package com.moveitech.dealerpay.ui.cardPayment

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moveitech.dealerpay.dataModel.request.cardPayment.CardPayment
import com.moveitech.dealerpay.dataModel.response.user.Department
import com.moveitech.dealerpay.databinding.FragmentCardPaymentBinding
import com.moveitech.dealerpay.ui.BaseFragment
import com.moveitech.dealerpay.ui.requestPayment.PaymentRequestOneDirections
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.util.setupAdapter
import com.moveitech.dealerpay.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CardPaymentFragment : BaseFragment<FragmentCardPaymentBinding>() {

    private val viewModel: UserViewModel by viewModels()
    private val departmentIDMap: MutableMap<String, String> = mutableMapOf()
    private val transactionTypeMap: MutableMap<String, String> = mutableMapOf()

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    override fun initViews() {
        setDefaultUi(showProfilePic = true)
        settingAutoCompleteText()
        textWacther()
    }

    private fun textWacther() {

        binding.etSaleAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.tvSaleAmount.text = "$ $p0"
                if (!binding.etSaleAmount.text.isNullOrEmpty()) {
                    if (!binding.payShareAmount.text.isNullOrEmpty()) {
                        binding.tvTotalAmount.text = "Total Amount :$${
                            binding.payShareAmount.text.toString()
                                .toInt() + binding.etSaleAmount.text.toString().toInt()
                        }"

                    } else {
                        binding.tvTotalAmount.text = "Total Amount :$${binding.etSaleAmount.text}"

                    }

                } else {
                    binding.tvTotalAmount.text = "Total Amount :$${binding.payShareAmount.text}"
                }
            }

        })
        binding.payShareAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.tvShareAmount.text = "$ $p0"
                if (!binding.etSaleAmount.text.isNullOrEmpty()) {
                    if (!binding.payShareAmount.text.isNullOrEmpty()) {
                        binding.tvTotalAmount.text = "Total Amount :$${
                            binding.payShareAmount.text.toString()
                                .toInt() + binding.etSaleAmount.text.toString().toInt()
                        }"

                    } else {
                        binding.tvTotalAmount.text = "Total Amount :$${binding.etSaleAmount.text}"

                    }

                } else {
                    binding.tvTotalAmount.text = "Total Amount :$${binding.payShareAmount.text}"
                }
            }

        })
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
            binding.etSelectItem.setText(nameList[0])
            binding.etSelectItem.setupAdapter(nameList)
        }


    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCardPaymentBinding.inflate(layoutInflater, container, false)


    override fun liveDataObserver() {
    }

    override fun btnListener() {
        binding.btnNext.setOnClickListener {
            setupData()
        }
    }

    private fun setupData() {

        val cardPayment = CardPayment()


        cardPayment.departmentId =
            departmentIDMap[binding.etSelectItem.text.toString()].toString()
        if (!binding.etSaleAmount.text.isNullOrEmpty()) {
            cardPayment.saleAmount = binding.etSaleAmount.text.toString().toInt()
        }
        if (!binding.payShareAmount.text.isNullOrEmpty()) {
            cardPayment.hasPayShare = true
            cardPayment.payShareAmount = binding.payShareAmount.text.toString().toInt()
        }
        cardPayment.transactionTypeId =
            transactionTypeMap[binding.etSelectItem.text.toString()].toString()
        cardPayment.paymentId = UUID.randomUUID().toString()

        getDataFromDataStore(cardPayment)

    }

    private fun getDataFromDataStore(cardPayment: CardPayment) {

        lifecycleScope.launch {
            dataStoreHelper.apply {
                dealerID.collect {
                    cardPayment.dealerId = it
                    requireActivity().runOnUiThread {
                        val action =
                            CardPaymentFragmentDirections.actionCardPaymentFragmentToPaymentFragmentTwo()
                        action.model = cardPayment
                        moveToNextScreen(action)

                    }
                }

            }

        }
    }

}