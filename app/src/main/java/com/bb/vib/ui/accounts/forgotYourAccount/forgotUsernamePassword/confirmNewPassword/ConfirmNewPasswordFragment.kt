package com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.confirmNewPassword

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentConfirmNewPasswordBinding
import com.bb.vib.extentions.showToast
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConfirmNewPasswordFragment : BaseFragment<FragmentConfirmNewPasswordBinding>() {

    private val mConfirmNewPasswordVM: ConfirmNewPasswordVM by viewModel()

    private var incomingPassword = ""
    private var inputPassword = ""

    private var selectedOption = 0

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_confirm_new_password
    }

    override fun onViewReady() {

        mBinding.confirmNewPasswordVm = mConfirmNewPasswordVM
        mBinding.lifecycleOwner = this

        if (arguments != null) {
            incomingPassword = requireArguments().getString("inputPassword", "")
            selectedOption = requireArguments().getInt("selectedOption")
        }

        alertDialog = AlertDialog(requireContext())

        mBinding.buttonContinue.setOnClickListener {
            if (checkInputs()) {
                if (incomingPassword == inputPassword) {
                    val bundle = Bundle()
                    bundle.putInt("selectedOption", selectedOption)
                    findNavController().navigate(R.id.action_confirmNewPasswordFragment_to_authenticateOtpFragment, bundle)
                } else {
                    showToast("Password Mismatch")
                }
            } else {
                showToast("Password Incorrect")
            }
        }

    }

    private fun checkInputs(): Boolean {

        inputPassword = mBinding.editNewPassword.text.toString()
        return !mBinding.editNewPassword.text.isNullOrEmpty()
    }

    override fun onResume() {
        super.onResume()

        mBinding.editNewPassword.text?.clear()
        mBinding.editNewPassword.requestFocus()
        mBinding.editNewPassword.performClick()
    }

}