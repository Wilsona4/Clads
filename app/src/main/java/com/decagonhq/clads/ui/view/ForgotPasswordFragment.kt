package com.decagonhq.clads.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Initialize User Inputs*/
        val emailEditText = binding.forgotPasswordFragmentEmailEditText

        /*Validate Email*/
        binding.forgotPasswordFragmentSendRequestButton.setOnClickListener {

            if (emailEditText.text.toString().isEmpty()) {
                emailEditText.error = "Email Can't Be Empty"
                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_confirmPasswordResetFragment)
        }
    }


}
