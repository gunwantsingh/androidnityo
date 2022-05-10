package com.bb.vib.ui.home.others.otherUseFingerprint

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentOtherUseFingerprintBinding
import com.bb.vib.ui.accounts.AccountsActivity
import com.bb.vib.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class OtherUseFingerprintFragment : BaseFragment<FragmentOtherUseFingerprintBinding>() {

    private val mUseFingerprintVM: OtherUseFingerprintVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_other_use_fingerprint
    }

    override fun onViewReady() {

        mBinding.useFingerprintVm = mUseFingerprintVM
        mBinding.lifecycleOwner = this

        mBinding.buttonAgree.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", 3)
            findNavController().navigate(
                R.id.action_navigation_other_use_fingerprint_to_navigation_other_input_pin,
                bundle
            )
        }

        mBinding.buttonLater.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}