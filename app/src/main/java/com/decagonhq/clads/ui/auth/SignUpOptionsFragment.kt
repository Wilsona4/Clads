package com.decagonhq.clads.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.SignUpOptionsFragmentBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class SignUpOptionsFragment : Fragment() {
    private var _binding: SignUpOptionsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var emailSignUpButton: TextView
    private lateinit var googleSignUpButton: TextView
    private lateinit var loginButton: TextView
    private lateinit var cladsGoogleSignInClient: GoogleSignInClient
    private var GOOGLE_SIGN_IN_REQ_CODE = 100
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*Inflate the layout for this fragment*/
        _binding = SignUpOptionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailSignUpButton = binding.signUpOptionsFragmentSignUpWithEmailButton
        googleSignUpButton = binding.signUpOptionsFragmentCladsSignUpWithGoogleButton
        loginButton = binding.signUpOptionsFragmentLoginTextView

        emailSignUpButton.setOnClickListener {
            findNavController().navigate(R.id.email_sign_up_fragment)
        }

        /*call the googleSignInClient method*/
        googleSignInClient()

        emailSignUpButton.setOnClickListener {
            findNavController().navigate(R.id.email_sign_up_fragment)
        }

        /*call the googleSignInClient method*/
        googleSignInClient()

        /*add a listener to the sign in button*/
        googleSignUpButton.setOnClickListener {
            signIn()
        }
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.login_fragment)
        }
    }

    private fun googleSignInClient() {
        /*create the google sign in client*/
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        cladsGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
    }

    /*launch the login screen*/
    private fun signIn() {
        cladsGoogleSignInClient.signOut()
        val signInIntent = cladsGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQ_CODE)
    }

    /*gets the result of successful authentication*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignUpResult(task)
        }
    }

    /*handles the result of successful signUp with google*/
    private fun handleSignUpResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            loadEmailSignUpFragment(account)
        } catch (e: ApiException) {
            loadEmailSignUpFragment(null)
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    /*load the emailSignUpFragment*/
    private fun loadEmailSignUpFragment(account: GoogleSignInAccount?) {
        if (account != null) {
            findNavController().navigate(R.id.email_sign_up_fragment)
        }
    }

    /*remove the binding from the view to prevent memory leak*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
