package com.bb.vib.ui.home.others.accountInfo

import android.util.Log
import androidx.lifecycle.Observer
import com.bb.vib.R
import com.bb.vib.api.model.response.UserDetailsResponseModel
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentAccountInfoBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.extentions.showToast
import com.bb.vib.extentions.toggleLoader
import com.bb.vib.ui.home.others.accountInfo.adapter.AccountInfoAdapter
import com.bb.vib.utils.ErrorUtil
import org.koin.androidx.viewmodel.ext.android.viewModel


class AccountInfoFragment : BaseFragment<FragmentAccountInfoBinding>() {

    private val mAccountInfoVM: AccountInfoVM by viewModel()

    private lateinit var accountInfoAdapter: AccountInfoAdapter

    override fun mLayoutRes(): Int {
        return R.layout.fragment_account_info
    }

    override fun onViewReady() {

        mBinding.accountInfoVm = mAccountInfoVM
        mBinding.lifecycleOwner = this

        observeCalls()
        mAccountInfoVM.getUserDetails(getPreferences(requireContext()).getUserId)

        accountInfoAdapter = AccountInfoAdapter(requireContext())
        mBinding.accountInfoRv.adapter = accountInfoAdapter

    }

    private fun observeCalls() {

        mAccountInfoVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mAccountInfoVM.userDetailsResponse.observe(this, EventObserver {

            if (it.result != null) {
                setAccountUserData(it.result)
            } else {
                showToast("API Response Error")
            }

        })

        mAccountInfoVM.errorResponse.observe(this, Observer {
            ErrorUtil.handlerGeneralError(requireContext(), mBinding.textAccountName, it)
            Log.d("Error", it.message!!)
        })

    }

    private fun setAccountUserData(userData: UserDetailsResponseModel.Result) {

        mBinding.textAccountName.text = userData.fullName

    }

}