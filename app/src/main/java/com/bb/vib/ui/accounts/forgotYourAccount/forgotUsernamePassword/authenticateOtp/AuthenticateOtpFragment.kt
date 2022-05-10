package com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.authenticateOtp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentAuthenticateOtpBinding
import com.bb.vib.extentions.hideKeyword
import com.bb.vib.extentions.showKeyword
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import com.bb.vib.utils.SmartOtpGenericTextWatcher
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthenticateOtpFragment : BaseFragment<FragmentAuthenticateOtpBinding>() {

    private val mAuthenticateOtpVM: AuthenticateOtpVM by viewModel()

    private var inputOtp = ""
    private lateinit var edit: Array<EditText>

    private var selectedOption = 0

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_authenticate_otp
    }

    override fun onViewReady() {

        mBinding.authenticateOtpVm = mAuthenticateOtpVM
        mBinding.lifecycleOwner = this

        if (arguments != null) {
            selectedOption = requireArguments().getInt("selectedOption")
        }

        alertDialog = AlertDialog(requireContext())

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
        showKeyword(requireContext(), mBinding.editPin1)

        edit = arrayOf<EditText>(
            mBinding.editPin1,
            mBinding.editPin2,
            mBinding.editPin3,
            mBinding.editPin4,
            mBinding.editPin5,
            mBinding.editPin6
        )
        registerSmartOtpGenericTextWatcher()

        mBinding.buttonContinue.setOnClickListener {
            if (validateInputs()) {
                inputOtp =
                    mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                            mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString() +
                            mBinding.editPin5.text.toString() + mBinding.editPin6.text.toString()
                if (inputOtp == inputOtp) {
                    hideKeyword(requireContext(), mBinding.editPin4)
                    val bundle = Bundle()
                    bundle.putInt("selectedOption", selectedOption)
                    findNavController().navigate(R.id.action_authenticateOtpFragment_to_usernameRecoveredFragment, bundle)
                } else {
                    alertDialog.show()
                    alertDialog.setCanceledOnTouchOutside(false)
                    alertDialog.setCancelable(false)

                    alertDialog.imageAlert?.setImageResource(R.drawable.ic_info_circle_icon)
                    alertDialog.textAlertHead?.text = getString(R.string.text_invalid_otp)
                    alertDialog.textAlertMessage?.text = getString(R.string.text_invalid_otp_message)
                    alertDialog.buttonAlertDialog?.visibility = View.GONE

                    alertDialog.alertCrossButton?.setOnClickListener {
                        alertDialog.dismiss()
                    }

                    clearAllInputs()
                }
            } else {
                alertDialog.show()
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.setCancelable(false)

                alertDialog.imageAlert?.setImageResource(R.drawable.ic_info_circle_icon)
                alertDialog.textAlertHead?.text = getString(R.string.text_invalid_otp)
                alertDialog.textAlertMessage?.text = getString(R.string.text_invalid_otp_message)
                alertDialog.buttonAlertDialog?.visibility = View.GONE

                alertDialog.alertCrossButton?.setOnClickListener {
                    alertDialog.dismiss()
                }

                clearAllInputs()
            }
        }

    }

    private fun validateInputs(): Boolean {
        return when {
            mBinding.editPin1.text.isNullOrEmpty() -> {
                false
            }
            mBinding.editPin2.text.isNullOrEmpty() -> {
                false
            }
            mBinding.editPin3.text.isNullOrEmpty() -> {
                false
            }
            mBinding.editPin4.text.isNullOrEmpty() -> {
                false
            }
            mBinding.editPin5.text.isNullOrEmpty() -> {
                false
            }
            mBinding.editPin6.text.isNullOrEmpty() -> {
                false
            }
            else -> true
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

    override fun onResume() {
        super.onResume()

        clearAllInputs()
        showKeyword(requireContext(), mBinding.editPin1)

        registerSmartOtpGenericTextWatcher()
    }

}