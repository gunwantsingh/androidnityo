package com.bb.vib.ui.home.others.smartOtpPin.forgotSmartOtp.passwordForgotSmartOtp

import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentPasswordForgotSmartOtpBinding
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class PasswordForgotSmartOtpFragment : BaseFragment<FragmentPasswordForgotSmartOtpBinding>() {

    private val mPasswordForgotSmartOtpVM: PasswordForgotSmartOtpVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_password_forgot_smart_otp
    }

    override fun onViewReady() {

        mBinding.passwordForgotSmartOtpVm = mPasswordForgotSmartOtpVM
        mBinding.lifecycleOwner = this

        mBinding.editPassword.requestFocus()
        mBinding.editPassword.performClick()
        showKeyword(requireContext(), mBinding.editPassword)

        mBinding.buttonContinue.setOnClickListener {
            if (mBinding.editPassword.text.isNullOrEmpty()) {
                showToast("Enter password")
            } else {
                findNavController().navigate(R.id.action_passwordForgotSmartOtpFragment_to_newSmartOtpFragment)
            }
        }

    }

    override fun onResume() {

        mBinding.editPassword.text?.clear()

        mBinding.editPassword.requestFocus()
        mBinding.editPassword.performClick()
        showKeyword(requireContext(), mBinding.editPassword)

        super.onResume()

    }

}