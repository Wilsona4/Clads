package com.decagonhq.clads.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
import com.decagonhq.clads.viewmodels.UserProfileViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import timber.log.Timber

@AndroidEntryPoint
class SignUpOptionsFragment : BaseFragment() {
    private var _binding: SignUpOptionsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var emailSignUpButton: TextView
    private lateinit var googleSignUpButton: TextView
    private lateinit var loginButton: TextView
    private lateinit var googleSignInClient: GoogleSignInClient
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels()
    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        if (
            sessionManager.loadFromSharedPref(getString(R.string.login_status)) ==
            getString(R.string.logout) &&
            sessionManager.loadFromSharedPref(getString(R.string.login_status)).isNotEmpty()
        ) {
            navigateTo(R.id.login_fragment)
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
            navigateTo(R.id.email_sign_up_fragment)
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
            Timber.d("onActivityResult: $task")
            handleSignUpResult(task)
        }
    }

    /*handles the result of successful signUp with google*/
    private fun handleSignUpResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Timber.w("Try block: $account")
//            val firstName = account?.givenName.toString()
//
//            val newRegisteredUser = UserRegistration(
//                firstName = firstName,
//                lastName = account?.familyName.toString(),
//                email = account?.email.toString(),
//                phoneNumber = "08104690512",
//                category = getString(R.string.tailor),
//                role = getString(R.string.tailor),
//                password = "password",
//                deliveryAddress = getString(R.string.null_all),
//                gender = getString(R.string.male),
//                country = getString(R.string.nigeria),
//                thumbnail = getString(R.string.null_all)
//            )
//            authenticationViewModel.registerUser(newRegisteredUser)

            loadDashBoardFragment(account)
        } catch (e: ApiException) {
            Timber.w("signInResult:failed code= ${e.statusCode}")
            Toast.makeText(
                requireContext(),
                "Cancelled: ${e.localizedMessage}", Toast.LENGTH_SHORT
            ).show()
        }
    }

    /*load the emailSignUpFragment*/
    private fun loadEmailSignUpFragment(account: GoogleSignInAccount?) {
        if (account == null || account.isExpired) {
            navigateTo(R.id.sign_up_options_fragment)
        } else {

            authenticationViewModel.userRegData.observe(
                viewLifecycleOwner,
                Observer {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.payload?.let {
                                userProfileViewModel.saveUserProfileToLocalDatabase()
                                Timber.d("Successfuull!!!")
                            }
                            progressDialog.hideProgressDialog()
                            sessionManager.saveToSharedPref(
                                getString(R.string.user_name),
                                account.givenName.toString().capitalize(Locale.ROOT)
                            )
                            Toast.makeText(
                                requireContext(),
                                "Registered successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController()
                                .navigate(R.id.action_sign_up_options_fragment_to_email_confirmation_fragment)
                        }
                        is Resource.Error -> {
                            progressDialog.hideProgressDialog()
                            handleApiError(it, mainRetrofit, requireView())
                        }
                        is Resource.Loading -> {
                            it.message?.let { message ->
                                progressDialog.showDialogFragment(message)
                            }
                        }
                    }
                }
            )

//            val dashboardIntent = Intent(requireContext(), DashboardActivity::class.java)
//            startActivity(dashboardIntent)
//            activity?.finish()
        }
    }

    /*open the dashboard fragment if account was selected*/
    private fun loadDashBoardFragment(account: GoogleSignInAccount?) {
        if (account != null) {

            account.idToken.let {
                if (it != null) {
                    sessionManager.saveToSharedPref(Constants.TOKEN, it)
                    Log.d("TOKEN", "loadDashBoardFragment: $it")
                }
            }

            authenticationViewModel.loginUserWithGoogle(
                UserRole(getString(R.string.tailor))
            )
            progressDialog.showDialogFragment(getString(R.string.please_wait))

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
                        }
                        is Resource.Loading -> {
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
