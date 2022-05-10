package com.bb.vib.ui.home.account

import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentAccountBinding
import kotlinx.android.synthetic.main.custom_error_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private val mAccountVM: AccountVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_account
    }

    override fun onViewReady() {

        mBinding.accountVm = mAccountVM
        mBinding.lifecycleOwner = this

        mBinding.seeAllCurrent.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_account_to_currentAccountFragment)
        }

    }

}