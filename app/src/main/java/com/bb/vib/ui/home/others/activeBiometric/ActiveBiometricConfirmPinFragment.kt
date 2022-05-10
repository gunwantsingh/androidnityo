package com.bb.vib.ui.home.others.activeBiometric

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentActiveBiometricConfirmPinBinding
import com.bb.vib.extentions.*
import com.bb.vib.ui.accounts.loginPin.loginActiveBiometric.LoginActiveBiometricConfirmPinVM
import com.bb.vib.ui.accounts.useFaceId.UseFaceIdVM
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.GenericTextWatcher
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_success_dialog.*
import kotlinx.android.synthetic.main.fragment_confirm_smart_pin.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ActiveBiometricConfirmPinFragment : BaseFragment<FragmentActiveBiometricConfirmPinBinding>() {

    private val mActiveBiometricConfirmPinVM: ActiveBiometricConfirmPinVM by viewModel()
    private val mLoginActiveBiometricConfirmPinVM: LoginActiveBiometricConfirmPinVM by viewModel()
    private val mUseFaceIdVM: UseFaceIdVM by viewModel()

    private var inputPin = ""

    private lateinit var successDialog: SuccessDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_active_biometric_confirm_pin
    }

    override fun onViewReady() {

        mBinding.activeBiometricConfirmPinVm = mActiveBiometricConfirmPinVM
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
                inputPin =
                    mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                            mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                callVerifyPinApi()
            }

        }

    }

    private fun callVerifyPinApi() {

        observeCalls()

        mLoginActiveBiometricConfirmPinVM.verifyPin(
            getPreferences(requireContext()).getUserId,
            inputPin.toInt()
        )

    }

    private fun observeCalls() {

        mLoginActiveBiometricConfirmPinVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mLoginActiveBiometricConfirmPinVM.verifyPinResponse.observe(this, EventObserver {

            if (it.result != null) {
                mUseFaceIdVM.enableDisableBiometric("enable")
            } else {
                showToast("API Response Empty")
            }

        })

        mLoginActiveBiometricConfirmPinVM.errorResponse.observe(this, Observer {
            findNavController().popBackStack(R.id.navigation_others, false)
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.editPin1, it)
            Log.d("Error", it.message!!)
        })

        mUseFaceIdVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mUseFaceIdVM.enableDisableBiometricResponse.observe(this, EventObserver {

            if (!it.result.isNullOrEmpty()) {
                getPreferences(requireContext()).setBiometricToken(it.result)
                getPreferences(requireContext()).setBiometric(false)
                showSuccessDialog()
            } else {
                showToast("API Response Empty")
            }

        })

        mUseFaceIdVM.errorResponse.observe(this, Observer {
            findNavController().popBackStack(R.id.navigation_others, false)
            Log.d("Error", it.message!!)
        })

    }

    private fun showSuccessDialog() {

        successDialog.show()
        successDialog.setCanceledOnTouchOutside(false)
        successDialog.setCancelable(false)

        successDialog.textSuccessHead?.text = getString(R.string.text_fingerprint_successfully_activated)
        successDialog.textSuccessMessage?.text = getString(R.string.text_fingerprint_successfully_activated_message)
        successDialog.buttonSuccessDialog.visibility = View.GONE
        successDialog.successCrossButton.visibility = View.VISIBLE

        getPreferences(requireContext()).setBiometric(true)

        successDialog.successCrossButton?.setOnClickListener {
            successDialog.dismiss()
            findNavController().popBackStack(R.id.navigation_others, false)
//                    activity?.onBackPressed()
        }

    }

}