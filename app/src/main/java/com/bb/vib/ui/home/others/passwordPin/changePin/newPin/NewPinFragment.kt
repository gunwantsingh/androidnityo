package com.bb.vib.ui.home.others.passwordPin.changePin.newPin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentNewPinBinding
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import com.bb.vib.utils.GenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.buttonSuccessDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class NewPinFragment : BaseFragment<FragmentNewPinBinding>() {

    private val mNewPinVM: NewPinVM by viewModel()

    private lateinit var errorDialog: ErrorDialog

    private var inputNewPin = ""

    override fun mLayoutRes(): Int {
        return R.layout.fragment_new_pin
    }

    override fun onViewReady() {

        mBinding.newPinVm = mNewPinVM
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
            consecutiveForwardPattern.matcher(inputNewPin).find() -> {
                showErrorNotification(1)
                false
            }
            isContinuousPin() -> {
                showErrorNotification(2)
                false
            }
            else -> true
        }
    }

    private fun isContinuousPin(): Boolean {
        return mBinding.editPin2.text.toString().toInt() - 1 == mBinding.editPin1.text.toString().toInt() &&
                mBinding.editPin3.text.toString().toInt() - 1 == mBinding.editPin2.text.toString().toInt() &&
                mBinding.editPin4.text.toString().toInt() - 1 == mBinding.editPin3.text.toString().toInt()
    }

    private fun showErrorNotification(flag: Int) {

        errorDialog.show()
        errorDialog.setCanceledOnTouchOutside(false)
        errorDialog.setCancelable(false)

        if (flag == 1) {
            errorDialog.textErrorHead?.text = getString(R.string.text_invalid_pin)
            errorDialog.textErrorMessage?.text =
                getString(R.string.pin_same_digits_message)
        } else if (flag == 2) {
            errorDialog.textErrorHead?.text = getString(R.string.text_invalid_pin)
            errorDialog.textErrorMessage?.text =
                getString(R.string.pin_continuous_digits_message)
        } else {
            errorDialog.textErrorHead?.text = getString(R.string.text_invalid_pin)
            errorDialog.textErrorMessage?.text =
                getString(R.string.text_same_pin_error_message)
        }

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
                    inputNewPin =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                    if (checkInputs()) {
                        val bundle = Bundle()
                        bundle.putString("pin", inputNewPin)
                        findNavController().navigate(R.id.action_newPinFragment_to_confirmNewPinFragment, bundle)
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

}