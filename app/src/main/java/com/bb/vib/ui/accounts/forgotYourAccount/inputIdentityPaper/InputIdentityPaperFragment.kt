package com.bb.vib.ui.accounts.forgotYourAccount.inputIdentityPaper

import android.view.View
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentInputBusinessLicenseBinding
import com.bb.vib.databinding.FragmentInputIdentityPaperBinding
import com.bb.vib.extentions.showToast
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class InputIdentityPaperFragment : BaseFragment<FragmentInputIdentityPaperBinding>() {

    private val mInputIdentityPaperVM: InputIdentityPaperVM by viewModel()

    private var mode = 1

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_input_identity_paper
    }

    override fun onViewReady() {

        mBinding.inputIdentityPaperVm = mInputIdentityPaperVM
        mBinding.lifecycleOwner = this

        if (arguments != null) {
            mode = requireArguments().getInt("selectedMode")
        }

        alertDialog = AlertDialog(requireContext())

        when (mode) {
            1 -> {
                mBinding.textInputIdentity.text = getString(R.string.input_identity_card)
                mBinding.textFieldCitizenCard.visibility = View.GONE
                mBinding.textFieldPassport.visibility = View.GONE
                mBinding.textFieldIdentityCard.visibility = View.VISIBLE
            }
            2 -> {
                mBinding.textInputIdentity.text = getString(R.string.input_citizen_identity_card)
                mBinding.textFieldPassport.visibility = View.GONE
                mBinding.textFieldIdentityCard.visibility = View.GONE
                mBinding.textFieldCitizenCard.visibility = View.VISIBLE
            }
            else -> {
                mBinding.textInputIdentity.text = getString(R.string.input_passport)
                mBinding.textFieldIdentityCard.visibility = View.GONE
                mBinding.textFieldCitizenCard.visibility = View.GONE
                mBinding.textFieldPassport.visibility = View.VISIBLE
            }
        }

        mBinding.buttonContinue.setOnClickListener {
            if (checkInputs()) {
                findNavController().navigate(R.id.action_inputIdentityPaperFragment_to_inputAccountNumberFragment)
            } else {
                alertDialog.show()
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.setCancelable(false)

                alertDialog.textAlertHead?.text = getString(R.string.text_invalid_identity_card)
                alertDialog.textAlertMessage?.text = getString(R.string.text_invalid_identity_card_message)
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

        var inputIdentity = ""

        inputIdentity = when (mode) {
            1 -> mBinding.editIdentityCard.text.toString()
            2 -> mBinding.editCitizenIdentityCard.text.toString()
            else -> mBinding.editPassport.text.toString()
        }

        when (mode) {
            1 -> {
                return when {
                    inputIdentity.length != 9 || inputIdentity.length != 12 -> {
                        showToast("Identity Length")
                        false
                    }
                    specialPattern.matcher(inputIdentity).find() -> {
                        showToast("Identity Special Character")
                        false
                    }
                    inputIdentity.contains(" ", true) -> {
                        showToast("Identity Space")
                        false
                    }
                    else -> true
                }
            }
            2 -> {
                return when {
                    inputIdentity.length != 12 -> {
                        showToast("Identity Length")
                        false
                    }
                    specialPattern.matcher(inputIdentity).find() -> {
                        showToast("Identity Special Character")
                        false
                    }
                    inputIdentity.contains(" ", true) -> {
                        showToast("Identity Space")
                        false
                    }
                    else -> true
                }
            }
            else -> {
                return when {
                    inputIdentity.length !in 12 downTo 8 -> {
                        showToast("Identity Length")
                        false
                    }
                    specialPattern.matcher(inputIdentity).find() -> {
                        showToast("Identity Special Character")
                        false
                    }
                    inputIdentity.contains(" ", true) -> {
                        showToast("Identity Space")
                        false
                    }
                    else -> true
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

        mBinding.editIdentityCard.text?.clear()
        mBinding.editCitizenIdentityCard.text?.clear()
        mBinding.editPassport.text?.clear()

        when (mode) {
            1 -> {
                mBinding.editIdentityCard.requestFocus()
                mBinding.editIdentityCard.performClick()
            }
            2 -> {
                mBinding.editCitizenIdentityCard.requestFocus()
                mBinding.editCitizenIdentityCard.performClick()
            }
            else -> {
                mBinding.editPassport.requestFocus()
                mBinding.editPassport.performClick()
            }
        }

    }

}