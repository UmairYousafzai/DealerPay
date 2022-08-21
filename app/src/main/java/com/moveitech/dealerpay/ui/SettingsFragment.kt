package com.moveitech.dealerpay.ui

import android.R
import android.content.Intent
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moveitech.dealerpay.LoginActivity
import com.moveitech.dealerpay.dataModel.response.user.UserResponse
import com.moveitech.dealerpay.databinding.FragmentSettingsBinding
import com.moveitech.dealerpay.util.DataStoreHelper
import com.moveitech.dealerpay.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    private val viewModel: UserViewModel by viewModels()
    private var userResponse = UserResponse()
    private val MENU_1_ITEM = Menu.FIRST
    private val dealerIDMap: MutableMap<String, String> = mutableMapOf()

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
                saveUserData(it, 0)
                this@SettingsFragment.userResponse = it
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

        binding.tvChangeDealerShip.setOnClickListener {
            buttonPopupMenu(it)
        }
    }

    private fun saveUserData(userResponse: UserResponse?, dealerPosition: Int) {
        if (userResponse != null) {
            viewModel.saveDepartments(userResponse.enterprises[0].dealers[dealerPosition].departments)
            lifecycleScope.launch {
                userResponse.enterprises[0].apply {
                    dataStoreHelper.saveEnterprise(enterpriseId, name)

                }
                userResponse.enterprises[0].dealers[dealerPosition].apply {

                    dataStoreHelper.saveDealer(name, dealerId)
                }
                dataStoreHelper.saveUserNAme(userResponse.firstName + " " + userResponse.lastName)

            }
        }

    }

    private fun buttonPopupMenu(view: View) {
        val menuIdList = ArrayList<Int>()
        for ((counter, model) in userResponse.enterprises[0].dealers.withIndex()) {
            menuIdList.add(Menu.FIRST + counter)
            dealerIDMap[model.name] = model.dealerId
        }
        val popupMenu = PopupMenu(requireContext(), view)
        for (counter in menuIdList.withIndex()) {
            popupMenu.menu.add(
                counter.index,
                menuIdList[counter.index],
                0,
                userResponse.enterprises[0].dealers[counter.index].name
            )

        }
//        popupMenu.getMenu().add(MENU2, MENU_2_ITEM, 1, getText(R.string.menu2))
//        val menu3: SubMenu =
//            popupMenu.getMenu().addSubMenu(MENU3, MENU_3_ITEM, 2, getText(R.string.menu3))
//        menu3.add(MENU3, MENU_3_1_ITEM, 0, getText(R.string.menu3_1))
//        menu3.add(MENU3, MENU_3_2_ITEM, 1, getText(R.string.menu3_2))
        popupMenu.setOnMenuItemClickListener { item ->
            lifecycleScope.launch {
                dealerIDMap[item.title.toString()]
                    ?.let { dataStoreHelper.saveDealer(item.title.toString(), it) }
                val dealer =
                    userResponse.enterprises[0].dealers.single { model -> model.name == item.title }
                viewModel.saveDepartments(dealer.departments)


            }

            false
        }
        popupMenu.show()
    }
}