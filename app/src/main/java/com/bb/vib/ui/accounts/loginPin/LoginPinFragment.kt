package com.bb.vib.ui.accounts.loginPin

import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.BuildConfig
import com.bb.vib.R
import com.bb.vib.api.model.request.RegisterPinRequestModel
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentLoginPinBinding
import com.bb.vib.extentions.*
import com.bb.vib.ui.accounts.useFaceId.UseFaceIdVM
import com.bb.vib.ui.home.HomeActivity
import com.bb.vib.ui.home.others.BiometricDialog
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.GenericTextWatcher
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.biometric_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginPinFragment : BaseFragment<FragmentLoginPinBinding>() {

    private var isRegisteringBiometric = false
    private var inputPin = ""

    private var cancellationSignal: CancellationSignal? = null
    private val  authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object: BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication Error: $errString")
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)

                    Log.d("Biometric", result.toString())
                    if (isRegisteringBiometric) {
                        findNavController().navigate(R.id.action_navigation_login_pin_to_loginActiveBiometricConfirmPinFragment)
                    } else {
                        callLoginWithBiometricApi()
                    }

                }
            }

    private lateinit var biometricDialog: BiometricDialog
    private lateinit var errorDialog: ErrorDialog

    private val mLoginPinVM: LoginPinVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_login_pin
    }

    override fun onViewReady() {

        mBinding.loginPinVm = mLoginPinVM
        mBinding.lifecycleOwner = this

        biometricDialog = BiometricDialog(requireContext())
        errorDialog = ErrorDialog(requireContext())

        if (getPreferences(requireContext()).canUseBiometric) {
            if (getPreferences(requireContext()).isBiometricEnabled) {
                mBinding.loginFaceId.visibility = View.GONE
                mBinding.buttonLater.visibility = View.GONE
                mBinding.loginFingerprint.visibility = View.VISIBLE
            } else {
                mBinding.loginFingerprint.visibility = View.GONE
                mBinding.loginFaceId.visibility = View.GONE
                mBinding.buttonLater.visibility = View.VISIBLE
            }
        } else {
            mBinding.loginFaceId.visibility = View.GONE
            mBinding.buttonLater.visibility = View.GONE
            mBinding.loginFingerprint.visibility = View.GONE
        }

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
                R.id.action_navigation_login_pin_to_navigation_forgot_pin_username,
                bundle
            )
        }

        mBinding.buttonLater.setOnClickListener {

            isRegisteringBiometric = true

            biometricDialog.show()
            biometricDialog.setCanceledOnTouchOutside(false)
            biometricDialog.setCancelable(false)

            biometricDialog.textFingerprintHead?.text =
                getString(R.string.text_fingerprint_activation)
            biometricDialog.textFingerprintMessage?.text =
                getString(R.string.text_fingerprint_activation_message)
            biometricDialog.buttonActivateDialog.text = getString(R.string.text_activate)

            biometricDialog.closeButton.setOnClickListener {
                biometricDialog.dismiss()
            }

            biometricDialog.buttonActivateDialog?.setOnClickListener {
                biometricDialog.dismiss()

                val biometricPrompt : BiometricPrompt = BiometricPrompt.Builder(requireContext())
                    .setTitle("VIB Authentication")
                    .setSubtitle("Fingerprint Authentication")
                    .setDescription("Authentication is required")
                    .setNegativeButton("Cancel", requireActivity().mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                    }).build()
                biometricPrompt.authenticate(getCancellationSignal(), requireActivity().mainExecutor, authenticationCallback)

            }

        }

        mBinding.loginFingerprint.setOnClickListener {

            isRegisteringBiometric = false
            hideKeyword(requireContext(), mBinding.editPin4)

            val biometricPrompt : BiometricPrompt = BiometricPrompt.Builder(requireContext())
                .setTitle("VIB Authentication")
                .setSubtitle("Fingerprint Authentication")
                .setDescription("Authentication is required")
                .setNegativeButton("Cancel", requireActivity().mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                }).build()
            biometricPrompt.authenticate(getCancellationSignal(), requireActivity().mainExecutor, authenticationCallback)

        }

    }

    private fun clearAllInputs() {
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
                    hideKeyword(requireContext(), mBinding.editPin4)
                    inputPin =
                        mBinding.editPin1.text.toString() + mBinding.editPin2.text.toString() +
                                mBinding.editPin3.text.toString() + mBinding.editPin4.text.toString()
                    callLoginWithPinApi()
                }
            }

        })

        super.onResume()

    }

    private fun callLoginWithPinApi() {

        observeCalls()

        mLoginPinVM.loginWithPin(
            getPreferences(requireContext()).getUserId,
            inputPin.toInt()
        )

    }

    private fun callLoginWithBiometricApi() {

        observeCalls()

        mLoginPinVM.loginWithBiometric(
            getPreferences(requireContext()).getUserId,
            getPreferences(requireContext()).getBiometricToken
        )

    }

    private fun observeCalls() {

        mLoginPinVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mLoginPinVM.loginPinResponse.observe(this, EventObserver {

            if (it.result != null) {
                getPreferences(requireContext()).setLogin(true)
                startHomeActivity()
            } else {
                clearAllInputs()
                showToast("API Response Empty")
            }

        })

        mLoginPinVM.errorResponse.observe(this, Observer {
            clearAllInputs()
            toggleLoader(requireContext(), false)
            showErrorDialog(
                requireContext(),
                "Invalid PIN",
                "PIN is not correct. Your username will be locked after 05 consecutive wrong PIN inputs. Please try again."
            )
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.editPin1, it)
            Log.d("Error", it.message!!)
        })


    }

    private fun startHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun notifyUser(message: String) {
        showToast(message)
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

}