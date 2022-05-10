package com.bb.vib.ui.home.others.otherUseFaceId

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.bb.vib.R
import com.bb.vib.base.BaseFragment
import com.bb.vib.databinding.FragmentOtherUseFaceIdBinding
import com.bb.vib.ui.accounts.AccountsActivity
import com.bb.vib.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class OtherUseFaceIdFragment : BaseFragment<FragmentOtherUseFaceIdBinding>() {

    private val mUseFaceIdVM: OtherUseFaceIdVM by viewModel()

    private var from = 0

    override fun mLayoutRes(): Int {
        return R.layout.fragment_other_use_face_id
    }

    override fun onViewReady() {

        mBinding.useFaceIdVm = mUseFaceIdVM
        mBinding.lifecycleOwner = this

        if (arguments != null) {
            from = requireArguments().getInt("from")
        }

        mBinding.buttonAgree.setOnClickListener {
            if (from == 2) {
                val bundle = Bundle()
                bundle.putInt("type", 6)
                findNavController().navigate(
                    R.id.action_navigation_use_face_id_to_navigation_input_pin,
                    bundle
                )
            } else {
                val bundle = Bundle()
                bundle.putInt("type", 3)
                (requireContext() as AccountsActivity).setNavigation(AccountsActivity.SCREEN_INPUT_PIN)
                findNavController().navigate(
                    R.id.action_navigation_use_face_id_to_navigation_input_pin,
                    bundle
                )
            }
        }

        mBinding.buttonLater.setOnClickListener {
            if (from == 2) {
                activity?.onBackPressed()
            } else {
                startHomeActivity()
            }
        }

    }

    private fun startHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}