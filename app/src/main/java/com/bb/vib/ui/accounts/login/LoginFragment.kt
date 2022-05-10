package com.bb.vib.ui.accounts.login

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.api.model.request.LoginRequestModel
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentLoginBinding
import com.bb.vib.extentions.*
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val mLoginVM: LoginVM by viewModel()

    private var inputUsername = ""
    private var inputPassword = ""

    private lateinit var errorDialog: ErrorDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun onViewReady() {

        mBinding.loginVm = mLoginVM
        mBinding.lifecycleOwner = this

        errorDialog = ErrorDialog(requireContext())

        mBinding.textForgotAccount.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_inputBusinessLicenseFragment)
        }

        mBinding.buttonLogin.setOnClickListener {
            if (checkInputs()) {
                val loginRequest = LoginRequestModel(
                    "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    "",
                    inputPassword,
                    0,
                    0,
                    "SmartOtp",
                    "",
                    "",
                    0,
                    "",
                    inputUsername,
                    "",
                    "",
                    getPreferences(requireContext()).getLanguage,
                    "",
                    "",
                    "",
                    "",
                    "",
                    0,
                    getCurrentTimeStamp(),
                    getCurrentTimeStamp(),
                    getCurrentTimeStamp(),
                    getCurrentTimeStamp(),
                    getCurrentTimeStamp(),
                    getCurrentTimeStamp(),
                    true
                )
                observeCalls()
                mLoginVM.login(loginRequest)
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

    private fun checkInputs(): Boolean {

        val specialPattern = Pattern.compile("[!@#$%&*()+=|<>?{}\\[\\]~-]")

        inputUsername = mBinding.editUsername.text.toString()
        inputPassword = mBinding.editPassword.text.toString()

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

    override fun onResume() {
        super.onResume()

        mBinding.editPassword.text?.clear()
        mBinding.editUsername.text?.clear()
        mBinding.editUsername.requestFocus()
        mBinding.editUsername.performClick()
    }

    private fun observeCalls() {

        mLoginVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mLoginVM.loginResponse.observe(this, EventObserver {

            if (!it.result.isNullOrEmpty()) {
                getPreferences(requireContext()).setRegister(true)
                getPreferences(requireContext()).setUserId(it.result)
                val bundle = Bundle()
                bundle.putInt("type", 1)
                findNavController().navigate(R.id.action_navigation_login_to_navigation_create_pin, bundle)
            } else {
                showToast("API Response Empty")
            }

        })

        mLoginVM.errorResponse.observe(this, Observer {
            showErrorDialog(
                requireContext(),
                "Invalid login",
                "The username or password is not correct or does not match. Please try again."
            )
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.buttonLogin, it)
            Log.d("Error", it.message!!)
        })


    }

}