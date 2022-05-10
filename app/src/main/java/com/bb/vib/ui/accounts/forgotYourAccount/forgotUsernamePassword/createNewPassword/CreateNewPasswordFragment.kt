package com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.createNewPassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentCreateNewPasswordBinding
import com.bb.vib.extentions.showToast
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class CreateNewPasswordFragment : BaseFragment<FragmentCreateNewPasswordBinding>() {

    private val mCreateNewPasswordVM: CreateNewPasswordVM by viewModel()

    private lateinit var alertDialog: AlertDialog

    private var selectedOption = 0

    private var checkCharacterRange = false
    private var checkLowerCase = false
    private var checkUpperCase = false
    private var checkDigit = false
    private var checkSpecialCharacter = false

    override fun mLayoutRes(): Int {
        return R.layout.fragment_create_new_password
    }

    override fun onViewReady() {

        mBinding.createNewPasswordVm = mCreateNewPasswordVM
        mBinding.lifecycleOwner = this

        alertDialog = AlertDialog(requireContext())

        if (arguments != null) {
            selectedOption = requireArguments().getInt("selectedOption")
        }

        mBinding.editNewPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                checkInputs()
            }

        })

        mBinding.buttonContinue.setOnClickListener {
            if (checkCharacterRange && checkLowerCase && checkUpperCase && checkDigit && checkSpecialCharacter) {
                val bundle = Bundle()
                bundle.putInt("selectedOption", selectedOption)
                bundle.putString("inputPassword", mBinding.editNewPassword.text.toString())
                findNavController().navigate(R.id.action_createNewPasswordFragment_to_confirmNewPasswordFragment, bundle)
            } else {
                showToast("Invalid Password")
            }
        }

    }

    private fun checkInputs() {

        val inputPassword: String = mBinding.editNewPassword.text.toString()

        if (inputPassword.length in 20 downTo 8) {
            checkCharacterRange = true
            mBinding.imageCharLength.isActivated = true
        } else {
            checkCharacterRange = false
            mBinding.imageCharLength.isActivated = false
        }

        if ((inputPassword.length - inputPassword.replace("[a-z]".toRegex(), "").length) < 1) {
            checkLowerCase = false
            mBinding.imageLowerCase.isActivated = false
        } else {
            checkLowerCase = true
            mBinding.imageLowerCase.isActivated = true
        }

        if ((inputPassword.length - inputPassword.replace("[A-Z]".toRegex(), "").length) < 1) {
            checkUpperCase = false
            mBinding.imageUpperCase.isActivated = false
        } else {
            checkUpperCase = true
            mBinding.imageUpperCase.isActivated = true
        }

        if ((inputPassword.length - inputPassword.replace("[0-9]".toRegex(), "").length) < 1) {
            checkDigit = false
            mBinding.imageDigit.isActivated = false
        } else {
            checkDigit = true
            mBinding.imageDigit.isActivated = true
        }

        if ((inputPassword.length - inputPassword.replace(
                "[!@#\$%&_*()+=|<>?{}\\[\\]~-]".toRegex(),
                ""
            ).length) < 1) {
            checkSpecialCharacter = false
            mBinding.imageSpecial.isActivated = false
        } else {
            checkSpecialCharacter = true
            mBinding.imageSpecial.isActivated = true
        }

    }

    override fun onResume() {
        super.onResume()

        mBinding.editNewPassword.text?.clear()
        mBinding.editNewPassword.requestFocus()
        mBinding.editNewPassword.performClick()
    }

}