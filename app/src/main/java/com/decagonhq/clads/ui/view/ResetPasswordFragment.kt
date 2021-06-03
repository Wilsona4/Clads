package com.decagonhq.clads.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.FragmentResetPasswordBinding
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Initialize User Inputs*/
        val newPasswordEditText = binding.resetPasswordFragmentNewPasswordEditText
        val confirmNewPasswordEditText = binding.resetPasswordFragmentConfirmNewPasswordEditText

        /*Validate Email*/
        binding.resetPasswordFragmentBtnResetPasswordButton.setOnClickListener {

            when {

                newPasswordEditText.text.toString().isEmpty() -> {
                    newPasswordEditText.error = "Password Can't Be Empty"
                    return@setOnClickListener
                }

                confirmNewPasswordEditText.text.toString().isEmpty() -> {
                    confirmNewPasswordEditText.error = "Confirm Password Can't Be Empty"
                    return@setOnClickListener
                }

                !validatePasswordMismatch(
                    newPasswordEditText.text.toString(),
                    confirmNewPasswordEditText.text.toString()
                ) -> {
                    binding.resetPasswordFragmentNewPasswordEditText.error =
                        "Password Can't Be Empty"
                    binding.resetPasswordFragmentConfirmNewPasswordEditText.error =
                        "Confirm Password Can't Be Empty"
                    return@setOnClickListener
                }
                else -> {
                    findNavController().navigate(R.id.action_reset_password_fragment_to_loginFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
