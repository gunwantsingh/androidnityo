package com.bb.vib.ui.home.others.passwordPin.changePin.confirmNewPin

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.api.model.request.UpdatePinRequestModel
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentConfirmNewPinBinding
import com.bb.vib.extentions.*
import com.bb.vib.ui.home.others.otherCreatePin.OtherCreatePinVM
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.GenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.buttonSuccessDialog
import kotlinx.android.synthetic.main.custom_success_dialog.successCrossButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class ConfirmNewPinFragment : BaseFragment<FragmentConfirmNewPinBinding>() {

    private val mConfirmNewPinVM: ConfirmNewPinVM by viewModel()
    private val mCreatePinVM: OtherCreatePinVM by viewModel()

    private lateinit var successDialog: SuccessDialog
    private lateinit var errorDialog: ErrorDialog

    private var inputPin = ""
    private var incomingPin = ""

    override fun mLayoutRes(): Int {
        return R.layout.fragment_confirm_new_pin
    }

    override fun onViewReady() {

        if (arguments != null) {
            incomingPin = requireArguments().getString("pin", "")
        }

        mBinding.confirmNewPinVm = mConfirmNewPinVM
        mBinding.lifecycleOwner = this

        successDialog = SuccessDialog(requireContext())
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
                    inputPin =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                    if (inputPin == incomingPin) {
                        callUpdatePinAPi()
                    } else {
                        errorDialog.show()
                        errorDialog.setCanceledOnTouchOutside(false)

                        errorDialog.textErrorHead?.text = getString(R.string.text_invalid_pin)
                        errorDialog.textErrorMessage?.text =
                            getString(R.string.text_same_pin_error_message)
                        errorDialog.buttonErrorDialog?.text = getString(R.string.text_continue)

                        errorDialog.buttonErrorDialog?.setOnClickListener {
                            errorDialog.dismiss()
                            findNavController().popBackStack(R.id.navigation_others_password_pin, false)
                        }

                        mBinding.editPin4.text.clear()
                        mBinding.editPin3.text.clear()
                        mBinding.editPin2.text.clear()
                        mBinding.editPin1.text.clear()

                        mBinding.editPin1.requestFocus()
                        mBinding.editPin1.performClick()
                    }
                } else {
                        errorDialog.show()
                        errorDialog.setCanceledOnTouchOutside(false)

                        errorDialog.textErrorHead?.text = getString(R.string.text_invalid_pin)
                        errorDialog.textErrorMessage?.text =
                            getString(R.string.text_same_pin_error_message)
                        errorDialog.buttonErrorDialog?.text = getString(R.string.text_continue)

                        errorDialog.buttonErrorDialog?.setOnClickListener {
                            errorDialog.dismiss()
                            findNavController().popBackStack(R.id.navigation_others_password_pin, false)
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

    private fun callUpdatePinAPi() {

        observeCalls()

        val updatePinRequest = UpdatePinRequestModel(
            getCurrentTimeStamp(),
            "",
            getCurrentTimeStamp(),
            getCurrentTimeStamp(),
            "",
            getCurrentTimeStamp(),
            "",
            "",
            "",
            0,
            "",
            true,
            getPreferences(requireContext()).getLanguage,
            getCurrentTimeStamp(),
            "",
            "",
            "",
            0,
            "",
            inputPin.toInt(),
            "",
            true,
            "",
            getCurrentTimeStamp(),
            "",
            getPreferences(requireContext()).getUserId,
            0,
            ""
        )

        mCreatePinVM.updatePin(updatePinRequest)

    }

    private fun observeCalls() {

        mCreatePinVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mCreatePinVM.updatePinResponse.observe(this, EventObserver {

            if (it.result!!) {

                successDialog.show()
                successDialog.setCanceledOnTouchOutside(false)
                successDialog.setCancelable(false)

                successDialog.textSuccessHead?.text = getString(R.string.change_pin_head)
                successDialog.textSuccessMessage?.text = getString(R.string.change_pin_dialog_message)
                successDialog.buttonSuccessDialog.visibility = View.GONE
                successDialog.successCrossButton.visibility = View.VISIBLE
                successDialog.successCrossButton.setOnClickListener {
                    successDialog.dismiss()
                    findNavController().popBackStack(R.id.navigation_others_password_pin, false)
                }

            } else {
                showToast("API Response Empty")
            }

        })

        mCreatePinVM.errorResponse.observe(this, Observer {
            showErrorDialog(
                requireContext(),
                "Unknown error",
                "Something went wrong when processing your request. Please try again or call 18008180 for assistance."
            )
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.editPin1, it)
            Log.d("Error", it.message!!)
        })

    }

}