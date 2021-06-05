package com.decagonhq.clads.ui.auth

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
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.LoginFragmentBinding
import com.decagonhq.clads.util.CustomTypefaceSpan
import com.decagonhq.clads.util.ValidationObject.validateEmail
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {
    // Binding
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var newUserSignUpForFree: TextView
    private lateinit var forgetPasswordButton: TextView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

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

        newUserSignUpForFreeSpannable()

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
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }
        }

        forgetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.forgot_password_fragment)
        }
    }

    override fun onResume() {
        super.onResume()
        /*Method to Validate Email Field onText Change*/
        validateSignUpFieldsOnTextChange()
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
        var message = getString(R.string.new_user_sign_up_for_free)
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
