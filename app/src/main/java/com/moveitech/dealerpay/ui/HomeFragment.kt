package com.moveitech.dealerpay.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moveitech.dealerpay.adapter.TransactionAdapter
import com.moveitech.dealerpay.databinding.FragmentHomeBinding
import com.moveitech.dealerpay.util.closeKeyBoard
import com.moveitech.dealerpay.util.observer
import com.moveitech.dealerpay.viewModel.AuthenticationViewModel
import com.moveitech.dealerpay.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()

    override fun initViews() {

        binding.viewModel= viewModel

        setDefaultUi(showProfilePic = true)
        setDefaultUi(true, showNavigationDrawer = true, showProfilePic = true)

        Handler(Looper.getMainLooper()).postDelayed({
            closeKeyBoard()

        },100)

        observerSearchView()
    }

    private fun observerSearchView() {

        binding.searchView.onActionViewExpanded()
        binding.searchView.isIconified = true

        binding.searchView.observer {
            ( binding.rvTransaction.adapter as TransactionAdapter).filter.filter(it)
        }
    }


    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentHomeBinding.inflate(layoutInflater,container,false)

    override fun liveDataObserver() {
        with(viewModel)
        {
            setupGeneralViewModel(this)
            Handler(Looper.getMainLooper()).postDelayed({
                getTransactions()
            }
            ,500)
        }
    }

    override fun btnListener() {
    }
}