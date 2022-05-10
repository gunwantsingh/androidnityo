package com.bb.vib.ui.home.others.smartOtpPin.activeSmartOtp.confirmSmartOtp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentConfirmSmartOtpBinding
import com.bb.vib.extentions.*
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.SmartOtpGenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConfirmSmartOtpFragment : BaseFragment<FragmentConfirmSmartOtpBinding>() {

    private var inputSmartOtp = ""
    private var incomingOtp = ""
    private var isLongOtp = false

    private lateinit var errorDialog: ErrorDialog

    private lateinit var edit: Array<EditText>

    private val mConfirmSmartOtpVM: ConfirmSmartOtpVM by viewModel()

    protected var yourTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.toString().trim().length == 1) {
                if (isLongOtp) {
                    inputSmartOtp =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString() +
                                mBinding.editPin5.text.toString() + mBinding.editPin6.text.toString()
                } else {
                    inputSmartOtp =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                }
                if (inputSmartOtp == incomingOtp) {
                    callSendActivationCodeApi()
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

                    clearAllInputs()

                }
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

                clearAllInputs()

            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // your logic here
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // your logic here
        }
    }

    override fun mLayoutRes(): Int {
        return R.layout.fragment_confirm_smart_otp
    }

    override fun onViewReady() {

        mBinding.confirmSmartOtpVm = mConfirmSmartOtpVM
        mBinding.lifecycleOwner = this

        if (arguments != null) {
            incomingOtp = requireArguments().getString("smartOtp", "")
            isLongOtp = requireArguments().getBoolean("isLongOtp")
        }

        errorDialog = ErrorDialog(requireContext())

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
        showKeyword(requireContext(), mBinding.editPin1)

        if (isLongOtp) {

            mBinding.editPin5.visibility = View.VISIBLE
            mBinding.editPin6.visibility = View.VISIBLE

            edit = arrayOf<EditText>(
                mBinding.editPin1,
                mBinding.editPin2,
                mBinding.editPin3,
                mBinding.editPin4,
                mBinding.editPin5,
                mBinding.editPin6
            )
        } else {

            mBinding.editPin5.visibility = View.GONE
            mBinding.editPin6.visibility = View.GONE

            edit = arrayOf<EditText>(
                mBinding.editPin1,
                mBinding.editPin2,
                mBinding.editPin3,
                mBinding.editPin4
            )
        }

    }

    private fun clearAllInputs() {
        mBinding.editPin6.text.clear()
        mBinding.editPin5.text.clear()
        mBinding.editPin4.text.clear()
        mBinding.editPin3.text.clear()
        mBinding.editPin2.text.clear()
        mBinding.editPin1.text.clear()

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
    }

    private fun registerSmartOtpGenericTextWatcher() {

        mBinding.editPin1.addTextChangedListener(
            SmartOtpGenericTextWatcher(
                edit,
                mBinding.editPin1
            )
        )
        mBinding.editPin2.addTextChangedListener(
            SmartOtpGenericTextWatcher(
                edit,
                mBinding.editPin2
            )
        )
        mBinding.editPin3.addTextChangedListener(
            SmartOtpGenericTextWatcher(
                edit,
                mBinding.editPin3
            )
        )
        mBinding.editPin4.addTextChangedListener(
            SmartOtpGenericTextWatcher(
                edit,
                mBinding.editPin4
            )
        )

        if (isLongOtp) {
            mBinding.editPin5.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    edit,
                    mBinding.editPin5
                )
            )
            mBinding.editPin6.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    edit,
                    mBinding.editPin6
                )
            )
        }

    }

    override fun onResume() {
        super.onResume()

        clearAllInputs()
        showKeyword(requireContext(), mBinding.editPin1)

        registerSmartOtpGenericTextWatcher()
        if (isLongOtp) {
            mBinding.editPin6.addTextChangedListener(yourTextWatcher)
        } else {
            mBinding.editPin4.addTextChangedListener(yourTextWatcher)
        }
    }

    private fun callSendActivationCodeApi() {

        observeCalls()
        mConfirmSmartOtpVM.sendActivationCode(getPreferences(requireContext()).getUserId)

    }

    private fun observeCalls() {

        mConfirmSmartOtpVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mConfirmSmartOtpVM.activationCodeResponse.observe(this, EventObserver {

            if (it.result != null) {
                val bundle = Bundle()
                bundle.putString("smartOtp", inputSmartOtp)
                findNavController().navigate(
                    R.id.action_confirmSmartOtpFragment_to_otpAuthenticationFragment,
                    bundle
                )
            } else {
                showToast("API Response Empty")
            }

        })

        mConfirmSmartOtpVM.errorResponse.observe(this, Observer {
            showErrorDialog(
                requireContext(),
                "Occurring error",
                "Error occurs, please try again after a few minutes"
            )
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.editPin1, it)
            Log.d("Error", it.message!!)
        })

    }

}