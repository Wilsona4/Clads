package com.decagonhq.clads.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.LandingScreenFragmentBinding

class LandingScreenFragment : Fragment() {

    private lateinit var emailLoginButton: TextView
    private var _binding: LandingScreenFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = LandingScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailLoginButton = binding.landingScreenFragmentLoginButton

        // Navigation to sign up option fragment
        binding.landingScreenFragmentSignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_landingScreenFragment_to_signUpOptionsFragment)
        }

        // Navigation to login fragment
        emailLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_landing_screen_fragment_to_login_fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
