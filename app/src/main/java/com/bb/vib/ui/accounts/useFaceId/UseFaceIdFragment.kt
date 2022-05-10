package com.bb.vib.ui.accounts.useFaceId

import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.FragmentUseFaceIdBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.extentions.showErrorDialog
import com.bb.vib.extentions.showToast
import com.bb.vib.extentions.toggleLoader
import com.bb.vib.ui.home.HomeActivity
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_success_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class UseFaceIdFragment : BaseFragment<FragmentUseFaceIdBinding>() {

    private lateinit var successDialog: SuccessDialog

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
                    callEnableBiometricAPI()
                }
            }

    private val mUseFaceIdVM: UseFaceIdVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_use_face_id
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewReady() {

        mBinding.useFaceIdVm = mUseFaceIdVM
        mBinding.lifecycleOwner = this

        successDialog = SuccessDialog(requireContext())

        mBinding.buttonAgree.setOnClickListener {

            val biometricPrompt : BiometricPrompt = BiometricPrompt.Builder(requireContext())
                .setTitle("VIB Authentication")
                .setSubtitle("Fingerprint Authentication")
                .setDescription("Authentication is required")
                .setNegativeButton("Cancel", requireActivity().mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                }).build()
            biometricPrompt.authenticate(getCancellationSignal(), requireActivity().mainExecutor, authenticationCallback)


//            val bundle = Bundle()
//            bundle.putInt("type", 3)
//            (requireContext() as AccountsActivity).setNavigation(AccountsActivity.SCREEN_INPUT_PIN)
//            findNavController().navigate(
//                R.id.action_navigation_use_face_id_to_navigation_input_pin,
//                bundle
//            )
        }

        mBinding.buttonLater.setOnClickListener {
            startHomeActivity()
        }

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

    private fun startHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
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
                findNavController().navigate(R.id.action_navigation_confirm_pin_to_navigation_use_face_id)
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

    private fun callEnableBiometricAPI() {

        observeCalls()
        mUseFaceIdVM.enableDisableBiometric("enable")

    }

    private fun observeCalls() {

        mUseFaceIdVM.progressIndicator.observe(this, Observer {
            toggleLoader(requireContext(), it)
        })

        mUseFaceIdVM.enableDisableBiometricResponse.observe(this, EventObserver {

            if (!it.result.isNullOrEmpty()) {
                getPreferences(requireContext()).setBiometricToken(it.result)
                getPreferences(requireContext()).setBiometric(true)
                showSuccessNotification(2)
            } else {
                showToast("API Response Empty")
            }

        })

        mUseFaceIdVM.errorResponse.observe(this, Observer {
            showErrorDialog(
                requireContext(),
                "Unknown error",
                "Something went wrong when processing your request. Please try again or call 18008180 for assistance."
            )
//            ErrorUtil.handlerGeneralError(requireContext(), mBinding.buttonAgree, it)
            Log.d("Error", it.message!!)
        })

    }

}