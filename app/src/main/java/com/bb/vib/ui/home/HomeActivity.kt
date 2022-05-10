package com.bb.vib.ui.home

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bb.vib.R
import com.bb.vib.base.BaseActivity
import com.bb.vib.base.EventObserver
import com.bb.vib.databinding.ActivityHomeBinding
import com.bb.vib.extentions.showToast
import com.bb.vib.extentions.toggleLoader
import com.bb.vib.ui.accounts.AccountsActivity
import com.bb.vib.ui.accounts.interfaces.NavigationListener
import com.bb.vib.utils.ErrorUtil
import com.bb.vib.utils.dialogs.SuccessDialog
import kotlinx.android.synthetic.main.custom_success_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity() : BaseActivity<ActivityHomeBinding>(), NavigationListener {

    companion object {
        const val NAVIGATION_ACCOUNT = 1
        const val NAVIGATION_TRANSFER = 2
        const val NAVIGATION_QR = 3
        const val NAVIGATION_NOTIFICATION = 4
        const val NAVIGATION_OTHERS = 5

        const val NAVIGATION_ACCOUNT_INFO = 32

        const val NAVIGATION_LANGUAGE_SETTINGS = 33

        const val NAVIGATION_OTHER_PASSWORD_PIN = 6
        const val NAVIGATION_OTHER_BIOMETRIC = 7
        const val NAVIGATION_OTHER_INPUT_PIN = 8

        const val NAVIGATION_OTHER_FORGOT_PIN_USERNAME = 9
        const val NAVIGATION_OTHER_FORGOT_PIN_PASSWORD = 10
        const val NAVIGATION_OTHER_CREATE_NEW_PIN = 11
        const val NAVIGATION_OTHER_CONFIRM_NEW_PIN = 12
        const val NAVIGATION_OTHER_CONFIRM_SMART_PIN = 13

        const val NAVIGATION_CURRENT_PIN = 14
        const val NAVIGATION_NEW_PIN = 15
        const val NAVIGATION_CONFIRM_NEW_PIN = 16

        const val NAVIGATION_OTHER_SMART_OTP = 17
        const val NAVIGATION_CREATE_SMART_OTP = 18
        const val NAVIGATION_CONFIRM_SMART_OTP = 19
        const val NAVIGATION_OTP_AUTHENTICATION = 20

        const val NAVIGATION_SMART_OTP_AUTHENTICATION = 21

        const val NAVIGATION_USERNAME_SMART_OTP = 22
        const val NAVIGATION_PASSWORD_SMART_OTP = 23
        const val NAVIGATION_FORGOT_NEW_SMART_OTP = 24
        const val NAVIGATION_FORGOT_CONFIRM_SMART_OTP = 25
        const val NAVIGATION_OTP_AUTHORIZATION = 31

        const val NAVIGATION_CURRENT_SMART_OTP = 26
        const val NAVIGATION_CHANGE_NEW_SMART_OTP = 27
        const val NAVIGATION_CONFIRM_NEW_SMART_OTP = 28

        const val NAVIGATION_ACTIVATE_BIOMETRIC = 29
        const val NAVIGATION_DEACTIVATE_BIOMETRIC = 30
    }

    private var navigatorValue = 0

    private lateinit var navController: NavController
    private lateinit var successDialog: SuccessDialog

    private val mHomeVM: HomeVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun onViewReady() {

        mBinding.homeVm = mHomeVM
        mBinding.lifecycleOwner = this

        successDialog = SuccessDialog(this@HomeActivity)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(
            mBinding.bottomNavigation,
            navController
        )

        mBinding.backIcon.setOnClickListener {
            onBackPressed()
        }

        mBinding.signOutIcon.setOnClickListener {

            successDialog.show()
            successDialog.setCanceledOnTouchOutside(false)

            successDialog.imageSuccess?.setImageResource(R.drawable.ic_sign_out_big_icon)
            successDialog.textSuccessHead?.text = getString(R.string.text_log_out)
            successDialog.textSuccessMessage?.text = getString(R.string.text_log_out_message)
            successDialog.buttonSuccessDialog.visibility = View.VISIBLE
            successDialog.buttonSuccessDialog.text = getString(R.string.text_log_out)
            successDialog.successCrossButton.visibility = View.VISIBLE

            successDialog.successCrossButton?.setOnClickListener {
                successDialog.dismiss()
            }

            successDialog.buttonSuccessDialog.setOnClickListener {
                successDialog.dismiss()
                callLogoutApi()
            }

        }

        mBinding.backButton.setOnClickListener {
            onBackPressed()
        }

        mBinding.crossButton.setOnClickListener {
            onBackPressed()
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.navigation_account -> navigatorValue = NAVIGATION_ACCOUNT
                R.id.navigation_others -> navigatorValue = NAVIGATION_OTHERS
                R.id.navigation_others_password_pin -> navigatorValue = NAVIGATION_OTHER_PASSWORD_PIN
                R.id.navigation_other_forgot_pin_username -> navigatorValue = NAVIGATION_OTHER_FORGOT_PIN_USERNAME
                R.id.navigation_other_forgot_pin_password -> navigatorValue = NAVIGATION_OTHER_FORGOT_PIN_PASSWORD
                R.id.navigation_other_create_new_pin -> navigatorValue = NAVIGATION_OTHER_CREATE_NEW_PIN
                R.id.navigation_other_confirm_new_pin -> navigatorValue = NAVIGATION_OTHER_CONFIRM_NEW_PIN
                R.id.navigation_other_confirm_smart_pin -> navigatorValue = NAVIGATION_OTHER_CONFIRM_SMART_PIN
                R.id.navigation_other_use_fingerprint -> navigatorValue = NAVIGATION_OTHER_BIOMETRIC
                R.id.navigation_other_input_pin -> navigatorValue = NAVIGATION_OTHER_INPUT_PIN
                R.id.currentPinFragment -> navigatorValue = NAVIGATION_CURRENT_PIN
                R.id.newPinFragment -> navigatorValue = NAVIGATION_NEW_PIN
                R.id.confirmNewPinFragment -> navigatorValue = NAVIGATION_CONFIRM_NEW_PIN
                R.id.smartOtpPinFragment -> navigatorValue = NAVIGATION_OTHER_SMART_OTP
                R.id.createSmartOtpFragment -> navigatorValue = NAVIGATION_CREATE_SMART_OTP
                R.id.confirmSmartOtpFragment -> navigatorValue = NAVIGATION_CONFIRM_SMART_OTP
                R.id.otpAuthenticationFragment -> navigatorValue = NAVIGATION_OTP_AUTHENTICATION
                R.id.smartOtpAuthenticationFragment -> navigatorValue = NAVIGATION_SMART_OTP_AUTHENTICATION
                R.id.usernameForgotSmartOtpFragment -> navigatorValue = NAVIGATION_USERNAME_SMART_OTP
                R.id.passwordForgotSmartOtpFragment -> navigatorValue = NAVIGATION_PASSWORD_SMART_OTP
                R.id.newSmartOtpFragment -> navigatorValue = NAVIGATION_FORGOT_NEW_SMART_OTP
                R.id.confirmNewSmartOtpFragment -> navigatorValue = NAVIGATION_FORGOT_CONFIRM_SMART_OTP
                R.id.otpAuthorizationFragment -> navigatorValue = NAVIGATION_OTP_AUTHORIZATION
                R.id.currentSmartOtpFragment -> navigatorValue = NAVIGATION_CURRENT_SMART_OTP
                R.id.changeNewSmartOtpFragment -> navigatorValue = NAVIGATION_CHANGE_NEW_SMART_OTP
                R.id.confirmChangeNewSmartOtpFragment -> navigatorValue = NAVIGATION_CONFIRM_NEW_SMART_OTP
                R.id.activeBiometricConfirmPinFragment -> navigatorValue = NAVIGATION_ACTIVATE_BIOMETRIC
                R.id.deactivateBiometricConfirmPinFragment -> navigatorValue = NAVIGATION_DEACTIVATE_BIOMETRIC
                R.id.accountInfoFragment -> navigatorValue = NAVIGATION_ACCOUNT_INFO
                R.id.languageSettingsFragment -> navigatorValue = NAVIGATION_LANGUAGE_SETTINGS
            }
            manageScreenComponents()
        }

    }

    private fun manageScreenComponents() {
        when(navigatorValue) {
            NAVIGATION_ACCOUNT -> {
                mBinding.toolbarLayoutBig.visibility = View.GONE
                mBinding.bottomNavigation.visibility = View.VISIBLE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.signOutIcon.visibility = View.GONE
                mBinding.homeIcon.visibility = View.VISIBLE
                mBinding.toolbarLayoutHome.visibility = View.VISIBLE
            }
            NAVIGATION_OTHERS -> {
                mBinding.toolbarLayoutBig.visibility = View.GONE
                mBinding.bottomNavigation.visibility = View.VISIBLE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.homeIcon.visibility = View.GONE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_others)
                mBinding.signOutIcon.visibility = View.VISIBLE
                mBinding.toolbarLayoutHome.visibility = View.VISIBLE
            }
            NAVIGATION_OTHER_PASSWORD_PIN -> {
                mBinding.toolbarLayoutBig.visibility = View.GONE
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.toolbarTitleOther.visibility = View.VISIBLE
                mBinding.toolbarTitleOther.text = getString(R.string.title_password_pin)
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.VISIBLE
            }
            NAVIGATION_OTHER_BIOMETRIC -> {
                mBinding.toolbarLayoutBig.visibility = View.GONE
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
            }
            NAVIGATION_OTHER_INPUT_PIN -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.GONE
                mBinding.pinProgress.visibility = View.GONE
            }
            NAVIGATION_OTHER_FORGOT_PIN_USERNAME -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(25)
            }
            NAVIGATION_OTHER_FORGOT_PIN_PASSWORD -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(50)
            }
            NAVIGATION_OTHER_CREATE_NEW_PIN -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(50)
            }
            NAVIGATION_OTHER_CONFIRM_NEW_PIN -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.VISIBLE
                mBinding.toolbarTitle.text = getString(R.string.text_forgot_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(100)
            }
            NAVIGATION_OTHER_CONFIRM_SMART_PIN, NAVIGATION_ACTIVATE_BIOMETRIC, NAVIGATION_DEACTIVATE_BIOMETRIC -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_confirm)
                mBinding.pinProgress.visibility = View.GONE
                mBinding.backButton.visibility = View.GONE
                mBinding.crossButton.visibility = View.VISIBLE
            }
            NAVIGATION_CURRENT_PIN -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_change_app_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(33)
            }
            NAVIGATION_NEW_PIN -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_change_app_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(66)
            }
            NAVIGATION_CONFIRM_NEW_PIN -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_change_app_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(100)
            }
            NAVIGATION_OTHER_SMART_OTP -> {
                mBinding.toolbarLayoutBig.visibility = View.GONE
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.toolbarTitleOther.visibility = View.VISIBLE
                mBinding.toolbarTitleOther.text = getString(R.string.smart_otp)
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.VISIBLE
            }
            NAVIGATION_CREATE_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(50)
            }
            NAVIGATION_CONFIRM_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitle.visibility = View.GONE
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(100)
            }
            NAVIGATION_OTP_AUTHENTICATION, NAVIGATION_SMART_OTP_AUTHENTICATION -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.otp_authentication)
                mBinding.pinProgress.visibility = View.GONE
                mBinding.backButton.visibility = View.GONE
                mBinding.crossButton.visibility = View.VISIBLE
            }
            NAVIGATION_OTP_AUTHORIZATION -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.otp_authentication)
                mBinding.pinProgress.visibility = View.GONE
                mBinding.backButton.visibility = View.GONE
                mBinding.crossButton.visibility = View.VISIBLE
            }
            NAVIGATION_USERNAME_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_forgot_smart_otp_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(25)
            }
            NAVIGATION_PASSWORD_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_forgot_smart_otp_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(50)
            }
            NAVIGATION_FORGOT_NEW_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.backButton.visibility = View.VISIBLE
                mBinding.crossButton.visibility = View.GONE
                mBinding.toolbarTitleBig.text = getString(R.string.smart_otp)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(75)
            }
            NAVIGATION_FORGOT_CONFIRM_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.backButton.visibility = View.VISIBLE
                mBinding.crossButton.visibility = View.GONE
                mBinding.toolbarTitleBig.text = getString(R.string.smart_otp)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(100)
            }
            NAVIGATION_CURRENT_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.backButton.visibility = View.VISIBLE
                mBinding.crossButton.visibility = View.GONE
                mBinding.toolbarTitleBig.text = getString(R.string.text_change_smart_otp_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(33)
            }
            NAVIGATION_CHANGE_NEW_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_change_smart_otp_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(67)
            }
            NAVIGATION_CONFIRM_NEW_SMART_OTP -> {
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.GONE
                mBinding.toolbarLayoutBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.visibility = View.VISIBLE
                mBinding.toolbarTitleBig.text = getString(R.string.text_change_smart_otp_pin)
                mBinding.pinProgress.visibility = View.VISIBLE
                updateProgress(100)
            }
            NAVIGATION_ACCOUNT_INFO -> {
                mBinding.toolbarLayoutBig.visibility = View.GONE
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.toolbarTitleOther.visibility = View.GONE
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.VISIBLE
            }
            NAVIGATION_LANGUAGE_SETTINGS -> {
                mBinding.toolbarLayoutBig.visibility = View.GONE
                mBinding.bottomNavigation.visibility = View.GONE
                mBinding.toolbarLayoutHome.visibility = View.GONE
                mBinding.toolbarTitleOther.visibility = View.VISIBLE
                mBinding.toolbarTitleOther.text = getString(R.string.language)
                mBinding.otherOptionIcon.visibility = View.GONE
                mBinding.toolbarLayoutOther.visibility = View.VISIBLE
            }

        }
    }

    private fun callLogoutApi() {

        observeCalls()
        mHomeVM.logout(com.bb.vib.extentions.getPreferences(this@HomeActivity).getUserId)

    }

    private fun observeCalls() {

        mHomeVM.progressIndicator.observe(this, Observer {
            toggleLoader(this@HomeActivity, it)
        })

        mHomeVM.logoutResponse.observe(this, EventObserver {

            if (it.result!!) {
                startAccountsActivity()
            } else {
                showToast("API Response Error")
            }

        })

        mHomeVM.errorResponse.observe(this, Observer {
            ErrorUtil.handlerGeneralError(this@HomeActivity, mBinding.backButton, it)
            Log.d("Error", it.message!!)
        })

    }

    private fun startAccountsActivity() {
        val intent = Intent(this@HomeActivity, AccountsActivity::class.java)
        startActivity(intent)
        finish()
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

        when(navigatorValue) {
            NAVIGATION_OTHER_INPUT_PIN,
            NAVIGATION_OTHER_CONFIRM_SMART_PIN -> {
                navController.popBackStack(R.id.navigation_others, false)
            }
            NAVIGATION_NEW_PIN, NAVIGATION_CONFIRM_NEW_PIN,
            NAVIGATION_OTHER_FORGOT_PIN_USERNAME, NAVIGATION_OTHER_FORGOT_PIN_PASSWORD,
            NAVIGATION_OTHER_CREATE_NEW_PIN, NAVIGATION_OTHER_CONFIRM_NEW_PIN-> {
                navController.popBackStack(R.id.navigation_others_password_pin, false)
            }
            NAVIGATION_CONFIRM_SMART_OTP, NAVIGATION_OTP_AUTHENTICATION, NAVIGATION_PASSWORD_SMART_OTP,
            NAVIGATION_FORGOT_NEW_SMART_OTP, NAVIGATION_FORGOT_CONFIRM_SMART_OTP,
            NAVIGATION_CHANGE_NEW_SMART_OTP, NAVIGATION_CONFIRM_NEW_SMART_OTP, NAVIGATION_OTP_AUTHORIZATION -> {
                navController.popBackStack(R.id.smartOtpPinFragment, false)
            }
            else -> super.onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        com.bb.vib.extentions.getPreferences(this@HomeActivity).setLogin(false)
    }

}