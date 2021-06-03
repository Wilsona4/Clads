package com.decagonhq.clads.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.EmailSignUpFragmentBinding
import com.decagonhq.clads.util.ValidationObject.validateAccountCategory
import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.decagonhq.clads.util.ValidationObject.validatePasswordMismatch
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EmailSignUpFragment : Fragment() {
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

        validateSignUpFieldsOnTextChange()

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
                        "Please Enter First Name"
                    return@setOnClickListener
                }
                !validateAccountCategory(accountCategory) -> {
                    binding.emailSignUpFragmentAccountCategoryTextLayout.error =
                        "Select Account Type"
                    binding.emailSignUpFragmentAccountCategoryTextLayout.errorIconDrawable = null
                    return@setOnClickListener
                }
                email.isEmpty() -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error = "Email Can't Be Empty"
                    return@setOnClickListener
                }
                !validateEmail(email) -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error = "Invalid Email"
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.emailSignUpFragmentPasswordEditTextLayout.error = "Password is Required"
                    binding.emailSignUpFragmentPasswordEditTextLayout.errorIconDrawable = null
                    return@setOnClickListener
                }
                confirmPassword.isEmpty() -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        "Password is Required"
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.errorIconDrawable =
                        null
                    return@setOnClickListener
                }
                !validatePasswordMismatch(password, confirmPassword) -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        "Password Mismatch"
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.errorIconDrawable =
                        null
                    return@setOnClickListener
                }
                else -> {
                    if (validateSignUpFieldsOnTextChange()) {
                        val action =
                            EmailSignUpFragmentDirections.actionEmailSignUpFragmentToEmailConfirmationFragment(
                                accountCategory
                            )
                        findNavController().navigate(action)
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
    }

    /*Method to Validate All Sign Up Fields*/
    private fun validateSignUpFieldsOnTextChange(): Boolean {
        var isValidated = true

        firstNameEditText.doOnTextChanged { text, start, before, count ->
            when {
                firstNameEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentFirstNameEditTextLayout.error =
                        "Please Enter First Name"
                    isValidated = false
                }
                else -> {
                    binding.emailSignUpFragmentFirstNameEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        emailEditText.doOnTextChanged { text, start, before, count ->
            when {
                emailEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error = "Email Can't Be Empty"
                    isValidated = false
                }
                !validateEmail(emailEditText.text.toString().trim()) -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error = "Invalid Email"
                    isValidated = false
                }
                else -> {
                    binding.emailSignUpFragmentEmailEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        passwordEditText.doOnTextChanged { text, start, before, count ->
            when {
                passwordEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentPasswordEditTextLayout.error = "Password is Required"
                    binding.emailSignUpFragmentPasswordEditTextLayout.errorIconDrawable = null
                    isValidated = false
                }
                else -> {
                    binding.emailSignUpFragmentPasswordEditTextLayout.error = null
                    isValidated = true
                }
            }
        }

        confirmPasswordEditText.doOnTextChanged { text, start, before, count ->
            when {
                confirmPasswordEditText.text.toString().trim().isEmpty() -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        "Password is Required"
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.errorIconDrawable =
                        null
                    isValidated = false
                }
                !validatePasswordMismatch(
                    passwordEditText.text.toString().trim(),
                    confirmPasswordEditText.text.toString().trim()
                ) -> {
                    binding.emailSignUpFragmentConfirmPasswordEditTextLayout.error =
                        "Password Mismatch"
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

        accountCategoryDropDown.doOnTextChanged { text, start, before, count ->
            if (!validateAccountCategory(binding.emailSignUpFragmentAccountCategoryTextView.text.toString())) {
                binding.emailSignUpFragmentAccountCategoryTextLayout.error = "Select Account Type"
                binding.emailSignUpFragmentAccountCategoryTextLayout.errorIconDrawable = null
                isValidated = false
            } else {
                binding.emailSignUpFragmentAccountCategoryTextLayout.error = null
                isValidated = true
            }
        }

        return isValidated
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
