package com.bb.vib.koin


import com.bb.vib.api.WebServiceRequests
import com.bb.vib.ui.accounts.AccountsVM
import com.bb.vib.ui.accounts.confirmSmartPin.ConfirmSmartPinVM
import com.bb.vib.ui.accounts.createPin.CreatePinVM
import com.bb.vib.ui.accounts.forgotPin.ForgotPinVM
import com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.authenticateOtp.AuthenticateOtpVM
import com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.confirmNewPassword.ConfirmNewPasswordVM
import com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.createNewPassword.CreateNewPasswordVM
import com.bb.vib.ui.accounts.forgotYourAccount.forgotUsernamePassword.usernameRecovered.UsernameRecoveredVM
import com.bb.vib.ui.accounts.forgotYourAccount.inputAccountBalance.InputAccountBalanceVM
import com.bb.vib.ui.accounts.forgotYourAccount.inputAccountNumber.InputAccountNumberVM
import com.bb.vib.ui.accounts.forgotYourAccount.inputBusinessLicense.InputBusinessLicenseVM
import com.bb.vib.ui.accounts.forgotYourAccount.inputIdentityPaper.InputIdentityPaperVM
import com.bb.vib.ui.accounts.forgotYourAccount.selectCredentials.SelectCredentialsVM
import com.bb.vib.ui.accounts.forgotYourAccount.selectIdentityPaper.SelectIdentityPaperVM
import com.bb.vib.ui.accounts.forgotYourAccount.verificationCode.VerificationCodeVM
import com.bb.vib.ui.accounts.login.LoginVM
import com.bb.vib.ui.accounts.loginPin.LoginPinVM
import com.bb.vib.ui.accounts.loginPin.loginActiveBiometric.LoginActiveBiometricConfirmPinVM
import com.bb.vib.ui.accounts.termsConditions.TermsConditionsVM
import com.bb.vib.ui.accounts.useFaceId.UseFaceIdVM
import com.bb.vib.ui.home.HomeVM
import com.bb.vib.ui.home.account.AccountVM
import com.bb.vib.ui.home.account.currentAccount.CurrentAccountVM
import com.bb.vib.ui.home.account.currentAccount.transactionAdvice.TransactionAdviceVM
import com.bb.vib.ui.home.others.OthersVM
import com.bb.vib.ui.home.others.accountInfo.AccountInfoVM
import com.bb.vib.ui.home.others.activeBiometric.ActiveBiometricConfirmPinVM
import com.bb.vib.ui.home.others.deactivateBiometric.DeactivateBiometricConfirmPinVM
import com.bb.vib.ui.home.others.languageSettings.LanguageSettingsVM
import com.bb.vib.ui.home.others.otherConfirmSmartPin.OtherConfirmSmartPinVM
import com.bb.vib.ui.home.others.otherCreatePin.OtherCreatePinVM
import com.bb.vib.ui.home.others.otherForgotPin.OtherForgotPinVM
import com.bb.vib.ui.home.others.otherUseFingerprint.OtherUseFingerprintVM
import com.bb.vib.ui.home.others.passwordPin.PasswordPinVM
import com.bb.vib.ui.home.others.passwordPin.changePin.confirmNewPin.ConfirmNewPinVM
import com.bb.vib.ui.home.others.passwordPin.changePin.currentPin.CurrentPinVM
import com.bb.vib.ui.home.others.passwordPin.changePin.newPin.NewPinVM
import com.bb.vib.ui.home.others.smartOtpPin.SmartOtpPinVM
import com.bb.vib.ui.home.others.smartOtpPin.activeSmartOtp.confirmSmartOtp.ConfirmSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.activeSmartOtp.createSmartOtp.CreateSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.activeSmartOtp.otpAuthentication.OtpAuthenticationVM
import com.bb.vib.ui.home.others.smartOtpPin.changeSmartOtp.changeNewSmartOtp.ChangeNewSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.changeSmartOtp.confirmChangeNewSmartOtp.ConfirmChangeNewSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.changeSmartOtp.currentSmartOtp.CurrentSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.forgotSmartOtp.confirmNewSmartOtp.ConfirmNewSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.forgotSmartOtp.newSmartOtp.NewSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.forgotSmartOtp.otpAuthorization.OtpAuthorizationVM
import com.bb.vib.ui.home.others.smartOtpPin.forgotSmartOtp.passwordForgotSmartOtp.PasswordForgotSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.forgotSmartOtp.usernameForgotSmartOtp.UsernameForgotSmartOtpVM
import com.bb.vib.ui.home.others.smartOtpPin.inactiveSmartOtp.smartOtpAuthentication.SmartOtpAuthenticationVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { WebServiceRequests() }

//    factory { (mListener: MyClickListener) -> AdapterPassCode(mListener) }

    viewModel { AccountsVM(get()) }
    viewModel { TermsConditionsVM(get()) }
    viewModel { LoginVM(get()) }
    viewModel { LoginPinVM(get()) }
    viewModel { LoginActiveBiometricConfirmPinVM(get()) }
    viewModel { CreatePinVM(get()) }
    viewModel { UseFaceIdVM(get()) }
    viewModel { ForgotPinVM(get()) }
    viewModel { ConfirmSmartPinVM(get()) }

    viewModel { InputBusinessLicenseVM(get()) }
    viewModel { SelectIdentityPaperVM(get()) }
    viewModel { InputIdentityPaperVM(get()) }
    viewModel { InputAccountNumberVM(get()) }
    viewModel { InputAccountBalanceVM(get()) }
    viewModel { VerificationCodeVM(get()) }
    viewModel { SelectCredentialsVM(get()) }
    viewModel { CreateNewPasswordVM(get()) }
    viewModel { ConfirmNewPasswordVM(get()) }
    viewModel { AuthenticateOtpVM(get()) }
    viewModel { UsernameRecoveredVM(get()) }

    viewModel { HomeVM(get()) }

    viewModel { AccountVM(get()) }
    viewModel { CurrentAccountVM(get()) }
    viewModel { TransactionAdviceVM(get()) }

    viewModel { OthersVM(get()) }

    viewModel { AccountInfoVM(get()) }

    viewModel { LanguageSettingsVM(get()) }

    viewModel { PasswordPinVM(get()) }
    viewModel { OtherForgotPinVM(get()) }

    viewModel { OtherUseFingerprintVM(get()) }

    viewModel { OtherCreatePinVM(get()) }
    viewModel { OtherConfirmSmartPinVM(get()) }

    viewModel { CurrentPinVM(get()) }
    viewModel { NewPinVM(get()) }
    viewModel { ConfirmNewPinVM(get()) }
    viewModel { SmartOtpPinVM(get()) }
    viewModel { CreateSmartOtpVM(get()) }
    viewModel { ConfirmSmartOtpVM(get()) }
    viewModel { OtpAuthenticationVM(get()) }
    viewModel { SmartOtpAuthenticationVM(get()) }

    viewModel { UsernameForgotSmartOtpVM(get()) }
    viewModel { PasswordForgotSmartOtpVM(get()) }
    viewModel { NewSmartOtpVM(get()) }
    viewModel { ConfirmNewSmartOtpVM(get()) }
    viewModel { OtpAuthorizationVM(get()) }

    viewModel { CurrentSmartOtpVM(get()) }
    viewModel { ChangeNewSmartOtpVM(get()) }
    viewModel { ConfirmChangeNewSmartOtpVM(get()) }

    viewModel { ActiveBiometricConfirmPinVM(get()) }
    viewModel { DeactivateBiometricConfirmPinVM(get()) }


}