package com.bb.vib.ui.home.others.otherConfirmSmartPin

import android.view.View
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentOtherConfirmSmartPinBinding
import com.bb.vib.extentions.hideKeyword
import com.bb.vib.extentions.showKeyword
import com.bb.vib.extentions.showToast
import com.bb.vib.utils.GenericTextWatcher
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_success_dialog.*
import kotlinx.android.synthetic.main.fragment_confirm_smart_pin.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OtherConfirmSmartPinFragment : BaseFragment<FragmentOtherConfirmSmartPinBinding>() {

    private val mConfirmSmartPinVM: OtherConfirmSmartPinVM by viewModel()

    private lateinit var successDialog: SuccessDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_other_confirm_smart_pin
    }

    override fun onViewReady() {

        mBinding.confirmSmartPinVm = mConfirmSmartPinVM
        mBinding.lifecycleOwner = this

        successDialog = SuccessDialog(requireContext())

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

        mBinding.buttonContinue.setOnClickListener {

            if (editPin1.text.isNullOrEmpty() || editPin2.text.isNullOrEmpty() ||
                editPin3.text.isNullOrEmpty() || editPin4.text.isNullOrEmpty()) {
                showToast("Enter OTP")
            } else {
                hideKeyword(requireContext(), mBinding.editPin4)
                successDialog.show()
                successDialog.setCanceledOnTouchOutside(false)

                successDialog.textSuccessHead?.text = getString(R.string.pin_successfully_created_forgot)
                successDialog.textSuccessMessage?.text = getString(R.string.pin_successfully_created_forgot_message)
                successDialog.buttonSuccessDialog.visibility = View.GONE
                successDialog.successCrossButton.visibility = View.VISIBLE

                successDialog.successCrossButton?.setOnClickListener {
                    successDialog.dismiss()
                    findNavController().popBackStack(R.id.navigation_others, false)
//                    activity?.onBackPressed()
                }
            }

        }

    }

}