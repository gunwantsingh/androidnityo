package com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.usernameRecovered

import android.view.View
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentUsernameRecoveredBinding
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import kotlinx.coroutines.selects.select
import org.koin.androidx.viewmodel.ext.android.viewModel


class UsernameRecoveredFragment : BaseFragment<FragmentUsernameRecoveredBinding>() {

    private val mUsernameRecoveredVM: UsernameRecoveredVM by viewModel()

    private lateinit var alertDialog: AlertDialog

    private var selectedOption = 0

    override fun mLayoutRes(): Int {
        return R.layout.fragment_username_recovered
    }

    override fun onViewReady() {

        mBinding.usernameRecoveredVm = mUsernameRecoveredVM
        mBinding.lifecycleOwner = this

        if (arguments != null) {
            selectedOption = requireArguments().getInt("selectedOption")
        }

        if (selectedOption == 2) {
            mBinding.textUsernameRecovered.text = getString(R.string.text_password_reset_successful)
            mBinding.textUsernameRecoveredMessage.visibility = View.GONE
        }

        mBinding.buttonLoginNow.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_login, false)
        }

        alertDialog = AlertDialog(requireContext())

    }

}