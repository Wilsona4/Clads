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

    // Binding
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

        //Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // On login button pressed
        binding.loginFragmentLogInCardView.setOnClickListener {
            val emailAddress = binding.loginFragmentEmailAddressEditText.text.toString()
            val password = binding.loginFragmentPasswordEditText.text.toString()
            when {
                // Check if email is empty
                emailAddress.isEmpty() -> {
                    binding.loginFragmentEmailAddressEditText.error = "Please enter email"
                    return@setOnClickListener
                }
                // Check if the password is empty
                password.isEmpty() -> {
                    binding.loginFragmentPasswordEditText.error = "Please enter password"
                    return@setOnClickListener
                }
                // Check if the email is valid
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
