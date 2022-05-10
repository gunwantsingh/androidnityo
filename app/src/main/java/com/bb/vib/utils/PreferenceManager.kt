package com.bb.vib.utils

import android.content.Context
import android.content.SharedPreferences

open class PreferenceManager protected constructor(context: Context) {
    private val preference: SharedPreferences =
        context.getSharedPreferences("Test", Context.MODE_PRIVATE)

    companion object {
        private var preferenceManager: PreferenceManager? = null
        private const val CAMEBACK = "CAMEBACK"
        private const val REGISTER = "REGISTER"
        private const val LOGIN = "LOGIN"
        private const val PIN = "PIN"
        private const val TOKEN = "TOKEN"
        private const val USER_ID = "USER_ID"
        private const val USER_NAME = "USER_NAME"
        private const val USER_MOBILE = "USER_MOBILE"
        private const val EMAIL = "EMAIL"
        private const val QUALIFICATION = "QUALIFICATION"
        private const val ROLE = "ROLE"
        private const val ROLE_ID = "ROLE_ID"
        private const val USER_CODE = "USER_CODE"
        private const val DOB = "DOB"
        private const val OTP_VERIFIED = "OTP_VERIFIED"
        private const val BIOMETRIC = "BIOMETRIC"
        private const val BIOMETRIC_TOKEN = "BIOMETRIC_TOKEN"
        private const val BIOMETRIC_SUPPORT = "BIOMETRIC_SUPPORT"
        private const val SMART_OTP = "SMART_OTP"
        private const val LONG_OTP = "LONG_OTP"
        private const val LANGUAGE = "LANGUAGE"
        private const val USERNAME = "USERNAME"
        private const val USERID = "USERID"
        private const val PHONE = "PHONE"
        private const val PROFILE_IMAGE = "PROFILE"
        private const val USER_FIRST_NAME = "USER_FIRST_NAME"
        private const val USER_LAST_NAME = "USER_LAST_NAME"

        fun getInstance(context: Context): PreferenceManager {
            if (preferenceManager == null)
                preferenceManager =
                    PreferenceManager(context)
            return preferenceManager as PreferenceManager
        }
    }

    fun setToken(login: String) {
        preference.edit().putString(TOKEN, login).apply()
    }

    val getToken: String
        get() = preference.getString(TOKEN, "AR-AUG-ARST-BIZBR-2019OLLY")!!

    fun setCameBack(check: Boolean) {
        preference.edit().putBoolean(CAMEBACK, check).apply()
    }

    val isComingBack : Boolean
        get() = preference.getBoolean(CAMEBACK, false)

    fun setRegister(register: Boolean) {
        preference.edit().putBoolean(REGISTER, register).apply()
    }

    val isUserRegistered: Boolean
        get() = preference.getBoolean(REGISTER, false)

    fun setLogin(login: Boolean) {
        preference.edit().putBoolean(LOGIN, login).apply()
    }

    val isLoggedIn: Boolean
        get() = preference.getBoolean(LOGIN, false)

    fun setPinCreation(pin: Boolean) {
        preference.edit().putBoolean(PIN, pin).apply()
    }

    val isPinCreated: Boolean
        get() = preference.getBoolean(PIN, false)

    fun setBiometricSupport(biometric: Boolean) {
        preference.edit().putBoolean(BIOMETRIC_SUPPORT, biometric).apply()
    }

    val canUseBiometric: Boolean
        get() = preference.getBoolean(BIOMETRIC_SUPPORT, false)

    fun setBiometric(biometric: Boolean) {
        preference.edit().putBoolean(BIOMETRIC, biometric).apply()
    }

    val isBiometricEnabled: Boolean
        get() = preference.getBoolean(BIOMETRIC, false)

    fun setBiometricToken(biometricToken: String) {
        preference.edit().putString(BIOMETRIC_TOKEN, biometricToken).apply()
    }

    val getBiometricToken: String
        get() = preference.getString(BIOMETRIC_TOKEN, "").toString()

    fun setSmartOtp(smartOtp: Boolean) {
        preference.edit().putBoolean(SMART_OTP, smartOtp).apply()
    }

    val isSmartOtpEnabled: Boolean
        get() = preference.getBoolean(SMART_OTP, false)

    fun setLongOtp(longOtp: Boolean) {
        preference.edit().putBoolean(LONG_OTP, longOtp).apply()
    }

    val isLongOtp: Boolean
        get() = preference.getBoolean(LONG_OTP, false)

    fun setLanguage(language: String) {
        preference.edit().putString(LANGUAGE, language).apply()
    }

    val getLanguage: String
        get() = preference.getString(LANGUAGE, "en")!!

    fun setUserId(userId: String) {
        preference.edit().putString(USER_ID, userId).apply()
    }

    val getUserId: String
        get() = preference.getString(USER_ID, "").toString()

    fun setUserName(userName: String) {
        preference.edit().putString(USER_NAME, userName).apply()
    }

    val getUserName: String
        get() = preference.getString(USER_NAME, "").toString()

    fun setUserImage(profile_image: String) {
        preference.edit().putString(PROFILE_IMAGE, profile_image).apply()
    }

    val getUserImage: String
        get() = preference.getString(PROFILE_IMAGE, "").toString()

    fun setUserMobile(userMobile: String) {
        preference.edit().putString(USER_MOBILE, userMobile).apply()
    }

    val getUserMobile: String
        get() = preference.getString(USER_MOBILE, "").toString()

    fun setEmail(email: String) {
        preference.edit().putString(EMAIL, email).apply()
    }

    val getEmail: String
        get() = preference.getString(EMAIL, "").toString()

    fun setQualification(qualification: String) {
        preference.edit().putString(QUALIFICATION, qualification).apply()
    }

    val getQualification: String
        get() = preference.getString(QUALIFICATION, "").toString()

    fun setRole(role: String) {
        preference.edit().putString(ROLE, role).apply()
    }

    val getRole: String
        get() = preference.getString(ROLE, "").toString()

    fun setRoleId(roleId: Int) {
        preference.edit().putInt(ROLE_ID, roleId).apply()
    }

    val getRoleId: Int
        get() = preference.getInt(ROLE_ID, 0)

    fun setUserCode(userCode: Int) {
        preference.edit().putInt(USER_CODE, userCode).apply()
    }

    val getUserCode: Int
        get() = preference.getInt(USER_CODE, 0)

    fun setDob(dob: String) {
        preference.edit().putString(DOB, dob).apply()
    }

    val getDob: String
        get() = preference.getString(DOB, "").toString()

    fun setOtpVerified(otpVerified: Boolean) {
        preference.edit().putBoolean(OTP_VERIFIED, otpVerified).apply()
    }

    val isOtpVerified: Boolean
        get() = preference.getBoolean(OTP_VERIFIED, false)

    fun setAuthToken(authToken: String) {
        preference.edit().putString(TOKEN, authToken).apply()
    }

    val getAuthToken: String
        get() = preference.getString(TOKEN, "").toString()

    open fun clear() {
        preference.edit().clear().apply()
    }

}