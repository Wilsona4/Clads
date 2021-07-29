package com.decagonhq.clads.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.databinding.SignUpOptionsFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.profile.DashboardActivity
import com.decagonhq.clads.util.Constants
import com.decagonhq.clads.util.Constants.GOOGLE_SIGN_IN_REQUEST_CODE
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.util.navigateTo
import com.decagonhq.clads.viewmodels.AuthenticationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpOptionsFragment : BaseFragment() {
    private var _binding: SignUpOptionsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var emailSignUpButton: TextView
    private lateinit var googleSignUpButton: TextView
    private lateinit var loginButton: TextView
    private lateinit var googleSignInClient: GoogleSignInClient
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()

        if (sessionManager.loadFromSharedPref(getString(R.string.login_status)) ==
            getString(R.string.logout) &&
            sessionManager.loadFromSharedPref(getString(R.string.login_status)).isNotEmpty()
        ) {
            val action =
                SignUpOptionsFragmentDirections.actionSignUpOptionsFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            val action =
                SignUpOptionsFragmentDirections.actionSignUpOptionsFragmentToEmailSignUpFragment()
            findNavController().navigate(action)
        }

        /*call the googleSignInClient method*/
        googleSignInClient()

        /*add a listener to the sign in button*/
        googleSignUpButton.setOnClickListener {
            signIn()
        }

        loginButton.setOnClickListener {
            navigateTo(R.id.login_fragment)
        }
    }

    private fun googleSignInClient() {
        /*create the google sign in client*/
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
    }

    /*Launch the login screen*/
    private fun signIn() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    /*Gets the result of successful authentication*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignUpResult(task)
        }
    }

    /*handles the result of successful signUp with google*/
    private fun handleSignUpResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            loadDashBoardFragment(account)
        } catch (e: ApiException) {
            showToast(e.localizedMessage)
        }
    }

    /*open the dashboard fragment if account was selected*/
    private fun loadDashBoardFragment(account: GoogleSignInAccount?) {
        if (account != null) {
            account.idToken.let {
                if (it != null) {
                    sessionManager.saveToSharedPref(Constants.TOKEN, it)
                }
            }

            authenticationViewModel.loginUserWithGoogle(
                UserRole("Tailor")
            )
            /*Handling the response from the retrofit*/
            authenticationViewModel.loginUserWithGoogle.observe(
                viewLifecycleOwner,
                Observer {
                    when (it) {
                        is Resource.Success -> {
                            val successResponse = it.data?.payload
                            if (successResponse != null) {
                                sessionManager.saveToSharedPref(Constants.TOKEN, successResponse)
                            }

                            progressDialog.hideProgressDialog()
                            val intent = Intent(requireContext(), DashboardActivity::class.java)

                            startActivity(intent)
                            activity?.finish()
                        }
                        is Resource.Error -> {
                            progressDialog.hideProgressDialog()
                            handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                        }
                        is Resource.Loading -> {
                            progressDialog.showDialogFragment(getString(R.string.please_wait))
                        }
                    }
                }
            )
        }
    }

    /*remove the binding from the view to prevent memory leak*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
