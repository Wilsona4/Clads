package com.decagonhq.clads.ui.view

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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.FragmentLoginBinding
import com.decagonhq.clads.utils.ValidationObject

class LoginFragment : Fragment() {

    // Binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var newUserSignUpForFree: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // On login button pressed

        newUserSignUpForFree = binding.loginFragmentSignUpForFreeTextView

        new_user_sign_up_for_free_spannable()

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
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun new_user_sign_up_for_free_spannable() {
        val spannable = SpannableStringBuilder("New user?  Sign Up for free")
        val myTypeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.poppins_bold), Typeface.BOLD)
        spannable.setSpan(TypefaceSpan(myTypeface), 11, 27, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        val clickableSignUpForFree = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Sign up for free clicked", Toast.LENGTH_SHORT).show()
            }
        }
        spannable.setSpan(clickableSignUpForFree, 11, 27, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.WHITE), 11, 27, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        newUserSignUpForFree.text = spannable
        newUserSignUpForFree.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
