package com.decagonhq.clads.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.ForgotPasswordFragmentBinding
import com.decagonhq.clads.util.ValidationObject
import com.google.android.material.textfield.TextInputEditText

class ForgotPasswordFragment : Fragment() {

    private var _binding: ForgotPasswordFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var emailEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*Inflate the layout for this fragment*/
        _binding = ForgotPasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Initialize User Inputs*/
        emailEditText = binding.forgotPasswordFragmentEmailEditText

        /*Validate Email*/
        binding.forgotPasswordFragmentSendRequestButton.setOnClickListener {
            when {
                emailEditText.text.toString().trim().isEmpty() -> {
                    binding.forgotPasswordFragmentEmailEditTextInputLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    return@setOnClickListener
                }
                !ValidationObject.validateEmail(emailEditText.text.toString().trim()) -> {
                    binding.forgotPasswordFragmentEmailEditTextInputLayout.error =
                        getString(R.string.all_invalid_email)
                    return@setOnClickListener
                }
                else -> {
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_confirmPasswordResetFragment)
                }
            }
        }

        /*Navigate to Login Screen*/
        binding.forgotPasswordFragmentLoginTextView.setOnClickListener {
            findNavController().navigate(R.id.login_fragment)
        }
    }

    override fun onResume() {
        super.onResume()
        /*Method to Validate Email Field onText Change*/
        validateSignUpFieldsOnTextChange()
    }

    /*Method to Validate Email Field onText Change*/
    private fun validateSignUpFieldsOnTextChange(): Boolean {
        var isValidated = true

        emailEditText.doOnTextChanged { _, _, _, _ ->
            when {
                emailEditText.text.toString().trim().isEmpty() -> {
                    binding.forgotPasswordFragmentEmailEditTextInputLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    isValidated = false
                }
                !ValidationObject.validateEmail(emailEditText.text.toString().trim()) -> {
                    binding.forgotPasswordFragmentEmailEditTextInputLayout.error =
                        getString(R.string.all_invalid_email)
                    isValidated = false
                }
                else -> {
                    binding.forgotPasswordFragmentEmailEditTextInputLayout.error = null
                    isValidated = true
                }
            }
        }
        return isValidated
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
