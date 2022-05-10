package com.bb.vib.ui.accounts.forgotYourAccount.selectIdentityPaper

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentSelectIdentityPaperBinding
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SelectIdentityPaperFragment : BaseFragment<FragmentSelectIdentityPaperBinding>() {

    private val mSelectIdentityPaperVM: SelectIdentityPaperVM by viewModel()

    private var selectedMode = 1

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_select_identity_paper
    }

    override fun onViewReady() {

        mBinding.selectIdentityPaperVm = mSelectIdentityPaperVM
        mBinding.lifecycleOwner = this

        alertDialog = AlertDialog(requireContext())

        mBinding.identityCardLayout.setOnClickListener {
            selectedMode = 1
            mBinding.citizenCardRb.visibility = View.GONE
            mBinding.passportRb.visibility = View.GONE
            mBinding.identityCardRb.visibility = View.VISIBLE
        }

        mBinding.citizenCardLayout.setOnClickListener {
            selectedMode = 2
            mBinding.passportRb.visibility = View.GONE
            mBinding.identityCardRb.visibility = View.GONE
            mBinding.citizenCardRb.visibility = View.VISIBLE
        }

        mBinding.passportLayout.setOnClickListener {
            selectedMode = 3
            mBinding.identityCardRb.visibility = View.GONE
            mBinding.citizenCardRb.visibility = View.GONE
            mBinding.passportRb.visibility = View.VISIBLE
        }

        mBinding.buttonContinue.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("selectedMode", selectedMode)
            findNavController().navigate(R.id.action_selectIdentityPaperFragment_to_inputIdentityPaperFragment, bundle)
        }

    }

}