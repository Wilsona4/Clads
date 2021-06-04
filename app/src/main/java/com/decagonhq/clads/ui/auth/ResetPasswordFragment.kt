package com.decagonhq.clads.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.ResetPasswordFragmentBinding
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch

class ResetPasswordFragment : Fragment() {

    private var _binding: ResetPasswordFragmentBinding? = null
    private val binding get() = _binding!!

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
        val newPasswordEditText = binding.resetPasswordFragmentNewPasswordEditText
        val confirmNewPasswordEditText = binding.resetPasswordFragmentConfirmNewPasswordEditText

        /*Validate Email*/
        binding.resetPasswordFragmentBtnResetPasswordButton.setOnClickListener {

            when {

                newPasswordEditText.text.toString().isEmpty() -> {
                    newPasswordEditText.error = getString(R.string.all_password_is_required)
                    return@setOnClickListener
                }

                confirmNewPasswordEditText.text.toString().isEmpty() -> {
                    confirmNewPasswordEditText.error = getString(R.string.all_password_is_required)
                    return@setOnClickListener
                }

                !validatePasswordMismatch(
                    newPasswordEditText.text.toString(),
                    confirmNewPasswordEditText.text.toString()
                ) -> {

                    binding.resetPasswordFragmentConfirmNewPasswordEditText.error =
                        getString(R.string.all_password_mismatch)
                    return@setOnClickListener
                }
                else -> {
                    findNavController().navigate(R.id.login_fragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
