package com.bb.vib.ui.home.others.smartOtpPin.forgotSmartOtp.usernameForgotSmartOtp

import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentUsernameForgotSmartOtpBinding
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class UsernameForgotSmartOtpFragment : BaseFragment<FragmentUsernameForgotSmartOtpBinding>() {

    private val mUsernameForgotSmartOtpVM: UsernameForgotSmartOtpVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_username_forgot_smart_otp
    }

    override fun onViewReady() {

        mBinding.usernameForgotSmartOtpVm = mUsernameForgotSmartOtpVM
        mBinding.lifecycleOwner = this

        mBinding.editUsername.requestFocus()
        mBinding.editUsername.performClick()
        showKeyword(requireContext(), mBinding.editUsername)

        mBinding.buttonContinue.setOnClickListener {
            if (mBinding.editUsername.text.isNullOrEmpty()) {
                showToast("Enter username")
            } else {
                findNavController().navigate(R.id.action_usernameForgotSmartOtpFragment_to_passwordForgotSmartOtpFragment)
            }
        }

    }

    override fun onResume() {

        mBinding.editUsername.text?.clear()

        mBinding.editUsername.requestFocus()
        mBinding.editUsername.performClick()
        showKeyword(requireContext(), mBinding.editUsername)

        super.onResume()

    }

}