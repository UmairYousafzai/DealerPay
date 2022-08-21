package com.moveitech.dealerpay.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moveitech.dealerpay.IDTECHPack.Fragments.SettingsViewModel
import com.moveitech.dealerpay.LoginActivity
import com.moveitech.dealerpay.dataModel.response.user.UserResponse
import com.moveitech.dealerpay.databinding.FragmentSettingsBinding
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.viewModel.HomeViewModel
import com.moveitech.dealerpay.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private val viewModel: UserViewModel by viewModels()

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper

    override fun initViews() {

        setDefaultUi(false, showNavigationDrawer = false)
        binding.viewModel = viewModel

        getLocalData()

    }

    private fun getLocalData() {

        lifecycleScope.launch {

            dataStoreHelper.dealerName.collect {
                binding.tvDealerShip.text = it
            }
        }
        lifecycleScope.launch {

            dataStoreHelper.enterpriseName.collect {
                binding.activeEntreprise.text = it

            }

        }
        lifecycleScope.launch {

            dataStoreHelper.userName.collect {
                binding.tvFullNAme.text = it
            }

        }
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(layoutInflater, container, false)


    override fun liveDataObserver() {
        with(viewModel)
        {
            setupGeneralViewModel(this)
            getUser()
            userResponse.observe(viewLifecycleOwner) {
                binding.model = it
                binding.executePendingBindings()
                saveUserData(it)
                saveDepartments(it.enterprises[0].dealers[0].departments)
            }
            logoutResponse.observe(viewLifecycleOwner) {
                if (it) {
                    lifecycleScope.launch {
                        dataStoreHelper.clear()
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    override fun btnListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun saveUserData(userResponse: UserResponse?) {
        if (userResponse != null) {
            viewModel.saveDepartments(userResponse.enterprises[0].dealers[0].departments)
            lifecycleScope.launch {
                userResponse.enterprises[0].apply {
                    dataStoreHelper.saveEnterprise(enterpriseId, name)

                }
                userResponse.enterprises[0].dealers[0].apply {
                    dataStoreHelper.saveDealer(name, dealerId)
                }
                dataStoreHelper.saveUserNAme(userResponse.firstName + " " + userResponse.lastName)

            }
        }

    }

}