package com.bb.vib.ui.accounts.termsConditions

import android.content.res.Resources
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentTermsConditionsBinding
import com.bb.vib.extentions.getPreferences
import com.bb.vib.utils.LocaleHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class TermsConditionsFragment : BaseFragment<FragmentTermsConditionsBinding>() {

    private val mTermsConditionsVM: TermsConditionsVM by viewModel()

    override fun mLayoutRes(): Int {
        return R.layout.fragment_terms_conditions
    }

    override fun onViewReady() {

        mBinding.termsConditionsVm = mTermsConditionsVM
        mBinding.lifecycleOwner = this

        mBinding.buttonAgree.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_terms_conditions_to_navigation_login)
        }

//        if (getPreferences(requireContext()).getLanguage == "en") {
//            mBinding.textLanguage.text = getString(R.string.lang_vietnamese)
//        } else {
//            mBinding.textLanguage.text = getString(R.string.lang_english)
//        }
//
//        mBinding.textLanguage.setOnClickListener {
//            if (getPreferences(requireContext()).getLanguage == "en") {
//                val context = LocaleHelper.instance.setLocale(requireContext(), "vi")
//                val resources = context?.resources
//                mBinding.textLanguage.text = resources?.getString(R.string.lang_default)
//            } else if (getPreferences(requireContext()).getLanguage == "vi") {
//                val context = LocaleHelper.instance.setLocale(requireContext(), "en")
//                val resources = context?.resources
//                mBinding.textLanguage.text = resources?.getString(R.string.lang_default)
//            }
//        }

        mBinding.textLanguage.setOnClickListener {
            if (getPreferences(requireContext()).getLanguage == "en") {
                val context = LocaleHelper.instance.setLocale(requireContext(), "vi")
                val resources = context?.resources
                mTermsConditionsVM.changeDeviceLanguage("vi")
                refreshCurrentResources(resources!!)
//                activity?.finishAffinity()
//                startActivity(Intent(requireContext(), SplashActivity::class.java))
            } else if (getPreferences(requireContext()).getLanguage == "vi") {
                val context = LocaleHelper.instance.setLocale(requireContext(), "en")
                val resources = context?.resources
                mTermsConditionsVM.changeDeviceLanguage("en")
                refreshCurrentResources(resources!!)
//                activity?.finishAffinity()
//                startActivity(Intent(requireContext(), SplashActivity::class.java))
            }
        }

    }

    private fun refreshCurrentResources(resources: Resources) {
        mBinding.textLanguage.text = resources.getString(R.string.lang_default)
        mBinding.buttonAgree.text = resources.getString(R.string.i_agree)
    }

}