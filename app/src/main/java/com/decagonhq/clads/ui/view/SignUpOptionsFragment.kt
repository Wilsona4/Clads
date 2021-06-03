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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpOptionsFragment : Fragment() {

    private var _binding: FragmentSignUpOptionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var cladsGoogleSignInClient: GoogleSignInClient
    private var GOOGLE_SIGN_IN_REQ_CODE = 100
    private lateinit var authentication: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*Inflate the layout for this fragment*/
        _binding = FragmentSignUpOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*create the google sign in client*/
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        cladsGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        /*Initialize Firebase Auth */
        authentication = Firebase.auth

        /*add a listener to the sign in button*/
//        binding.signInButton.setOnClickListener {
//            signIn()
//        }
    }

    /*launch the login screen*/
    private fun signIn() {
        val signInIntent = cladsGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQ_CODE)
    }

    /*gets the result of successful authentication*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(requireActivity(), e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*check for existing account on start*/
    override fun onStart() {
        super.onStart()
        /*Check if user is signed in (non-null) and update UI accordingly.*/
        val currentUser = authentication.currentUser

        if (currentUser != null) {
            findNavController().navigate(R.id.action_signUpOptionsFragment_to_emailSignUpFragment)
        }
    }

    // start auth with google
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        authentication.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    val user = authentication.currentUser
                    updateUI(user)
                    Toast.makeText(requireContext(), "User Signed In", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    /*update the user*/
    private fun updateUI(user: FirebaseUser?) {
        findNavController().navigate(R.id.action_signUpOptionsFragment_to_emailSignUpFragment)
    }

    /*remove the binding from the view to prevent memory leak*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
