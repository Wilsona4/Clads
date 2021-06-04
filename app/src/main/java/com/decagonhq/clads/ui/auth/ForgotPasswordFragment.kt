package com.decagonhq.clads.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.ForgotPasswordFragmentBinding

class ForgotPasswordFragment : Fragment() {

    private var _binding: ForgotPasswordFragmentBinding? = null
    private val binding get() = _binding!!

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
        val emailEditText = binding.forgotPasswordFragmentEmailEditText

        /*Validate Email*/
        binding.forgotPasswordFragmentSendRequestButton.setOnClickListener {

            if (emailEditText.text.toString().isEmpty()) {
                emailEditText.error = getString(R.string.all_email_cant_be_empty)
                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_confirmPasswordResetFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
