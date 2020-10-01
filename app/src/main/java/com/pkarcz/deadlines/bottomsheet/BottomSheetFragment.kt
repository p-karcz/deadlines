package com.pkarcz.deadlines.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pkarcz.deadlines.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pkarcz.deadlines.databinding.FragmentBottomSheetBinding
import com.pkarcz.deadlines.isUserLoggedIn

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentBottomSheetBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_bottom_sheet, container, false)

        activity?.let {
            NavigationUI.setupWithNavController(binding.navViewBottom, it.findNavController(R.id.navHostFragment))
        }

        if(isUserLoggedIn()) {
            binding.navViewBottom.menu.findItem(R.id.loginFragment).title = "Log out"
        } else {
            binding.navViewBottom.menu.findItem(R.id.loginFragment).title = "Log in with Facebook"
        }

        return binding.root
    }
}