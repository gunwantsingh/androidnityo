package com.bb.vib.ui.home.others.smartOtpPin.activeSmartOtp.otpAuthentication

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentOtpAuthenticationBinding
import com.bb.vib.extentions.*
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.interfaces.IOnLoginListener
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.interfaces.IOnResultListener
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.utils.SoftTokenSDKHelper
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.SmartOtpGenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_error_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OtpAuthenticationFragment : BaseFragment<FragmentOtpAuthenticationBinding>() {

    private var inputOtp = ""
    private var smartOtp = ""

    private lateinit var errorDialog: ErrorDialog
    private lateinit var successDialog: SuccessDialog

    private lateinit var edit: Array<EditText>

    private var countDownTimer: CountDownTimer? = null

    private val mOtpAuthenticationVM: OtpAuthenticationVM by viewModel()

    protected var yourTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.toString().trim().length == 1) {
                inputOtp =
                    mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                            mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString() +
                            mBinding.editPin5.text.toString() + mBinding.editPin6.text.toString()
                hideKeyword(requireContext(), mBinding.editPin4)
                callVerifyActivationCodeApi()
                if (inputOtp == inputOtp) {

                    successDialog.show()
                    successDialog.setCanceledOnTouchOutside(false)
                    successDialog.setCancelable(false)

                    successDialog.textSuccessHead?.text = getString(R.string.text_smart_otp_activated)
                    successDialog.textSuccessMessage?.text = getString(R.string.text_smart_otp_activated_message)
                    successDialog.buttonSuccessDialog.visibility = View.GONE
                    successDialog.successCrossButton.visibility = View.VISIBLE
                    getPreferences(requireContext()).setSmartOtp(true)

                    successDialog.successCrossButton?.setOnClickListener {
                        successDialog.dismiss()
                        findNavController().popBackStack(R.id.smartOtpPinFragment, false)
//                    activity?.onBackPressed()
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
        return R.layout.fragment_otp_authentication
    }

    override fun onViewReady() {

        mBinding.otpAuthenticationVm = mOtpAuthenticationVM
        mBinding.lifecycleOwner = this

        successDialog = SuccessDialog(requireContext())
        errorDialog = ErrorDialog(requireContext())

        if (arguments != null) {
            smartOtp = requireArguments().getString("smartOtp", "")
        }

        mBinding.editPin1.requestFocus()
        mBinding.editPin1.performClick()
        showKeyword(requireContext(), mBinding.editPin1)

        countDown()

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
                hideKeyword(requireContext(), mBinding.editPin4)
                callVerifyActivationCodeApi()
            } else {
                showToast("Enter OTP")
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

    private fun countDown() {
        countDownTimer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mBinding.textResendOtp.setTextColor(resources.getColor(R.color.app_grey_400))
                mBinding.textResendOtp.text = "Resend OTP after ${millisUntilFinished / 1000}s"
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                mBinding.textResendOtp.setTextColor(resources.getColor(R.color.app_blue))
                mBinding.textResendOtp.text = "Resend OTP"
                mBinding.textResendOtp.setOnClickListener {
                    countDown()
                }

            }
        }.start()

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

    override fun onDestroy() {
        countDownTimer?.cancel()
        countDownTimer?.onFinish()
        super.onDestroy()
    }

    private fun callVerifyActivationCodeApi() {

        observeCalls()
        mOtpAuthenticationVM.verifyActivationCode(
            getPreferences(requireContext()).getUserId,
            inputOtp
        )

    }

    private fun observeCalls() {

        mOtpAuthenticationVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mOtpAuthenticationVM.verifyActivationCodeResponse.observe(this, EventObserver {

            if (it.result != null) {
                setupSoftToken()
            } else {
                showToast("API Response Empty")
            }

        })

        mOtpAuthenticationVM.activateSmartOtpResponse.observe(this, EventObserver {

            if (it.result != null) {

                successDialog.show()
                successDialog.setCanceledOnTouchOutside(false)
                successDialog.setCancelable(false)

                successDialog.textSuccessHead?.text = getString(R.string.text_smart_otp_activated)
                successDialog.textSuccessMessage?.text = getString(R.string.text_smart_otp_activated_message)
                successDialog.buttonSuccessDialog.visibility = View.GONE
                successDialog.successCrossButton.visibility = View.VISIBLE
                getPreferences(requireContext()).setSmartOtp(true)

                successDialog.successCrossButton?.setOnClickListener {
                    successDialog.dismiss()
                    findNavController().popBackStack(R.id.smartOtpPinFragment, false)
//                    activity?.onBackPressed()
                }

            } else {
                showToast("API Response Empty")
            }

        })

        mOtpAuthenticationVM.errorResponse.observe(this, Observer {
            showErrorDialog(
                requireContext(),
                "Occurring error",
                "Error occurs, please try again after a few minutes"
            )
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.editPin1, it)
            Log.d("Error", it.message!!)
        })

    }

    private fun setupSoftToken() {

        // Set the time step for new otp is 30 second
//        SoftTokenSDKHelper.getInstance(activity).timeStep = 30

//        val isActive = SoftTokenSDKHelper.getInstance(activity).checkSdkActive()
//        Toast.makeText(requireContext(), "SDKActive: $isActive", Toast.LENGTH_LONG).show()

//        SoftTokenSDKHelper.getInstance(activity).resetLoginFailTimes()

//        val token: String = SoftTokenSDKHelper.getInstance(activity).tokenSerial
//        Toast.makeText(requireContext(), "Token - $token", Toast.LENGTH_SHORT).show()

        // Active smart otp
        SoftTokenSDKHelper.getInstance(activity).doActive(inputOtp, object : IOnResultListener {
            override fun onSuccess(result: String?) {
                Toast.makeText(requireContext(), "DoActiveSuccessResult:$result", Toast.LENGTH_LONG).show()
                setSmartOtpPin()
            }

            override fun onFail(result: String?) {
                Toast.makeText(requireContext(), "Wrong Activation Code: $result", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun setSmartOtpPin() {

        SoftTokenSDKHelper.getInstance(activity).setPin(smartOtp)

        SoftTokenSDKHelper.getInstance(activity).loginPIN(smartOtp, object : IOnLoginListener {
            override fun onWrongPIN(wrongTimes: Int) {
                Toast.makeText(requireContext(), "Login Wrong Pin", Toast.LENGTH_SHORT).show()
            }

            override fun onWrongPINManyTimes() {
                Toast.makeText(requireContext(), "Login Wrong Pin Many Times", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onLocked() {
                Toast.makeText(requireContext(), "Login Locked", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show();
            }
        })

        mOtpAuthenticationVM.activateSmartOtp(getPreferences(requireContext()).getUserId)

    }

}