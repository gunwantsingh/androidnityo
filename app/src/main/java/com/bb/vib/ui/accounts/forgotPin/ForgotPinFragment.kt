package com.bb.vib.ui.accounts.forgotPin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentForgotPinBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import com.bb.vib.extentions.toggleLoader
import com.bb.vib.ui.accounts.AccountsActivity
import com.bb.vib.ui.home.others.otherForgotPin.OtherForgotPinVM
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class ForgotPinFragment : BaseFragment<FragmentForgotPinBinding>() {

    private val mForgotPinVM: ForgotPinVM by viewModel()
    private val mOtherForgotPinVM: OtherForgotPinVM by viewModel()

    var inputUsername = ""
    var inputPassword = ""

    private lateinit var errorDialog: ErrorDialog

    private var type = 1

    override fun mLayoutRes(): Int {
        return R.layout.fragment_forgot_pin
    }

    override fun onViewReady() {

        if (arguments != null) {
            type = requireArguments().getInt("type")
        }

        mBinding.forgotPinVm = mForgotPinVM
        mBinding.lifecycleOwner = this

        if (type == 1) {
            mBinding.editUsername.requestFocus()
            mBinding.editUsername.performClick()
            showKeyword(requireContext(), mBinding.editUsername)
        } else {

            mBinding.textEnterUsername.visibility = View.GONE
            mBinding.textFieldUsername.visibility = View.GONE
            mBinding.textEnterPassword.visibility = View.VISIBLE
            mBinding.textFieldPassword.visibility = View.VISIBLE

            mBinding.editPassword.requestFocus()
            mBinding.editPassword.performClick()
            showKeyword(requireContext(), mBinding.editPassword)

        }

        errorDialog = ErrorDialog(requireContext())

        mBinding.buttonContinue.setOnClickListener {
            if (type == 1) {
                if (checkUsername()) {
                    callVerifyUsernameApi()
                } else {
                    errorDialog.show()
                    errorDialog.setCanceledOnTouchOutside(false)

                    errorDialog.textErrorHead?.text = getString(R.string.error_invalid_login)
                    errorDialog.textErrorMessage?.text = getString(R.string.error_invalid_login_message)
                    errorDialog.buttonErrorDialog?.text = getString(R.string.text_continue)

                    errorDialog.buttonErrorDialog?.setOnClickListener {
                        errorDialog.dismiss()
                    }
                }
            } else {
                if (checkPassword()) {
                    callVerifyPasswordApi()
                } else {
                    errorDialog.show()
                    errorDialog.setCanceledOnTouchOutside(false)

                    errorDialog.textErrorHead?.text = getString(R.string.error_invalid_login)
                    errorDialog.textErrorMessage?.text = getString(R.string.error_invalid_login_message)
                    errorDialog.buttonErrorDialog?.text = getString(R.string.text_continue)

                    errorDialog.buttonErrorDialog?.setOnClickListener {
                        errorDialog.dismiss()
                    }
                }
            }
        }

    }

    override fun onResume() {

        mBinding.editUsername.text?.clear()
        mBinding.editPassword.text?.clear()

        if (type == 1) {
            mBinding.editUsername.requestFocus()
            mBinding.editUsername.performClick()
        } else {
            mBinding.editPassword.requestFocus()
            mBinding.editPassword.performClick()
        }
        showKeyword(requireContext(), mBinding.editUsername)

        super.onResume()

    }

    private fun checkUsername(): Boolean {

        val specialPattern = Pattern.compile("[!@#$%&*()+=|<>?{}\\[\\]~-]")
        inputUsername = mBinding.editUsername.text.toString()

        return when {
            inputUsername.length !in 12 downTo 6 -> {
                showToast("Username Length")
                false
            }
            specialPattern.matcher(inputUsername).find() -> {
                showToast("Username Pattern")
                false
            }
            inputUsername.contains("__", false) -> {
                showToast("Consecutive Underscore")
                false
            }
            else -> true
        }
    }

    private fun checkPassword(): Boolean {

        val specialPattern = Pattern.compile("[!@#$%&*()+=|<>?{}\\[\\]~-]")
        inputPassword = mBinding.editPassword.text.toString()

        return when {
            inputPassword.length !in 12 downTo 6 -> {
                showToast("Password Length")
                false
            }
            (inputPassword.length - inputPassword.replace("[a-z]".toRegex(), "").length) < 1 -> {
                showToast("Password Small Caps")
                false
            }
            (inputPassword.length - inputPassword.replace("[A-Z]".toRegex(), "").length) < 1 -> {
                showToast("Password Big Caps")
                false
            }
            (inputPassword.length - inputPassword.replace(
                "[!@#\$%&_*()+=|<>?{}\\[\\]~-]".toRegex(),
                ""
            ).length) < 1 -> {
                showToast("Password Pattern")
                false
            }
            else -> true
        }
    }

    private fun callVerifyUsernameApi() {

        observeCalls()
        mOtherForgotPinVM.verifyUsername(
            getPreferences(requireContext()).getUserId,
            inputUsername
        )

    }

    private fun callVerifyPasswordApi() {

        observeCalls()
        mOtherForgotPinVM.verifyPassword(
            getPreferences(requireContext()).getUserId,
            inputPassword
        )

    }

    private fun observeCalls() {

        mOtherForgotPinVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mOtherForgotPinVM.verifyUsernameResponse.observe(this, EventObserver {

            if (it.result!!) {
                val bundle = Bundle()
                bundle.putInt("type", 2)
                findNavController().navigate(
                    R.id.action_navigation_forgot_pin_username_to_navigation_forgot_pin_password,
                    bundle
                )
            } else {
                showToast("API Response Empty")
            }

        })

        mOtherForgotPinVM.verifyPasswordResponse.observe(this, EventObserver {

            if (it.result!!) {
                val bundle = Bundle()
                bundle.putInt("type", 4)
                findNavController().navigate(
                    R.id.action_navigation_forgot_pin_password_to_navigation_create_new_pin,
                    bundle
                )
            } else {
                showToast("API Response Empty")
            }

        })

        mOtherForgotPinVM.errorResponse.observe(this, Observer {
            ErrorUtil.handlerGeneralError(requireContext(), mBinding.buttonContinue, it)
            Log.d("Error", it.message!!)
        })

    }

}