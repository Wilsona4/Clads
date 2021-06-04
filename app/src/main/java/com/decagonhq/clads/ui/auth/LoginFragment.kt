package com.decagonhq.clads.ui.auth

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.LoginFragmentBinding
import com.decagonhq.clads.util.ValidationObject

class LoginFragment : Fragment() {

    // Binding
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var newUserSignUpForFree: TextView
    private lateinit var forgetPasswordButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // On login button pressed

        newUserSignUpForFree = binding.loginFragmentSignUpForFreeTextView

        forgetPasswordButton = binding.loginFragmentForgetPasswordTextView

        newUserSignUpForFreeSpannable()

        binding.loginFragmentLogInButton.setOnClickListener {
            val emailAddress = binding.loginFragmentEmailAddressEditText.text.toString()
            val password = binding.loginFragmentPasswordEditText.text.toString()
            when {
                // Check if email is empty
                emailAddress.isEmpty() -> {
                    binding.loginFragmentEmailAddressEditText.error = "Please enter email"
                    return@setOnClickListener
                }
                // Check if the password is empty
                password.isEmpty() -> {
                    binding.loginFragmentPasswordEditText.error = "Please enter password"
                    return@setOnClickListener
                }
                // Check if the email is valid
                !ValidationObject.validateEmail(emailAddress) -> {
                    binding.loginFragmentEmailAddressEditText.error = "Invalid email"
                    return@setOnClickListener
                }
                else -> {
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }
        }

        forgetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun newUserSignUpForFreeSpannable() {
        val spannable = SpannableStringBuilder("New user?  Sign Up for free")
        val myTypeface = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.poppins_bold),
            Typeface.BOLD
        )
        spannable.setSpan(TypefaceSpan(myTypeface), 11, 27, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val clickableSignUpForFree = object : ClickableSpan() {
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.action_login_fragment_to_email_sign_up_fragment)
            }
        }
        spannable.setSpan(clickableSignUpForFree, 11, 27, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        spannable.setSpan(
            ForegroundColorSpan(Color.WHITE),
            11,
            27,
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
