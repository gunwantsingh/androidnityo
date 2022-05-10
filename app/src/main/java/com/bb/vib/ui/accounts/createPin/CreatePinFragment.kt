package com.bb.vib.ui.accounts.createPin

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.BuildConfig
import com.bb.vib.R
import com.bb.vib.api.model.request.RegisterPinRequestModel
import com.bb.vib.api.model.request.UpdatePinRequestModel
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentCreatePinBinding
import com.bb.vib.extentions.*
import com.bb.vib.ui.home.HomeActivity
import com.bb.vib.ui.home.others.otherCreatePin.OtherCreatePinVM
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.GenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.buttonSuccessDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class CreatePinFragment : BaseFragment<FragmentCreatePinBinding>() {

    private val mCreatePinVM: CreatePinVM by viewModel()
    private val mOtherCreatePinVM: OtherCreatePinVM by viewModel()

    private lateinit var successDialog: SuccessDialog
    private lateinit var errorDialog: ErrorDialog

    private var inputCreatePin = ""
    private var inputConfirmPin = ""
    private var incomingPin = ""

    private var canUseBiometric = false

    private var type = 1

    override fun mLayoutRes(): Int {
        return R.layout.fragment_create_pin
    }

    override fun onViewReady() {

        if (arguments != null) {
            type = requireArguments().getInt("type")
            incomingPin = requireArguments().getString("pin", "")
        }

        mBinding.createPinVm = mCreatePinVM
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

        mBinding.textForgotPin.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", 1)
            findNavController().navigate(
                R.id.action_navigation_input_pin_to_navigation_forgot_pin_username,
                bundle
            )
        }

    }

    private fun checkInputs(): Boolean {

        val consecutiveForwardPattern = Pattern.compile("^([0-9])\\1*$")
        val continuousForwardPattern = Pattern.compile("^(\\d{0,9}|\\d{4,})$")

        return when {
            consecutiveForwardPattern.matcher(inputCreatePin).find() -> {
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

    private fun showSuccessNotification(flag: Int) {

        successDialog.show()
        successDialog.setCanceledOnTouchOutside(false)
        successDialog.setCancelable(false)

        if (flag == 1) {
            successDialog.textSuccessHead?.text =
                getString(R.string.create_pin_head)
            successDialog.textSuccessMessage?.text =
                getString(R.string.create_pin_dialog_message)
            successDialog.buttonSuccessDialog.text = getString(R.string.text_continue)

            successDialog.buttonSuccessDialog?.setOnClickListener {
                successDialog.dismiss()
                if (canUseBiometric) {
                    findNavController().navigate(R.id.action_navigation_confirm_pin_to_navigation_use_face_id)
                } else {
                    getPreferences(requireContext()).setBiometric(false)
                    startHomeActivity()
                }

            }
        } else {
            successDialog.textSuccessHead?.text = getString(R.string.text_fingerprint_activation)
            successDialog.textSuccessMessage?.text =
                getString(R.string.text_fingerprint_successfully_activated_message)
            successDialog.buttonSuccessDialog.text = getString(R.string.text_continue)

            successDialog.buttonSuccessDialog?.setOnClickListener {
                successDialog.dismiss()
                startHomeActivity()
            }
        }

    }

    override fun onResume() {

        if (type == 2) {
            mBinding.textForgotPin.visibility = View.GONE
            mBinding.textCreatePin.text = getString(R.string.confirm_your_pin)
            mBinding.textCreatePinMessage.text = getString(R.string.confirm_pin_message)
        } else if (type == 3) {
            mBinding.textForgotPin.visibility = View.VISIBLE
            mBinding.textCreatePin.text = getString(R.string.input_your_pin)
            mBinding.textCreatePinMessage.text = getString(R.string.input_pin_message)
        } else if (type == 4) {
            mBinding.textForgotPin.visibility = View.GONE
            mBinding.textCreatePin.text = getString(R.string.create_new_pin)
            mBinding.textCreatePinMessage.text = getString(R.string.enter_new_pin_message)
        } else if (type == 5) {
            mBinding.textForgotPin.visibility = View.GONE
            mBinding.textCreatePin.text = getString(R.string.confirm_new_pin)
            mBinding.textCreatePinMessage.text = getString(R.string.confirm_new_pin_message)
        }

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
                    if (type == 1) {
                        inputCreatePin =
                            mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                    mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                        if (checkInputs()) {
                            val bundle = Bundle()
                            bundle.putInt("type", 2)
                            bundle.putString("pin", inputCreatePin)
                            findNavController().navigate(
                                R.id.action_navigation_create_pin_to_navigation_confirm_pin,
                                bundle
                            )
                        }
                    } else if (type == 2) {
                        hideKeyword(requireContext(), mBinding.editPin4)

                        inputConfirmPin =
                            mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                    mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                        if (incomingPin == inputConfirmPin) {
                            callRegisterPinApi()
                        } else {
                            showErrorNotification(3)
                        }
                    } else if (type == 3) {
                        hideKeyword(requireContext(), mBinding.editPin4)
                        showSuccessNotification(2)
                    } else if (type == 4) {
                        inputCreatePin =
                            mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                    mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                        if (checkInputs()) {
                            val bundle = Bundle()
                            bundle.putInt("type", 5)
                            bundle.putString("pin", inputCreatePin)
                            findNavController().navigate(
                                R.id.action_navigation_create_new_pin_to_navigation_confirm_new_pin,
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

                            mBinding.editPin4.text.clear()
                            mBinding.editPin3.text.clear()
                            mBinding.editPin2.text.clear()
                            mBinding.editPin1.text.clear()

                            mBinding.editPin1.requestFocus()
                            mBinding.editPin1.performClick()
                        }
                    } else if (type == 5) {
                        hideKeyword(requireContext(), mBinding.editPin4)

                        inputConfirmPin =
                            mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                    mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                        if (incomingPin == inputConfirmPin) {
                            hideKeyword(requireContext(), mBinding.editPin4)
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
                            }

                            mBinding.editPin4.text.clear()
                            mBinding.editPin3.text.clear()
                            mBinding.editPin2.text.clear()
                            mBinding.editPin1.text.clear()

                            mBinding.editPin1.requestFocus()
                            mBinding.editPin1.performClick()
                        }

                    }
                }
            }

        })

        super.onResume()

    }

    private fun callRegisterPinApi() {

        observeCalls()

        val registerPinRequest = RegisterPinRequestModel(
            RegisterPinRequestModel.ObjUserDevice(
                getCurrentTimeStamp(),
                "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                getCurrentTimeStamp(),
                "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                getPreferences(requireContext()).getUserId
            ),
            RegisterPinRequestModel.ObjUserLoginHistoryDevice(
                BuildConfig.VERSION_CODE.toString(),
                getCurrentTimeStamp(),
                "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "",
                50,
                70,
                getPreferences(requireContext()).getUserId,
                getPreferences(requireContext()).getUserId
            ),
            inputConfirmPin.toInt(),
            getPreferences(requireContext()).getUserId
        )

        mCreatePinVM.registerPin(registerPinRequest)

        canUseBiometric = checkBiometricSupport()
        getPreferences(requireContext()).setBiometricSupport(canUseBiometric)
//        showSuccessNotification(1)

    }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager : KeyguardManager = requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(!keyguardManager.isKeyguardSecure) {
            return false
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else {
            true
        }
    }

    private fun startHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun observeCalls() {

        mCreatePinVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mCreatePinVM.registerPinResponse.observe(this, EventObserver {

            if (it.result!!) {
                getPreferences(requireContext()).setPinCreation(true)
                getPreferences(requireContext()).setLogin(true)
                canUseBiometric = checkBiometricSupport()
                getPreferences(requireContext()).setBiometricSupport(canUseBiometric)
                showSuccessNotification(1)
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

        mOtherCreatePinVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mOtherCreatePinVM.updatePinResponse.observe(this, EventObserver {

            if (it.result!!) {

                successDialog.show()
                successDialog.setCanceledOnTouchOutside(false)
                successDialog.setCancelable(false)

                successDialog.textSuccessHead?.text = getString(R.string.pin_successfully_created_forgot)
                successDialog.textSuccessMessage?.text = getString(R.string.pin_successfully_created_forgot_message)
                successDialog.buttonSuccessDialog.visibility = View.GONE
                successDialog.successCrossButton.visibility = View.VISIBLE

                successDialog.successCrossButton?.setOnClickListener {
                    successDialog.dismiss()
                    findNavController().popBackStack(R.id.navigation_login_pin, false)
                }

            } else {
                showToast("API Response Empty")
            }

        })

        mOtherCreatePinVM.errorResponse.observe(this, Observer {
            showErrorDialog(
                requireContext(),
                "Unknown error",
                "Something went wrong when processing your request. Please try again or call 18008180 for assistance."
            )
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.editPin1, it)
            Log.d("Error", it.message!!)
        })

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
            inputConfirmPin.toInt(),
            "",
            true,
            "",
            getCurrentTimeStamp(),
            "",
            getPreferences(requireContext()).getUserId,
            0,
            ""
        )

        mOtherCreatePinVM.updatePin(updatePinRequest)

    }

}