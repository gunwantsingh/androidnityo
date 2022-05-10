package com.bb.vib.ui.accounts.forgotYourAccount.inputBusinessLicense

import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentInputBusinessLicenseBinding
import com.bb.vib.extentions.showToast
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class InputBusinessLicenseFragment : BaseFragment<FragmentInputBusinessLicenseBinding>() {

    private val mInputBusinessLicenseVM: InputBusinessLicenseVM by viewModel()

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_input_business_license
    }

    override fun onViewReady() {

        mBinding.inputBusinessLicenseVm = mInputBusinessLicenseVM
        mBinding.lifecycleOwner = this

        alertDialog = AlertDialog(requireContext())

        mBinding.buttonContinue.setOnClickListener {
            if (checkInputs()) {
                findNavController().navigate(R.id.action_inputBusinessLicenseFragment_to_selectIdentityPaperFragment)
            } else {
                alertDialog.show()
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.setCancelable(false)

                alertDialog.textAlertHead?.text = getString(R.string.text_invalid_business_license_number)
                alertDialog.textAlertMessage?.text = getString(R.string.text_invalid_business_license_number_message)
                alertDialog.buttonAlertDialog?.text = getString(R.string.text_call_support)

                alertDialog.buttonAlertDialog?.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.alertCrossButton?.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

    }

    private fun checkInputs(): Boolean {

        val specialPattern = Pattern.compile("[!@#$%&_*()+=|<>?{}\\[\\]~-]")

        val inputLicense: String = mBinding.editLicense.text.toString()

        return when {
            inputLicense.length != 10 -> {
                showToast("License Length")
                false
            }
            specialPattern.matcher(inputLicense).find() -> {
                showToast("License Special Character")
                false
            }
            inputLicense.contains(" ", true) -> {
                showToast("License Space")
                false
            }
            else -> true
        }

    }

    override fun onResume() {
        super.onResume()

        mBinding.editLicense.text?.clear()
        mBinding.editLicense.requestFocus()
        mBinding.editLicense.performClick()
    }

}