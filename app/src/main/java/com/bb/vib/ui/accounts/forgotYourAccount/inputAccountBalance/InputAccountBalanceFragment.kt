package com.bb.vib.ui.accounts.forgotYourAccount.inputAccountBalance

import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentInputAccountBalanceBinding
import com.bb.vib.databinding.FragmentInputAccountNumberBinding
import com.bb.vib.databinding.FragmentInputBusinessLicenseBinding
import com.bb.vib.extentions.showToast
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class InputAccountBalanceFragment : BaseFragment<FragmentInputAccountBalanceBinding>() {

    private val mInputAccountBalanceVM: InputAccountBalanceVM by viewModel()

    private lateinit var alertDialog: AlertDialog

    override fun mLayoutRes(): Int {
        return R.layout.fragment_input_account_balance
    }

    override fun onViewReady() {

        mBinding.inputAccountBalanceVm = mInputAccountBalanceVM
        mBinding.lifecycleOwner = this

        alertDialog = AlertDialog(requireContext())

        mBinding.buttonContinue.setOnClickListener {
            findNavController().navigate(R.id.action_inputAccountBalanceFragment_to_verificationCodeFragment)
        }

    }

    override fun onResume() {
        super.onResume()

        mBinding.editBalance.text?.clear()
        mBinding.editBalance.requestFocus()
        mBinding.editBalance.performClick()
    }

}