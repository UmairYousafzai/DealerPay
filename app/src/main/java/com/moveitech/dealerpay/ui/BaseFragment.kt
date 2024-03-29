package com.moveitech.dealerpay.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.moveitech.dealerpay.LoginActivity
import com.moveitech.dealerpay.MainActivity
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.util.DialogUtils
import com.moveitech.dealerpay.util.safeNavigate
import com.moveitech.dealerpay.util.showSnackBar
import com.moveitech.dealerpay.viewModel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class BaseFragment <T: ViewBinding>:Fragment() {

    protected lateinit var binding:T
    lateinit var dialog: AlertDialog
    @Inject
    lateinit var storeHelper:DataStoreHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog = DialogUtils.getProgressDialog(requireContext())

        binding =getFragmentBinding(layoutInflater,container)
       return binding.root
    }


    abstract fun initViews()


    abstract fun getFragmentBinding(layoutInflater: LayoutInflater,container: ViewGroup?): T




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        liveDataObserver()
        btnListener()
    }

    override fun onResume() {
        super.onResume()
        initViews()

    }

    abstract fun  liveDataObserver()

    abstract fun btnListener()
    protected fun setupGeneralViewModel(generalViewModel: BaseViewModel) {
        with(generalViewModel) {
            dialogMessage.observe(viewLifecycleOwner) {
//               showAlertDialog(it)
                showSnackBar(it)
            }

            progressBar.observe(viewLifecycleOwner) {
                    showProgressDialog(it)

            }
            sessionExpire.observe(viewLifecycleOwner)
            {
                lifecycleScope.launch {
                    storeHelper.clear()
                    val intent= Intent(requireActivity(),LoginActivity::class.java)
                    requireActivity().startActivity(intent)
                    requireActivity().finish()
                }

            }
        }
    }


    protected fun showProgressDialog(show: Boolean) {

        if (show) {
            if (!dialog.isShowing)
                dialog.apply { show() }
        } else if (!show) {
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }

    protected fun moveToNextScreen(navDirections: NavDirections)
    {
         findNavController().safeNavigate(navDirections)
    }

    protected fun setDefaultUi(showToolbar: Boolean = true,showNavigationDrawer:Boolean=true,showProfilePic:Boolean=false) {

        (requireActivity() as MainActivity).setDefaultUi(showToolbar,showNavigationDrawer,showProfilePic)
    }

    protected fun onBackPressed()
    {
        requireActivity().onBackPressed();
    }
}