package com.bb.vib.ui.home.others.passwordPin

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentPasswordPinBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PasswordPinFragment : BaseFragment<FragmentPasswordPinBinding>() {

    private val mPasswordPinVM: PasswordPinVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_password_pin
    }

    override fun onViewReady() {

        mBinding.passwordPinVm = mPasswordPinVM
        mBinding.lifecycleOwner = this

        mBinding.forgotPinLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", 1)
            findNavController().navigate(R.id.action_navigation_others_password_pin_to_navigation_other_forgot_pin_username, bundle)
        }

        mBinding.changePinLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_others_password_pin_to_currentPinFragment)
        }
    }

}