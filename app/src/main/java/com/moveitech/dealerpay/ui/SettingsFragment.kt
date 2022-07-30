package com.moveitech.dealerpay.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moveitech.dealerpay.IDTECHPack.Fragments.SettingsViewModel
import com.moveitech.dealerpay.LoginActivity
import com.moveitech.dealerpay.databinding.FragmentSettingsBinding
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.viewModel.HomeViewModel
import com.moveitech.dealerpay.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
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

}