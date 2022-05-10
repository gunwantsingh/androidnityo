package com.bb.vib.ui.accounts.forgotYourAccount.selectCredentials

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentSelectCredentialsBinding
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectCredentialsFragment : BaseFragment<FragmentSelectCredentialsBinding>() {

    private val mSelectCredentialsVM: SelectCredentialsVM by viewModel()

    private var selectedOption = 1

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_select_credentials
    }

    override fun onViewReady() {

        mBinding.selectCredentialsVm = mSelectCredentialsVM
        mBinding.lifecycleOwner = this

        alertDialog = AlertDialog(requireContext())

        mBinding.bothLayout.setOnClickListener {
            selectedOption = 1
            mBinding.passwordRb.visibility = View.GONE
            mBinding.usernameRb.visibility = View.GONE
            mBinding.bothRb.visibility = View.VISIBLE
        }

        mBinding.passwordLayout.setOnClickListener {
            selectedOption = 2
            mBinding.usernameRb.visibility = View.GONE
            mBinding.bothRb.visibility = View.GONE
            mBinding.passwordRb.visibility = View.VISIBLE
        }

        mBinding.usernameLayout.setOnClickListener {
            selectedOption = 3
            mBinding.bothRb.visibility = View.GONE
            mBinding.passwordRb.visibility = View.GONE
            mBinding.usernameRb.visibility = View.VISIBLE
        }

        mBinding.buttonContinue.setOnClickListener {
            if (selectedOption == 3) {
                val bundle = Bundle()
                bundle.putInt("selectedOption", selectedOption)
                findNavController().navigate(
                    R.id.action_selectCredentialsFragment_to_authenticateOtpFragment,
                    bundle
                )
            } else {
                val bundle = Bundle()
                bundle.putInt("selectedOption", selectedOption)
                findNavController().navigate(
                    R.id.action_selectCredentialsFragment_to_createNewPasswordFragment,
                    bundle
                )
            }
        }

    }

}