package com.decagonhq.clads.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.ResetPasswordFragmentBinding
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch
import com.google.android.material.textfield.TextInputEditText

class ResetPasswordFragment : Fragment() {

    private var _binding: ResetPasswordFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var newPasswordEditText: TextInputEditText
    private lateinit var confirmNewPasswordEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*Inflate the layout for this fragment*/
        _binding = ResetPasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Initialize User Inputs*/
        newPasswordEditText = binding.resetPasswordFragmentNewPasswordEditText
        confirmNewPasswordEditText = binding.resetPasswordFragmentConfirmNewPasswordEditText

        /*Validate Email*/
        binding.resetPasswordFragmentBtnResetPasswordButton.setOnClickListener {

            when {
                newPasswordEditText.text.toString().isEmpty() -> {
                    binding.resetPasswordFragmentNewPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.resetPasswordFragmentNewPasswordEditTextLayout.errorIconDrawable = null
                    return@setOnClickListener
                }

                confirmNewPasswordEditText.text.toString().isEmpty() -> {
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.errorIconDrawable =
                        null
                    return@setOnClickListener
                }

                !validatePasswordMismatch(
                    newPasswordEditText.text.toString(),
                    confirmNewPasswordEditText.text.toString()
                ) -> {
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.error =
                        getString(R.string.all_password_mismatch)
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.errorIconDrawable =
                        null
                    return@setOnClickListener
                }
                else -> {
                    if (validateSignUpFieldsOnTextChange()) {
                        val action = ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /*Method to Validate Text Field onText Change*/
        validateSignUpFieldsOnTextChange()
    }

    /*Method to Validate All Password Fields*/
    private fun validateSignUpFieldsOnTextChange(): Boolean {
        var isValidated = true

        confirmNewPasswordEditText.doOnTextChanged { _, _, _, _ ->
            when {
                confirmNewPasswordEditText.text.toString().trim().isEmpty() -> {
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.errorIconDrawable =
                        null
                    isValidated = false
                }
                !validatePasswordMismatch(
                    newPasswordEditText.text.toString().trim(),
                    confirmNewPasswordEditText.text.toString().trim()
                ) -> {
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.error =
                        getString(R.string.all_password_mismatch)
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.errorIconDrawable =
                        null
                    isValidated = false
                }
                else -> {
                    binding.resetPasswordFragmentConfirmNewPasswordEditTextLayout.error = null
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
