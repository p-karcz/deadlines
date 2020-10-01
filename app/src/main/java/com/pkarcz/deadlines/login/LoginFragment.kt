package com.pkarcz.deadlines.login

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.facebook.CallbackManager
import com.pkarcz.deadlines.R
import com.pkarcz.deadlines.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_login, container, false)

        val loginButton = binding.loginButton

        loginButton.fragment = this
        loginButton.setPermissions("email")

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}