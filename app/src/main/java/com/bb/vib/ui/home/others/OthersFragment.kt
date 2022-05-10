package com.bb.vib.ui.home.others

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentOthersBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.extentions.showToast
import com.bb.vib.utils.dialogs.ErrorDialog
import kotlinx.android.synthetic.main.biometric_dialog.*
import kotlinx.android.synthetic.main.custom_error_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OthersFragment : BaseFragment<FragmentOthersBinding>() {

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
                    findNavController().navigate(R.id.action_navigation_others_to_activeBiometricConfirmPinFragment)
                }
            }

    private val mOthersVM: OthersVM by viewModel()

    private lateinit var biometricDialog: BiometricDialog
    private lateinit var errorDialog: ErrorDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_others
    }

    override fun onViewReady() {

        mBinding.othersVm = mOthersVM
        mBinding.lifecycleOwner = this

        biometricDialog = BiometricDialog(requireContext())
        errorDialog = ErrorDialog(requireContext())

        mBinding.toggleBiometric.isActivated = getPreferences(requireContext()).isBiometricEnabled

        mBinding.userLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_others_to_accountInfoFragment)
        }

        mBinding.biometricLayout.setOnClickListener {
            if (getPreferences(requireContext()).isBiometricEnabled) {

                biometricDialog.show()
                biometricDialog.setCanceledOnTouchOutside(false)
                biometricDialog.setCancelable(false)

                biometricDialog.textFingerprintHead?.text = getString(R.string.text_fingerprint_deactivation)
                biometricDialog.textFingerprintMessage?.text = getString(R.string.text_fingerprint_deactivation_message)
                biometricDialog.buttonActivateDialog.text = getString(R.string.agree)

                biometricDialog.closeButton.setOnClickListener {
                    biometricDialog.dismiss()
                }

                biometricDialog.buttonActivateDialog?.setOnClickListener {
                    biometricDialog.dismiss()

                    findNavController().navigate(R.id.action_navigation_others_to_deactivateBiometricConfirmPinFragment)
                }

            } else {

                if (getPreferences(requireContext()).canUseBiometric) {
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

                } else {

                    errorDialog.show()
                    errorDialog.setCanceledOnTouchOutside(false)
                    errorDialog.setCancelable(true)

                    errorDialog.textErrorHead?.text = getString(R.string.text_fingerprint_not_activated)
                    errorDialog.textErrorMessage?.text = getString(R.string.text_fingerprint_not_activated_message)
                    errorDialog.buttonErrorDialog?.text = getString(R.string.text_settings)

                    errorDialog.buttonErrorDialog?.setOnClickListener {
                        errorDialog.dismiss()
                    }

                }

            }
        }

        mBinding.passwordLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_others_to_navigation_others_password_pin)
        }

        mBinding.otpLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_others_to_smartOtpPinFragment)
        }

        mBinding.languageLayout.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_others_to_languageSettingsFragment)
        }

    }

    override fun onResume() {
        super.onResume()

        mBinding.toggleBiometric.isActivated = getPreferences(requireContext()).isBiometricEnabled
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