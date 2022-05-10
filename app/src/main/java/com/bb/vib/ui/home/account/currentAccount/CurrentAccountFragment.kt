package com.bb.vib.ui.home.account.currentAccount

import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentCurrentAccountBinding
import com.bb.vib.ui.home.account.currentAccount.adapter.TransactionHistoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class CurrentAccountFragment : BaseFragment<FragmentCurrentAccountBinding>() {

    private val mCurrentAccountVM: CurrentAccountVM by viewModel()

    private lateinit var transactionHistoryAdapter: TransactionHistoryAdapter

    override fun mLayoutRes(): Int {
        return R.layout.fragment_current_account
    }

    override fun onViewReady() {

        mBinding.currentAccountVm = mCurrentAccountVM
        mBinding.lifecycleOwner = this

        transactionHistoryAdapter = TransactionHistoryAdapter(requireContext())
        mBinding.transactionHistoryRv.adapter = transactionHistoryAdapter

    }

}