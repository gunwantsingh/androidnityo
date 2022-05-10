package com.bb.vib.ui.home.account.currentAccount.transactionAdvice

import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentTransactionAdviceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransactionAdviceFragment : BaseFragment<FragmentTransactionAdviceBinding>() {

    private val mTransactionAdviceVM: TransactionAdviceVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_transaction_advice
    }

    override fun onViewReady() {

        mBinding.transactionAdviceVm = mTransactionAdviceVM
        mBinding.lifecycleOwner = this

    }

}