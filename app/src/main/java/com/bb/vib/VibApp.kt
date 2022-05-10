package com.bb.vib

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.bb.vib.api.Constants
import com.bb.vib.koin.appModule
import com.bb.vib.ui.home.others.smartOtpPin.softTokenSdk.utils.SharedPrefUtils
import com.bb.vib.utils.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import vn.mk.otp.OtpSdk

class VibApp : Application() {

    private var mSharedPrefUtils: SharedPrefUtils? = null

    init {
        instance = this
    }

    companion object {
        private var instance: VibApp? = null

        fun applicationContext(): VibApp {
            return instance as VibApp
        }

        fun getSharedPreferences(): SharedPrefUtils? {
            return instance!!.mSharedPrefUtils
        }

    }

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidContext(this@VibApp)
            modules(listOf(appModule))
        }

        mSharedPrefUtils = SharedPrefUtils(this)
        OtpSdk.init(this@VibApp)

        Constants.DeviceId = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        Constants.DeviceName = android.os.Build.MODEL

        val fields = android.os.Build.VERSION_CODES::class.java.fields
        var codeName = "UNKNOWN"
        fields.filter { it.getInt(android.os.Build.VERSION_CODES::class) == android.os.Build.VERSION.SDK_INT }
            .forEach { codeName = it.name }
        Constants.OsName = codeName

        Constants.Platform = "Android"

        if (PreferenceManager.getInstance(this).isLoggedIn) {
            Constants.HEADER_TOKEN = "Basic " + PreferenceManager.getInstance(this).getToken
        }

        Log.d("AuthToken", Constants.HEADER_TOKEN + "")

    }

}