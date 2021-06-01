package com.decagonhq.clads.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.FragmentLoginBinding
import com.decagonhq.clads.utils.ValidationObject


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginFragmentLogInCardView.setOnClickListener {
            val emailAddress = binding.loginFragmentEmailAddressEditText.text.toString()
            val password = binding.loginFragmentPasswordEditText.text.toString()

            when {
                emailAddress.isEmpty() -> {
                    binding.loginFragmentEmailAddressEditText.error = "Please enter email"
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.loginFragmentPasswordEditText.error = "Please enter password"
                    return@setOnClickListener
                }
                !ValidationObject.validateEmail(emailAddress) -> {
                    binding.loginFragmentEmailAddressEditText.error = "Invalid email"
                    return@setOnClickListener
                }
                else -> {
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}





