package com.decagonhq.clads.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.LoginFragmentBinding
import com.decagonhq.clads.ui.profile.DashboardActivity
import com.decagonhq.clads.util.CustomTypefaceSpan
import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {
    // Binding
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var newUserSignUpForFree: TextView
    private lateinit var forgetPasswordButton: TextView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var googleSignInButton: Button
    private lateinit var cladsSignInClient: GoogleSignInClient
    private var GOOGLE_SIGNIN_RQ_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Initialize Views*/
        emailEditText = binding.loginFragmentEmailAddressEditText
        passwordEditText = binding.loginFragmentPasswordEditText
        newUserSignUpForFree = binding.loginFragmentSignUpForFreeTextView
        forgetPasswordButton = binding.loginFragmentForgetPasswordTextView
        googleSignInButton = binding.loginFragmentGoogleSignInButton

        newUserSignUpForFreeSpannable()
        googleSignInClient()

        // On login button pressed
        binding.loginFragmentLogInButton.setOnClickListener {
            val emailAddress = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            when {
                // Check if email is empty
                emailAddress.isEmpty() -> {
                    binding.loginFragmentEmailAddressEditTextLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    return@setOnClickListener
                }
                // Check if the password is empty
                password.isEmpty() -> {
                    binding.loginFragmentPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.loginFragmentPasswordEditTextLayout.errorIconDrawable = null
                    return@setOnClickListener
                }
                // Check if the email is valid
                !validateEmail(emailAddress) -> {
                    binding.loginFragmentEmailAddressEditTextLayout.error =
                        getString(R.string.all_invalid_email)
                    return@setOnClickListener
                }
                else -> {
//                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    val intent = Intent(requireContext(), DashboardActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }

        forgetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.forgot_password_fragment)
        }

        /*implement the googleSignInClient method*/
        googleSignInClient()

        googleSignInButton.setOnClickListener {
            signIn()
        }
    }

    override fun onResume() {
        super.onResume()
        /*Method to Validate Email Field onText Change*/
        validateSignUpFieldsOnTextChange()
    }

    /*create the googleSignIn client*/
    private fun googleSignInClient() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        cladsSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
    }

    /*launch the signIn with google dialog*/
    private fun signIn() {
        cladsSignInClient.signOut()
        val signInIntent = cladsSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGNIN_RQ_CODE)
    }

    /*gets the selected google account from the intent*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGNIN_RQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    /*handles the result of successful sign in*/
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            loadDashBoardFragment(account)
        } catch (e: ApiException) {
            loadDashBoardFragment(null)
        }
    }

    /*open the dashboard fragment if account was selected*/
    private fun loadDashBoardFragment(account: GoogleSignInAccount?) {
        if (account != null) {
            val intent = Intent(requireContext(), DashboardActivity::class.java)
            startActivity(intent)
        }
    }

    /*Method to Validate All Login In Fields*/
    private fun validateSignUpFieldsOnTextChange(): Boolean {
        var isValidated = true

        emailEditText.doOnTextChanged { _, _, _, _ ->
            when {
                emailEditText.text.toString().trim().isEmpty() -> {
                    binding.loginFragmentEmailAddressEditTextLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    isValidated = false
                }
                !validateEmail(emailEditText.text.toString().trim()) -> {
                    binding.loginFragmentEmailAddressEditTextLayout.error =
                        getString(R.string.all_invalid_email)
                    isValidated = false
                }
                else -> {
                    binding.loginFragmentEmailAddressEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        passwordEditText.doOnTextChanged { _, _, _, _ ->
            when {
                passwordEditText.text.toString().trim().isEmpty() -> {
                    binding.loginFragmentPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.loginFragmentPasswordEditTextLayout.errorIconDrawable = null
                    isValidated = false
                }
                else -> {
                    binding.loginFragmentPasswordEditTextLayout.error = null
                    isValidated = true
                }
            }
        }
        return isValidated
    }

    private fun newUserSignUpForFreeSpannable() {
        val message = getString(R.string.new_user_sign_up_for_free)
        val spannable = SpannableStringBuilder(message)
        val myTypeface = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.poppins_bold),
            Typeface.BOLD
        )
        spannable.setSpan(
            CustomTypefaceSpan(myTypeface),
            10,
            message.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        val clickableSignUpForFree = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.email_sign_up_fragment)
            }
        }
        spannable.setSpan(
            clickableSignUpForFree,
            10,
            message.length,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.WHITE),
            10,
            message.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        newUserSignUpForFree.text = spannable
        newUserSignUpForFree.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}