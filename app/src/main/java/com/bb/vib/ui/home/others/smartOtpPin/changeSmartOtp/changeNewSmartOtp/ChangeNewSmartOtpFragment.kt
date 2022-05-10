package com.bb.vib.ui.home.others.smartOtpPin.changeSmartOtp.changeNewSmartOtp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentChangeNewSmartOtpBinding
import com.bb.vib.databinding.FragmentNewSmartOtpBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import com.bb.vib.utils.SmartOtpGenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class ChangeNewSmartOtpFragment : BaseFragment<FragmentChangeNewSmartOtpBinding>() {

    private val mChangeNewSmartOtpVM: ChangeNewSmartOtpVM by viewModel()

    private var isLongOtp = false
    private var inputSmartOtp = ""

    private lateinit var errorDialog: ErrorDialog

    private lateinit var editShort: Array<EditText>
    private lateinit var editLong: Array<EditText>

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
                if (checkInputs()) {
                    val bundle = Bundle()
                    bundle.putString("smartOtp", inputSmartOtp)
                    bundle.putBoolean("isLongOtp", isLongOtp)
                    findNavController().navigate(
                        R.id.action_changeNewSmartOtpFragment_to_confirmChangeNewSmartOtpFragment,
                        bundle
                    )
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
        return R.layout.fragment_change_new_smart_otp
    }

    override fun onViewReady() {

        mBinding.changeNewSmartOtpVm = mChangeNewSmartOtpVM
        mBinding.lifecycleOwner = this

        errorDialog = ErrorDialog(requireContext())

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
        showKeyword(requireContext(), mBinding.editPin1)

        editShort = arrayOf<EditText>(
            mBinding.editPin1,
            mBinding.editPin2,
            mBinding.editPin3,
            mBinding.editPin4
        )

        editLong = arrayOf<EditText>(
            mBinding.editPin1,
            mBinding.editPin2,
            mBinding.editPin3,
            mBinding.editPin4,
            mBinding.editPin5,
            mBinding.editPin6
        )

        mBinding.buttonDigit.setOnClickListener {
            clearAllInputs()
            if (isLongOtp) {
                isLongOtp = false
                mBinding.buttonDigit.text = getString(R.string._6_digits_pin)
                mBinding.editPin5.visibility = View.GONE
                mBinding.editPin6.visibility = View.GONE
                mBinding.editPin6.removeTextChangedListener(yourTextWatcher)
                mBinding.editPin4.addTextChangedListener(yourTextWatcher)
                unregisterSmartOtpGenericTextWatcher()
                registerSmartOtpGenericTextWatcher()
                getPreferences(requireContext()).setLongOtp(false)
            } else {
                isLongOtp = true
                mBinding.buttonDigit.text = getString(R.string._4_digits_pin)
                mBinding.editPin5.visibility = View.VISIBLE
                mBinding.editPin6.visibility = View.VISIBLE
                mBinding.editPin4.removeTextChangedListener(yourTextWatcher)
                mBinding.editPin6.addTextChangedListener(yourTextWatcher)
                unregisterSmartOtpGenericTextWatcher()
                registerSmartOtpGenericTextWatcher()
                getPreferences(requireContext()).setLongOtp(true)
            }
        }

    }

    private fun checkInputs(): Boolean {

        val consecutiveForwardPattern = Pattern.compile("^([0-9])\\1*$")
        val continuousForwardPattern = Pattern.compile("^(\\d{0,9}|\\d{4,})$")

        return when {
            consecutiveForwardPattern.matcher(inputSmartOtp).find() -> {
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

        if (isLongOtp) {
            mBinding.editPin1.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editLong,
                    mBinding.editPin1
                )
            )
            mBinding.editPin2.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editLong,
                    mBinding.editPin2
                )
            )
            mBinding.editPin3.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editLong,
                    mBinding.editPin3
                )
            )
            mBinding.editPin4.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editLong,
                    mBinding.editPin4
                )
            )
            mBinding.editPin5.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editLong,
                    mBinding.editPin5
                )
            )
            mBinding.editPin6.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editLong,
                    mBinding.editPin6
                )
            )
        } else {
            mBinding.editPin1.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editShort,
                    mBinding.editPin1
                )
            )
            mBinding.editPin2.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editShort,
                    mBinding.editPin2
                )
            )
            mBinding.editPin3.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editShort,
                    mBinding.editPin3
                )
            )
            mBinding.editPin4.addTextChangedListener(
                SmartOtpGenericTextWatcher(
                    editShort,
                    mBinding.editPin4
                )
            )
        }

    }

    private fun unregisterSmartOtpGenericTextWatcher() {
        mBinding.editPin1.removeTextChangedListener(
            SmartOtpGenericTextWatcher(editLong, mBinding.editPin1)
        )
        mBinding.editPin2.removeTextChangedListener(
            SmartOtpGenericTextWatcher(editLong, mBinding.editPin2)
        )
        mBinding.editPin3.removeTextChangedListener(
            SmartOtpGenericTextWatcher(editLong, mBinding.editPin3)
        )
        mBinding.editPin4.removeTextChangedListener(
            SmartOtpGenericTextWatcher(editLong, mBinding.editPin4)
        )
        mBinding.editPin5.removeTextChangedListener(
            SmartOtpGenericTextWatcher(editLong, mBinding.editPin5)
        )
        mBinding.editPin6.removeTextChangedListener(
            SmartOtpGenericTextWatcher(editLong, mBinding.editPin6)
        )
    }

    override fun onResume() {
        super.onResume()

        clearAllInputs()
        showKeyword(requireContext(), mBinding.editPin1)

        unregisterSmartOtpGenericTextWatcher()
        registerSmartOtpGenericTextWatcher()
        mBinding.editPin4.addTextChangedListener(yourTextWatcher)
    }

}