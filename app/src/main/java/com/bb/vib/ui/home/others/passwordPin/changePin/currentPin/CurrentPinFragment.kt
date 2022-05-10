package com.bb.vib.ui.home.others.passwordPin.changePin.currentPin

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentCurrentPinBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import com.bb.vib.extentions.toggleLoader
import com.bb.vib.ui.accounts.loginPin.loginActiveBiometric.LoginActiveBiometricConfirmPinVM
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.GenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.buttonSuccessDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class CurrentPinFragment : BaseFragment<FragmentCurrentPinBinding>() {

    private val mCurrentPinVM: CurrentPinVM by viewModel()
    private val mLoginActiveBiometricConfirmPinVM: LoginActiveBiometricConfirmPinVM by viewModel()

    private lateinit var errorDialog: ErrorDialog

    private var inputCurrentPin = ""

    override fun mLayoutRes(): Int {
        return R.layout.fragment_current_pin
    }

    override fun onViewReady() {

        mBinding.currentPinVm = mCurrentPinVM
        mBinding.lifecycleOwner = this

        errorDialog = ErrorDialog(requireContext())

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
        showKeyword(requireContext(), mBinding.editPin1)

        val edit = arrayOf<EditText>(
            mBinding.editPin1,
            mBinding.editPin2,
            mBinding.editPin3,
            mBinding.editPin4
        )
        mBinding.editPin1.addTextChangedListener(
            GenericTextWatcher(
                edit,
                mBinding.editPin1

            )
        )
        mBinding.editPin2.addTextChangedListener(
            GenericTextWatcher(
                edit,
                mBinding.editPin2
            )
        )
        mBinding.editPin3.addTextChangedListener(
            GenericTextWatcher(
                edit,
                mBinding.editPin3
            )
        )
        mBinding.editPin4.addTextChangedListener(
            GenericTextWatcher(
                edit,
                mBinding.editPin4
            )
        )

    }

    private fun checkInputs(): Boolean {

        val consecutiveForwardPattern = Pattern.compile("^([0-9])\\1*$")
        val continuousForwardPattern = Pattern.compile("^(\\d{0,9}|\\d{4,})$")

        return when {
            consecutiveForwardPattern.matcher(inputCurrentPin).find() -> {
                showToast("PIN digits same")
                false
            }
//            continuousForwardPattern.matcher(inputPin).find() -> {
//                showToast("PIN digits continuous")
//                false
//            }
            else -> true
        }
    }

    override fun onResume() {

        mBinding.editPin4.text.clear()
        mBinding.editPin3.text.clear()
        mBinding.editPin2.text.clear()
        mBinding.editPin1.text.clear()

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
        showKeyword(requireContext(), mBinding.editPin1)

        mBinding.editPin4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().trim().length == 1) {
                    inputCurrentPin =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                    callVerifyPinApi()
                } else {
                        errorDialog.show()
                        errorDialog.setCanceledOnTouchOutside(false)

                        errorDialog.textErrorHead?.text = getString(R.string.text_invalid_pin)
                        errorDialog.textErrorMessage?.text =
                            getString(R.string.pin_same_digits_message)
                        errorDialog.buttonErrorDialog?.text = getString(R.string.text_continue)

                        errorDialog.buttonErrorDialog?.setOnClickListener {
                            errorDialog.dismiss()
                        }

                        mBinding.editPin4.text.clear()
                        mBinding.editPin3.text.clear()
                        mBinding.editPin2.text.clear()
                        mBinding.editPin1.text.clear()

                        mBinding.editPin1.requestFocus()
                        mBinding.editPin1.performClick()
                    }
                }

        })

        super.onResume()

    }

    private fun callVerifyPinApi() {

        observeCalls()

        mLoginActiveBiometricConfirmPinVM.verifyPin(
            getPreferences(requireContext()).getUserId,
            inputCurrentPin.toInt()
        )

    }

    private fun observeCalls() {

        mLoginActiveBiometricConfirmPinVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mLoginActiveBiometricConfirmPinVM.verifyPinResponse.observe(this, EventObserver {

            if (it.result != null) {
                findNavController().navigate(R.id.action_currentPinFragment_to_newPinFragment)
            } else {
                showToast("API Response Empty")
            }

        })

        mLoginActiveBiometricConfirmPinVM.errorResponse.observe(this, Observer {
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.editPin1, it)
            findNavController().popBackStack(R.id.navigation_others_password_pin, false)
            Log.d("Error", it.message!!)
        })

    }

}