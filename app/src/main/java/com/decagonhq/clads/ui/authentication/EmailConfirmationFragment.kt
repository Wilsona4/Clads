package com.decagonhq.clads.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.EmailConfirmationFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.util.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment() {
    private var _binding: EmailConfirmationFragmentBinding? = null
    private val binding get() = _binding!!

//    @Inject
//    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = EmailConfirmationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Display user name*/
        val userName = sessionManager.loadFromSharedPref(getString(R.string.user_name))
        binding.emailConfirmationFragmentVerifyEmailTextTextView

        /*Navigate to Login Screen After Confirmation*/
        binding.emailConfirmationFragmentVerifyEmailAddressButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_APP_EMAIL)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
