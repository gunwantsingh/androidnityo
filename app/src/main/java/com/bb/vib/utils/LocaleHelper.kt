package com.bb.vib.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.extentions.getPreferences
import java.util.*


class LocaleHelper {

    companion object {
        val instance = LocaleHelper()
    }

    // the method is used to set the language at runtime
    fun setLocale(context: Context, language: String): Context? {
        persist(context, language)

        // updating the language for devices above android nougat
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResources(context, language)
        // for devices having lower version of android os
    }

    private fun persist(context: Context, language: String) {
        val preferences = getPreferences(context)
        preferences.setLanguage(language)
    }

    // the method is used update the language of application by creating
    // object of inbuilt Locale class and passing language argument to it
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
//        configuration.setLayoutDirection(locale)
//        return context.createConfigurationContext(configuration)

        val applicationRes = context.applicationContext.resources
        val applicationConf = applicationRes.configuration
        applicationConf.setLocale(locale)
        context.createConfigurationContext(configuration)
        applicationRes.updateConfiguration(
            applicationConf,
            applicationRes.displayMetrics
        )

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}