package com.bb.vib.ui.home.others.languageSettings

import android.content.res.Resources
import android.view.View
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentLanguageSettingsBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.ui.accounts.termsConditions.TermsConditionsVM
import com.bb.vib.ui.home.HomeActivity
import com.bb.vib.utils.LocaleHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class LanguageSettingsFragment : BaseFragment<FragmentLanguageSettingsBinding>() {

    private val mLanguageSettingsVM: LanguageSettingsVM by viewModel()
    private val mTermsConditionsVM: TermsConditionsVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_language_settings
    }

    override fun onViewReady() {

        mBinding.languageSettingsVm = mLanguageSettingsVM
        mBinding.lifecycleOwner = this

        if (getPreferences(requireContext()).getLanguage == "en") {
            mBinding.vietnameseRb.visibility = View.GONE
            mBinding.englishRb.visibility = View.VISIBLE
        } else {
            mBinding.englishRb.visibility = View.GONE
            mBinding.vietnameseRb.visibility = View.VISIBLE
        }

        mBinding.vietnameseLayout.setOnClickListener {
            mBinding.englishRb.visibility = View.GONE
            mBinding.vietnameseRb.visibility = View.VISIBLE

            val context = LocaleHelper.instance.setLocale(requireContext(), "vi")
            val resources = context?.resources
            mTermsConditionsVM.changeDeviceLanguage("vi")
            refreshCurrentResources(resources!!)
        }

        mBinding.englishLayout.setOnClickListener {
            mBinding.vietnameseRb.visibility = View.GONE
            mBinding.englishRb.visibility = View.VISIBLE

            val context = LocaleHelper.instance.setLocale(requireContext(), "en")
            val resources = context?.resources
            mTermsConditionsVM.changeDeviceLanguage("en")
            refreshCurrentResources(resources!!)
        }


    }

    private fun refreshCurrentResources(resources: Resources) {
        (requireContext() as HomeActivity).setNavigation(HomeActivity.NAVIGATION_LANGUAGE_SETTINGS)
    }

}