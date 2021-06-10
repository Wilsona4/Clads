package com.decagonhq.clads.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.EmailSignUpFragmentBinding
import com.decagonhq.clads.util.ValidationObject.validateAccountCategory
import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EmailSignUpFragment : Fragment() {
    private lateinit var cladsGoogleSignInClient: GoogleSignInClient
    private var _binding: EmailSignUpFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var otherNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var accountCategoryDropDown: AutoCompleteTextView
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var signUpButton: MaterialButton
    private lateinit var loginButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = EmailSignUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Initialize Views*/
        firstNameEditText = binding.emailSignUpFragmentFirstNameEditText
        lastNameEditText = binding.emailSignUpFragmentLastNameEditText
        otherNameEditText = binding.emailSignUpFragmentOtherNameEditText
        emailEditText = binding.emailSignUpFragmentEmailEditText
        accountCategoryDropDown = binding.emailSignUpFragmentAccountCategoryTextView
        passwordEditText = binding.emailSignUpFragmentPasswordEditText
        confirmPasswordEditText = binding.emailSignUpFragmentConfirmPasswordEditText
        signUpButton = binding.emailSignUpFragmentSignupButton
        loginButton = binding.emailSignUpFragmentLoginTextView

        getUserRemoteData()

        loginButton.setOnClickListener {
            findNavController().navigate(R.id.login_fragment)
        }

        /*Validate Email Sign Up*/
        signUpButton.setOnClickListener {

            /*Initialize User Inputs*/
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val otherName = otherNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val accountCategory = accountCategoryDropDown.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            when {
                firstName.isEmpty() -> {
                    binding.emailSignUpFragmentFirstNameEditTextLayout.error =
                        getString(R.string.all_please_enter_first_name)
                    return@setOnClickListener
                }
                !validateAccountCategory(accountCategory) -> {
                    binding.emailSignUpFragmentAccountCategoryTextLayout.error =
                        getString(R.string.all_select_account_type)
                    binding.emailSignUpFragmentAccountCategoryTextLayout.errorIconDrawable = null
                    return@setOnClickListener
                }
                email.isEmpty() -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    return@setOnClickListener
                }
                !validateEmail(email) -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error =
                        getString(R.string.all_invalid_email)
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.emailSignUpFragmentPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.emailSignUpFragmentPasswordEditTextLayout.errorIconDrawable = null
                    return@setOnClickListener
                }
                confirmPassword.isEmpty() -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.errorIconDrawable =
                        null
                    return@setOnClickListener
                }
                !validatePasswordMismatch(password, confirmPassword) -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        getString(R.string.all_password_mismatch)
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.errorIconDrawable =
                        null
                    return@setOnClickListener
                }
                else -> {
                    if (validateSignUpFieldsOnTextChange()) {
                        findNavController().navigate(R.id.email_confirmation_fragment)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /*Set up Account Category Dropdown*/
        val accountCategories = resources.getStringArray(R.array.account_category)
        val accountCategoriesArrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.account_category_dropdown_item,
            accountCategories
        )
        accountCategoryDropDown.setAdapter(accountCategoriesArrayAdapter)

        /*Method to Validate All Sign Up Fields*/
        validateSignUpFieldsOnTextChange()
    }

    /*Method to Validate All Sign Up Fields*/
    private fun validateSignUpFieldsOnTextChange(): Boolean {
        var isValidated = true

        firstNameEditText.doOnTextChanged { _, _, _, _ ->
            when {
                firstNameEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentFirstNameEditTextLayout.error =
                        getString(R.string.all_please_enter_first_name)
                    isValidated = false
                }
                else -> {
                    binding.emailSignUpFragmentFirstNameEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        emailEditText.doOnTextChanged { _, _, _, _ ->
            when {
                emailEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    isValidated = false
                }
                !validateEmail(emailEditText.text.toString().trim()) -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error =
                        getString(R.string.all_invalid_email)
                    isValidated = false
                }
                else -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        passwordEditText.doOnTextChanged { _, _, _, _ ->
            when {
                passwordEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.emailSignUpFragmentPasswordEditTextLayout.errorIconDrawable = null
                    isValidated = false
                }
                else -> {
                    binding.emailSignUpFragmentPasswordEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        confirmPasswordEditText.doOnTextChanged { _, _, _, _ ->
            when {
                confirmPasswordEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        getString(R.string.all_password_is_required)
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.errorIconDrawable =
                        null
                    isValidated = false
                }
                !validatePasswordMismatch(
                    passwordEditText.text.toString().trim(),
                    confirmPasswordEditText.text.toString().trim()
                ) -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        getString(R.string.all_password_mismatch)
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.errorIconDrawable =
                        null
                    isValidated = false
                }
                else -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        accountCategoryDropDown.doOnTextChanged { _, _, _, _ ->
            if (!validateAccountCategory(binding.emailSignUpFragmentAccountCategoryTextView.text.toString())) {
                binding.emailSignUpFragmentAccountCategoryTextLayout.error =
                    getString(R.string.all_select_account_type)
                binding.emailSignUpFragmentAccountCategoryTextLayout.errorIconDrawable = null
                isValidated = false
            } else {
                binding.emailSignUpFragmentAccountCategoryTextLayout.error = null
                isValidated = true
            }
        }

        return isValidated
    }

    private fun getUserRemoteData() {

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        cladsGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

        val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())

        if (googleAccount != null) {
            val accountFirstName = googleAccount.givenName
            val accountLastName = googleAccount.familyName
            val accountEmail = googleAccount.email
            val token = googleAccount.idToken
            Log.d("Token", "$token")
            firstNameEditText.setText(accountFirstName)
            lastNameEditText.setText(accountLastName)
            emailEditText.setText(accountEmail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
