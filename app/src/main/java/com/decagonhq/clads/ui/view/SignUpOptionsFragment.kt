package com.decagonhq.clads.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.FragmentSignUpOptionsBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult

class SignUpOptionsFragment : Fragment() {
    private lateinit var cladsGoogleSignInClient: GoogleSignInClient
    private var _binding: FragmentSignUpOptionsBinding? = null
    private val binding get() = _binding!!
    private var SIGN_IN = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSignUpOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // create the google signin client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        cladsGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        // add a listener to the signin button
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = cladsGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN)
    }

    // gets the result of successful authentication
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                handleSignInResult(result)
            }
        }
    }

    // handles the result of signin from user
    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            findNavController().navigate(R.id.action_signUpOptionsFragment_to_emailSignUpFragment)
        } else {
            Toast.makeText(
                requireContext().applicationContext,
                "Sign in cancel",
                Toast.LENGTH_LONG
            ).show()
        }
    }

// check for existing account on start
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        if (account != null) {
            findNavController().navigate(R.id.action_signUpOptionsFragment_to_emailSignUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
