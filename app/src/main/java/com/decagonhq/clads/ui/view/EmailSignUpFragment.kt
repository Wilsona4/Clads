package com.decagonhq.clads.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        /*Initialize User Inputs*/
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val otherName = otherNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val accountCategory = accountCategoryDropDown.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        /*Validate Email Sign Up*/
        signUpButton.setOnClickListener {

            when {
                firstName.isEmpty() -> {
                    firstNameEditText.error = "Please Enter First Name"
                    return@setOnClickListener
                }
                email.isEmpty() -> {
                    emailEditText.error = "Email Can't Be Empty"
                    return@setOnClickListener
                }
                !validateEmail(email) -> {
                    emailEditText.error = "Invalid Email"
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    passwordEditText.error = "Password Can't Be Empty"
                    return@setOnClickListener
                }
                confirmPassword.isEmpty() -> {
                    confirmPasswordEditText.error = "Field Can't Be Empty"
                    return@setOnClickListener
                }
                !validateAccountCategory(accountCategory) -> {
                    accountCategoryDropDown.error = "Select Account Type"
                    return@setOnClickListener
                }
                !validatePasswordMismatch(password, confirmPassword) -> {
                    confirmPasswordEditText.error = "Password Mismatch"
                    return@setOnClickListener
                }
                else -> {
                    val action =
                        EmailSignUpFragmentDirections.actionEmailSignUpFragmentToEmailConfirmationFragment(
                            accountCategory
                        )
                    findNavController().navigate(action)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
