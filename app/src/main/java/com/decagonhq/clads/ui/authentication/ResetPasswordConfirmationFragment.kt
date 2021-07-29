package com.decagonhq.clads.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.databinding.ResetPasswordConfirmationFragmentBinding
import com.google.android.material.button.MaterialButton

class ResetPasswordConfirmationFragment : Fragment() {

    private var _binding: ResetPasswordConfirmationFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var resetPasswordButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*Inflate the layout for this fragment*/
        _binding = ResetPasswordConfirmationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetPasswordButton = binding.passwordResetConfirmationFragmentResetPasswordButton

        resetPasswordButton.setOnClickListener {
            /*Send Reset Password Email*/
            val action = ResetPasswordConfirmationFragmentDirections.actionConfirmPasswordResetFragmentToResetPasswordFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
