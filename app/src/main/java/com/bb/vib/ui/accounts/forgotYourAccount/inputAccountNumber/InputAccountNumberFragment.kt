package com.bb.vib.ui.accounts.forgotYourAccount.inputAccountNumber

import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentInputAccountNumberBinding
import com.bb.vib.databinding.FragmentInputBusinessLicenseBinding
import com.bb.vib.extentions.showToast
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class InputAccountNumberFragment : BaseFragment<FragmentInputAccountNumberBinding>() {

    private val mInputAccountNumberVM: InputAccountNumberVM by viewModel()

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_input_account_number
    }

    override fun onViewReady() {

        mBinding.inputAccountNumberVm = mInputAccountNumberVM
        mBinding.lifecycleOwner = this

        alertDialog = AlertDialog(requireContext())

        mBinding.buttonContinue.setOnClickListener {
            if (checkInputs()) {
                findNavController().navigate(R.id.action_inputAccountNumberFragment_to_inputAccountBalanceFragment)
            } else {
                alertDialog.show()
                alertDialog.setCanceledOnTouchOutside(false)

                alertDialog.textAlertHead?.text = getString(R.string.error_invalid_login)
                alertDialog.textAlertMessage?.text = getString(R.string.error_invalid_login_message)
                alertDialog.buttonAlertDialog?.text = getString(R.string.text_call_support)

                alertDialog.buttonAlertDialog?.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

    }

    private fun checkInputs(): Boolean {

        val specialPattern = Pattern.compile("[!@#$%&_*()+=|<>?{}\\[\\]~-]")

        val inputAccount: String = mBinding.editAccount.text.toString()

        return when {
            inputAccount.length != 10 -> {
                showToast("License Length")
                false
            }
            specialPattern.matcher(inputAccount).find() -> {
                showToast("License Special Character")
                false
            }
            inputAccount.contains(" ", true) -> {
                showToast("License Space")
                false
            }
            else -> true
        }

    }

    override fun onResume() {
        super.onResume()

        mBinding.editAccount.text?.clear()
        mBinding.editAccount.requestFocus()
        mBinding.editAccount.performClick()
    }

}