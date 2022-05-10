package com.bb.vib.ui.accounts

import android.os.Build
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bb.vib.R
import com.bb.vib.base.BaseActivity
import com.bb.vib.databinding.ActivityAccountsBinding
import com.bb.vib.ui.accounts.forgotYourAccount.dialog.AlertDialogPrompt
import com.bb.vib.ui.accounts.interfaces.NavigationListener
import kotlinx.android.synthetic.main.custom_alert_prompt_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountsActivity() : BaseActivity<ActivityAccountsBinding>(), NavigationListener {

    companion object {
        const val SCREEN_TERMS_CONDITIONS = 1
        const val SCREEN_LOGIN = 2
        const val SCREEN_CREATE_PIN = 3
        const val SCREEN_CONFIRM_PIN = 4
        const val SCREEN_USE_FACE_ID = 5
        const val SCREEN_INPUT_PIN = 6
        const val SCREEN_FORGOT_PIN_USERNAME = 7
        const val SCREEN_FORGOT_PIN_PASSWORD = 8
        const val SCREEN_CREATE_NEW_PIN = 9
        const val SCREEN_CONFIRM_NEW_PIN = 10
        const val SCREEN_CONFIRM_SMART_PIN = 11
        const val SCREEN_LOGIN_PIN = 12
        const val SCREEN_LOGIN_ACTIVE_BIOMETRIC = 13
        const val SCREEN_INPUT_LICENSE = 14
        const val SCREEN_SELECT_IDENTITY = 15
        const val SCREEN_INPUT_IDENTITY = 16
        const val SCREEN_INPUT_ACCOUNT = 17
        const val SCREEN_INPUT_BALANCE = 18
        const val SCREEN_VERIFICATION_CODE = 19
        const val SCREEN_SELECT_CREDENTIALS = 20
        const val SCREEN_CREATE_NEW_PASSWORD = 21
        const val SCREEN_CONFIRM_NEW_PASSWORD = 22
        const val SCREEN_AUTHENTICATE_OTP = 23
        const val SCREEN_USERNAME_RECOVERED = 24
    }

    private lateinit var navController: NavController

    private var navigatorValue = 0

    private val mAccountsVM: AccountsVM by viewModel()

    private lateinit var alertDialog: AlertDialogPrompt

    override fun mLayoutRes(): Int {
        return R.layout.activity_accounts
    }

    override fun onViewReady() {

        mBinding.accountsVm = mAccountsVM
        mBinding.lifecycleOwner = this

        alertDialog = AlertDialogPrompt(this@AccountsActivity)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

//        com.bb.vib.extentions.getPreferences(this@AccountsActivity).setRegister(true)
//        com.bb.vib.extentions.getPreferences(this@AccountsActivity).setUserId("3fa85f64-5717-4562-b3fc-2c963f66afa6")

        if (com.bb.vib.extentions.getPreferences(this@AccountsActivity).isPinCreated) {
            val graph = navController.navInflater.inflate(R.navigation.accounts_navigation)
            graph.setStartDestination(R.id.navigation_login_pin)
            navController.graph = graph
        }

        mBinding.backButton.setOnClickListener {
            onBackPressed()
        }

        mBinding.crossButton.setOnClickListener {
            if (navigatorValue == SCREEN_INPUT_LICENSE || navigatorValue == SCREEN_SELECT_IDENTITY ||
                navigatorValue == SCREEN_INPUT_IDENTITY || navigatorValue == SCREEN_INPUT_ACCOUNT ||
                navigatorValue == SCREEN_INPUT_BALANCE || navigatorValue == SCREEN_VERIFICATION_CODE ||
                navigatorValue == SCREEN_SELECT_CREDENTIALS || navigatorValue == SCREEN_CREATE_NEW_PASSWORD ||
                navigatorValue == SCREEN_CONFIRM_NEW_PASSWORD || navigatorValue == SCREEN_AUTHENTICATE_OTP) {

                alertDialog.show()
                alertDialog.setCanceledOnTouchOutside(false)
                alertDialog.setCancelable(false)

                alertDialog.imageAlertPrompt?.visibility = View.GONE
                alertDialog.textAlertPromptHead?.text = getString(R.string.text_account_recovery)
                alertDialog.textAlertPromptMessage?.text = getString(R.string.text_account_recovery)
                alertDialog.buttonAlertPromptCancel?.visibility = View.VISIBLE
                alertDialog.buttonAlertPromptAgree?.visibility = View.VISIBLE

                alertDialog.buttonAlertPromptCancel?.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.alertPromptCrossButton?.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.buttonAlertPromptAgree?.setOnClickListener {
                    alertDialog.dismiss()
                    navController.popBackStack(R.id.navigation_login, false)
                }

            } else {
                onBackPressed()
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.navigation_terms_conditions -> navigatorValue = SCREEN_TERMS_CONDITIONS
                R.id.navigation_login -> navigatorValue = SCREEN_LOGIN
                R.id.navigation_create_pin -> navigatorValue = SCREEN_CREATE_PIN
                R.id.navigation_confirm_pin -> navigatorValue = SCREEN_CONFIRM_PIN
                R.id.navigation_use_face_id -> navigatorValue = SCREEN_USE_FACE_ID
                R.id.navigation_input_pin -> navigatorValue = SCREEN_INPUT_PIN
                R.id.navigation_forgot_pin_username -> navigatorValue = SCREEN_FORGOT_PIN_USERNAME
                R.id.navigation_forgot_pin_password -> navigatorValue = SCREEN_FORGOT_PIN_PASSWORD
                R.id.navigation_create_new_pin -> navigatorValue = SCREEN_CREATE_NEW_PIN
                R.id.navigation_confirm_new_pin -> navigatorValue = SCREEN_CONFIRM_NEW_PIN
                R.id.navigation_confirm_smart_pin -> navigatorValue = SCREEN_CONFIRM_SMART_PIN
                R.id.navigation_login_pin -> navigatorValue = SCREEN_LOGIN_PIN
                R.id.loginActiveBiometricConfirmPinFragment -> navigatorValue = SCREEN_LOGIN_ACTIVE_BIOMETRIC
                R.id.inputBusinessLicenseFragment -> navigatorValue = SCREEN_INPUT_LICENSE
                R.id.selectIdentityPaperFragment -> navigatorValue = SCREEN_SELECT_IDENTITY
                R.id.inputIdentityPaperFragment -> navigatorValue = SCREEN_INPUT_IDENTITY
                R.id.inputAccountNumberFragment -> navigatorValue = SCREEN_INPUT_ACCOUNT
                R.id.inputAccountBalanceFragment -> navigatorValue = SCREEN_INPUT_BALANCE
                R.id.verificationCodeFragment -> navigatorValue = SCREEN_VERIFICATION_CODE
                R.id.selectCredentialsFragment -> navigatorValue = SCREEN_SELECT_CREDENTIALS
                R.id.createNewPasswordFragment -> navigatorValue = SCREEN_CREATE_NEW_PASSWORD
                R.id.confirmNewPasswordFragment -> navigatorValue = SCREEN_CONFIRM_NEW_PASSWORD
                R.id.authenticateOtpFragment -> navigatorValue = SCREEN_AUTHENTICATE_OTP
                R.id.usernameRecoveredFragment -> navigatorValue = SCREEN_USERNAME_RECOVERED
            }
            manageScreenComponents()
        }

    }

    private fun manageScreenComponents() {
        when(navigatorValue) {
            SCREEN_TERMS_CONDITIONS, SCREEN_LOGIN, SCREEN_LOGIN_PIN, SCREEN_USE_FACE_ID -> {
                mBinding.toolbarLayoutCommon.visibility = View.GONE
                mBinding.pinProgress.visibility = View.GONE
            }
            SCREEN_CREATE_PIN -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(70)
            }
            SCREEN_CONFIRM_PIN -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(100)
            }
            SCREEN_INPUT_PIN -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.pinProgress.visibility = View.GONE
                mBinding.backButton.visibility = View.VISIBLE
                mBinding.crossButton.visibility = View.VISIBLE
            }
            SCREEN_FORGOT_PIN_USERNAME -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(25)
            }
            SCREEN_FORGOT_PIN_PASSWORD -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(50)
            }
            SCREEN_CREATE_NEW_PIN -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(75)
            }
            SCREEN_CONFIRM_NEW_PIN -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(100)
            }
            SCREEN_CONFIRM_SMART_PIN -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_confirm)
                mBinding.pinProgress.visibility = View.GONE
                mBinding.backButton.visibility = View.GONE
                mBinding.crossButton.visibility = View.VISIBLE
            }
            SCREEN_LOGIN_ACTIVE_BIOMETRIC -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_confirm)
                mBinding.pinProgress.visibility = View.GONE
                mBinding.backButton.visibility = View.GONE
                mBinding.crossButton.visibility = View.VISIBLE
            }
            SCREEN_INPUT_LICENSE, SCREEN_SELECT_IDENTITY, SCREEN_INPUT_IDENTITY,
            SCREEN_INPUT_ACCOUNT, SCREEN_INPUT_BALANCE, SCREEN_VERIFICATION_CODE,
            SCREEN_CREATE_NEW_PASSWORD, SCREEN_CONFIRM_NEW_PASSWORD, SCREEN_AUTHENTICATE_OTP -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.backButton.visibility = View.VISIBLE
                mBinding.crossButton.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_account_recovery)
                mBinding.pinProgress.visibility = View.VISIBLE
                when (navigatorValue) {
                    SCREEN_INPUT_LICENSE -> updateProgress(16)
                    SCREEN_SELECT_IDENTITY -> updateProgress(16)
                    SCREEN_INPUT_IDENTITY -> updateProgress(33)
                    SCREEN_INPUT_ACCOUNT -> updateProgress(50)
                    SCREEN_INPUT_BALANCE -> updateProgress(67)
                    SCREEN_VERIFICATION_CODE -> updateProgress(83)
                    SCREEN_CREATE_NEW_PASSWORD -> updateProgress(33)
                    SCREEN_CONFIRM_NEW_PASSWORD -> updateProgress(66)
                    SCREEN_AUTHENTICATE_OTP -> updateProgress(100)
                }
            }
            SCREEN_SELECT_CREDENTIALS -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.backButton.visibility = View.VISIBLE
                mBinding.crossButton.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_account_recovery)
                mBinding.pinProgress.visibility = View.GONE
            }
            SCREEN_USERNAME_RECOVERED -> {
                mBinding.toolbarLayoutCommon.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.pinProgress.visibility = View.GONE
                mBinding.backButton.visibility = View.GONE
                mBinding.crossButton.visibility = View.VISIBLE
            }
        }
    }

    override fun setNavigation(value: Int) {
        navigatorValue = value
        manageScreenComponents()
    }

    override fun updateProgress(progress: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBinding.pinProgress.setProgress(progress, true)
        } else {
            mBinding.pinProgress.progress = progress
        }
    }


    override fun onBackPressed() {

        super.onBackPressed()

        when(navigatorValue) {
            SCREEN_CREATE_PIN, SCREEN_CONFIRM_PIN, SCREEN_USERNAME_RECOVERED -> {
                navController.popBackStack(R.id.navigation_login, false)
//                setNavigation(SCREEN_LOGIN)
            }
            SCREEN_FORGOT_PIN_USERNAME, SCREEN_FORGOT_PIN_PASSWORD, SCREEN_CONFIRM_SMART_PIN,
            SCREEN_CREATE_NEW_PIN, SCREEN_CONFIRM_NEW_PIN -> {
                if (com.bb.vib.extentions.getPreferences(this@AccountsActivity).isLoggedIn) {
                    navController.popBackStack(R.id.navigation_login_pin, false)
                } else {
                    navController.popBackStack(R.id.navigation_input_pin, false)
                }
            }
            SCREEN_SELECT_CREDENTIALS -> {
                navController.popBackStack(R.id.inputAccountBalanceFragment, false)
            }
            SCREEN_CONFIRM_NEW_PASSWORD, SCREEN_AUTHENTICATE_OTP -> {
                navController.popBackStack(R.id.selectCredentialsFragment, false)
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        com.bb.vib.extentions.getPreferences(this@AccountsActivity).setLogin(false)
    }

}