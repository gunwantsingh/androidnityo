package com.bb.vib.ui.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bb.vib.BuildConfig
import com.bb.vib.R
import com.bb.vib.api.Constants
import com.bb.vib.api.model.request.DeviceInfoRequestModel
import com.bb.vib.extentions.getCurrentLatitude
import com.bb.vib.extentions.getCurrentLongitude
import com.bb.vib.extentions.getCurrentTimeStamp
import com.bb.vib.ui.accounts.AccountsActivity
import com.bb.vib.ui.accounts.AccountsVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SplashActivity : AppCompatActivity() {

    private val mAccountsVM: AccountsVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_splash)

        // making the status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        val postDeviceRequest = DeviceInfoRequestModel(
            BuildConfig.VERSION_CODE.toString(),
            com.bb.vib.extentions.getPreferences(this@SplashActivity).isBiometricEnabled.toString(),
            getCurrentTimeStamp(),
            Constants.DeviceId,
            "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            com.bb.vib.extentions.getPreferences(this@SplashActivity).getLanguage,
            50,
            70,
            Constants.DeviceName,
            "",
            Constants.OsName,
            Constants.Platform,
            "",
            getCurrentTimeStamp()
        )
        mAccountsVM.postDeviceInfo(postDeviceRequest)

        val lang = com.bb.vib.extentions.getPreferences(this@SplashActivity).getLanguage
        setApplicationLanguage(lang)

        startActivity(Intent(this, AccountsActivity::class.java))
        finish()

//        Handler().postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }, 2000)

//        if (!PreferenceManager.getInstance(this).isComingBack) {
//            PreferenceManager.getInstance(this).setCameBack(true)
//            startActivity(Intent(this, WelcomeActivity::class.java))
//            finish()
//        } else if (PreferenceManager.getInstance(this).isLoggedIn) {
//            Constants.HEADER_TOKEN =
//                "Basic " + PreferenceManager.getInstance(applicationContext).getToken
//            startActivity(Intent(this, HomeActivity::class.java))
//            finish()
//        } else {
//            startActivity(Intent(this, AccountsActivity::class.java))
//            finish()
//        }




    }

    private fun setApplicationLanguage(newLanguage: String) {
        val activityRes = resources
        val activityConf = activityRes.configuration
        val newLocale = Locale(newLanguage)
        Locale.setDefault(newLocale)
        activityConf.setLocale(newLocale)
        createConfigurationContext(activityConf)
        activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)


        val applicationRes = applicationContext.resources
        val applicationConf = applicationRes.configuration
        applicationConf.setLocale(newLocale)
        createConfigurationContext(applicationConf)
        applicationRes.updateConfiguration(
            applicationConf,
            applicationRes.displayMetrics
        )

    }

}