package com.bb.vib.api

class Constants {
    companion object {
        const val BASE_URL = "http://devbox.bizbrolly.com:8914/"
//        const val BASE_URL = "https://nonprod-msmeapi.vib.com.vn/dev/msme-customerprofile/"
        var DeviceName: String = "deviceName"
        var DeviceId: String = "deviceId"
        var DeviceIdAPI: String = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        var OsName: String = "osName"
        var Platform: String = "platform"
        var HEADER_TOKEN = "Basic AR-AUG-ARST-BIZBR-2019OLLY"

        const val FIRST_TIME_LOGIN = 1
        const val TOTAL_LOGIN_TIMES = 5

    }

    internal object Partials {
        const val Login = "api/Account/Login"
        const val Logout = "api/Account/Logout"
        const val DeviceLanguage = "api/Device/DeviceLanguage"
        const val EnableDisableBiometric = "api/Device/EnableDisableBiometric"
        const val DeviceInfo = "DeviceInfo"
        const val LoginWithBiometric = "api/User/LoginWithBiometric"
        const val LoginWithPin = "api/User/LoginWithPin"
        const val RegisterPin = "api/User/RegisterPin"
        const val UserPin = "api/User/UserPin"
        const val VerifyPin = "api/User/VerifyPin"
        const val User = "api/User/{userId}"
        const val VerifyUsername = "api/User/VerifyUsername"
        const val VerifyPassword = "api/User/VerifyPassword"
        const val ActivateSmartOtp = "api/SmartOtp/ActivateSmartOtp"
        const val CheckRegisterOtpMethod = "api/SmartOtp/CheckRegisterOtpMethod"
        const val SendActivationCode = "api/SmartOtp/SendActivationCode"
        const val VerifyActivationCode = "api/SmartOtp/VerifyActivationCode"
    }

    internal object Keys {
        const val Username = "Username"
        const val UserName = "userName"
        const val username = "username"
        const val Password = "password"
        const val Pin = "pin"
        const val UserId = "userId"
        const val DeviceId = "deviceId"
        const val BiometricsEnabled = "biometricsEnabled"
        const val Language = "language"
        const val BiometricToken = "biometricToken"
        const val ActivationCode = "activationCode"
    }
}