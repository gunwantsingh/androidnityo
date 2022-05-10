package com.bb.vib.ui.home.others.smartOtpPin.inactiveSmartOtp.smartOtpAuthentication

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentSmartOtpAuthenticationBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import com.bb.vib.utils.SmartOtpGenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.buttonSuccessDialog
import kotlinx.android.synthetic.main.custom_success_dialog.successCrossButton
import org.koin.androidx.viewmodel.ext.android.viewModel


class SmartOtpAuthenticationFragment : BaseFragment<FragmentSmartOtpAuthenticationBinding>() {

    private var inputSmartOtp = ""

    private lateinit var successDialog: SuccessDialog
    private lateinit var errorDialog: ErrorDialog

    private lateinit var edit: Array<EditText>

    private val mSmartOtpAuthenticationVM: SmartOtpAuthenticationVM by viewModel()

    protected var yourTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.toString().trim().length == 1) {
                if (getPreferences(requireContext()).isLongOtp) {
                    inputSmartOtp =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString() +
                                mBinding.editPin5.text.toString() + mBinding.editPin6.text.toString()
                } else {
                    inputSmartOtp =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                }
                if (inputSmartOtp == inputSmartOtp) {

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
        return R.layout.fragment_smart_otp_authentication
    }

    override fun onViewReady() {

        mBinding.smartOtpAuthenticationVm = mSmartOtpAuthenticationVM
        mBinding.lifecycleOwner = this

        successDialog = SuccessDialog(requireContext())
        errorDialog = ErrorDialog(requireContext())

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
        showKeyword(requireContext(), mBinding.editPin1)

        if (getPreferences(requireContext()).isLongOtp) {

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

        mBinding.buttonContinue.setOnClickListener {
            if (validateInputs()) {
                inputSmartOtp = if (getPreferences(requireContext()).isLongOtp) {
                    mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                            mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString() +
                            mBinding.editPin5.text.toString() + mBinding.editPin6.text.toString()
                } else {
                    mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                            mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                }
                if (inputSmartOtp == inputSmartOtp) {

                    successDialog.show()
                    successDialog.setCanceledOnTouchOutside(false)
                    successDialog.setCancelable(false)

                    successDialog.textSuccessHead?.text = getString(R.string.text_smart_otp_unregistered)
                    successDialog.textSuccessMessage?.text = getString(R.string.text_smart_otp_unregistered_message)
                    successDialog.buttonSuccessDialog.visibility = View.GONE
                    successDialog.successCrossButton.visibility = View.VISIBLE

                    getPreferences(requireContext()).setSmartOtp(false)

                    successDialog.successCrossButton?.setOnClickListener {
                        successDialog.dismiss()
                        findNavController().popBackStack(R.id.smartOtpPinFragment, false)
                    }

                } else {
                    showToast("Incorrect OTP")
                }
            } else {
                showToast("Enter OTP")
            }
        }

    }

    private fun validateInputs(): Boolean {
        return if (mBinding.editPin1.text.isNullOrEmpty()) {
            false
        } else if (mBinding.editPin2.text.isNullOrEmpty()) {
            false
        } else if (mBinding.editPin3.text.isNullOrEmpty()) {
            false
        } else if (mBinding.editPin4.text.isNullOrEmpty()) {
            false
        } else if (getPreferences(requireContext()).isLongOtp && mBinding.editPin5.text.isNullOrEmpty()) {
            false
        } else !(getPreferences(requireContext()).isLongOtp && mBinding.editPin6.text.isNullOrEmpty())
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

        if (getPreferences(requireContext()).isLongOtp) {
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

    private fun unregisterSmartOtpGenericTextWatcher() {
        mBinding.editPin1.removeTextChangedListener(
            SmartOtpGenericTextWatcher(edit, mBinding.editPin1)
        )
        mBinding.editPin2.removeTextChangedListener(
            SmartOtpGenericTextWatcher(edit, mBinding.editPin2)
        )
        mBinding.editPin3.removeTextChangedListener(
            SmartOtpGenericTextWatcher(edit, mBinding.editPin3)
        )
        mBinding.editPin4.removeTextChangedListener(
            SmartOtpGenericTextWatcher(edit, mBinding.editPin4)
        )
        mBinding.editPin5.removeTextChangedListener(
            SmartOtpGenericTextWatcher(edit, mBinding.editPin5)
        )
        mBinding.editPin6.removeTextChangedListener(
            SmartOtpGenericTextWatcher(edit, mBinding.editPin6)
        )
    }

    override fun onResume() {
        super.onResume()

        clearAllInputs()
        showKeyword(requireContext(), mBinding.editPin1)

        unregisterSmartOtpGenericTextWatcher()
        registerSmartOtpGenericTextWatcher()
    }

}