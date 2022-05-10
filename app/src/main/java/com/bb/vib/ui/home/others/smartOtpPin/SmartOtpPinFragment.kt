package com.bb.vib.ui.home.others.smartOtpPin

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentSmartOtpPinBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.interfaces.IOnResultListener
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.utils.SoftTokenSDKHelper
import kotlinx.android.synthetic.main.custom_success_dialog.*
import kotlinx.android.synthetic.main.custom_success_dialog.buttonSuccessDialog
import kotlinx.android.synthetic.main.custom_success_dialog.successCrossButton
import kotlinx.android.synthetic.main.custom_success_dialog.textSuccessHead
import kotlinx.android.synthetic.main.custom_success_dialog.textSuccessMessage
import kotlinx.android.synthetic.main.smart_otp_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SmartOtpPinFragment : BaseFragment<FragmentSmartOtpPinBinding>() {

    private val mSmartOtpPinVM: SmartOtpPinVM by viewModel()

    private lateinit var smartOtpDialog: SmartOtpDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_smart_otp_pin
    }

    override fun onViewReady() {

        mBinding.smartOtpPinVm = mSmartOtpPinVM
        mBinding.lifecycleOwner = this

        smartOtpDialog = SmartOtpDialog(requireContext())

        toggleSmartOtp()

        mBinding.smartOtpLayout.setOnClickListener {
            if (getPreferences(requireContext()).isSmartOtpEnabled) {
                smartOtpDialog.show()
                smartOtpDialog.setCanceledOnTouchOutside(false)
                smartOtpDialog.setCancelable(false)

                smartOtpDialog.textSuccessHead?.text = getString(R.string.text_unregister_smart_otp)
                smartOtpDialog.textSuccessMessage?.text = getString(R.string.text_unregister_smart_otp_message)
                smartOtpDialog.buttonSuccessDialog.text = getString(R.string.agree)
                smartOtpDialog.buttonSuccessDialog.visibility = View.VISIBLE

                smartOtpDialog.successCrossButton.setOnClickListener {
                    smartOtpDialog.dismiss()
                }

                smartOtpDialog.buttonSuccessDialog?.setOnClickListener {
                    smartOtpDialog.dismiss()
                    findNavController().navigate(R.id.action_smartOtpPinFragment_to_smartOtpAuthenticationFragment)
                }
            } else {
                smartOtpDialog.show()
                smartOtpDialog.setCanceledOnTouchOutside(false)
                smartOtpDialog.setCancelable(false)

                smartOtpDialog.textSuccessHead?.text = getString(R.string.text_activate_smart_otp)
                smartOtpDialog.textSuccessMessage?.text = getString(R.string.text_activate_smart_otp_message)
                smartOtpDialog.buttonSuccessDialog.text = getString(R.string.agree)

                smartOtpDialog.successCrossButton.setOnClickListener {
                    smartOtpDialog.dismiss()
                }

                smartOtpDialog.buttonSuccessDialog?.setOnClickListener {
                    smartOtpDialog.dismiss()
                    findNavController().navigate(R.id.action_smartOtpPinFragment_to_createSmartOtpFragment)
                }
            }

        }

        mBinding.forgotSmartOtpLayout.setOnClickListener {
            findNavController().navigate(R.id.action_smartOtpPinFragment_to_newSmartOtpFragment)
        }

        mBinding.changeSmartOtpLayout.setOnClickListener {
            findNavController().navigate(R.id.action_smartOtpPinFragment_to_currentSmartOtpFragment)
        }

        mBinding.syncSmartOtpLayout.setOnClickListener {
            smartOtpDialog.show()
            smartOtpDialog.setCanceledOnTouchOutside(false)
            smartOtpDialog.setCancelable(false)

            smartOtpDialog.textSuccessHead?.text = getString(R.string.text_sync_smart_otp)
            smartOtpDialog.textSuccessMessage?.text = getString(R.string.text_sync_smart_otp_message)
            smartOtpDialog.buttonSuccessDialog.visibility = View.GONE
            smartOtpDialog.successCrossButton.visibility = View.VISIBLE

            smartOtpDialog.successCrossButton.setOnClickListener {
                smartOtpDialog.dismiss()
            }

            smartOtpDialog.successCrossButton?.setOnClickListener {
                smartOtpDialog.dismiss()
            }
        }

    }

    private fun toggleSmartOtp() {
        if (getPreferences(requireContext()).isSmartOtpEnabled) {
            mBinding.toggleSmartOtp.isActivated = true
            mBinding.textSmartOtpMessage.visibility = View.GONE
            mBinding.divider1.visibility = View.VISIBLE
            mBinding.divider2.visibility = View.VISIBLE
            mBinding.divider3.visibility = View.VISIBLE
            mBinding.forgotSmartOtpLayout.visibility = View.VISIBLE
            mBinding.changeSmartOtpLayout.visibility = View.VISIBLE
            mBinding.syncSmartOtpLayout.visibility = View.VISIBLE
        } else {
            mBinding.toggleSmartOtp.isActivated = false
            mBinding.textSmartOtpMessage.visibility = View.VISIBLE
            mBinding.divider1.visibility = View.GONE
            mBinding.divider2.visibility = View.GONE
            mBinding.divider3.visibility = View.GONE
            mBinding.forgotSmartOtpLayout.visibility = View.GONE
            mBinding.changeSmartOtpLayout.visibility = View.GONE
            mBinding.syncSmartOtpLayout.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        mBinding.toggleSmartOtp.isActivated = getPreferences(requireContext()).isSmartOtpEnabled
        toggleSmartOtp()
    }

}