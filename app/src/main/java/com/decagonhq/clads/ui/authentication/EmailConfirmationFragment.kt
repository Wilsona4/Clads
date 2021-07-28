package com.decagonhq.clads.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.EmailConfirmationFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.profile.DashboardActivity
import com.decagonhq.clads.util.Constants
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.viewmodels.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment() {
    private var _binding: EmailConfirmationFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthenticationViewModel by activityViewModels()
    private val args by navArgs<EmailConfirmationFragmentArgs>()

    //    @Inject
//    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            startActivity(intent)
            requireActivity().finish()
        }

        binding.emailConfirmationFragmentVerifyEmailTextTextView.text =
            getString(R.string.confirk_email_hi) + userName + getString(R.string.confirm_email_you_are_ready_to_go)
    }

    override fun onResume() {
        super.onResume()
        val activationToken = args.token

        if (activationToken != null) {
            viewModel.verifyAuthToken(activationToken)
            viewModel.authenticationToken.observe(
                viewLifecycleOwner,
                {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data?.payload != null) {
                                sessionManager.saveToSharedPref(Constants.TOKEN, activationToken)
                                sessionManager.saveToSharedPref(
                                    getString(R.string.login_status),
                                    getString(
                                        R.string.log_in
                                    )
                                )
                            }

                            progressDialog.hideProgressDialog()
                            Toast.makeText(requireContext(), "Email Verified Successfully", Toast.LENGTH_SHORT).show()
                            val intent =
                                Intent(requireContext(), DashboardActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        }

                        is Resource.Error -> {
                            progressDialog.hideProgressDialog()
                            handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                        }

                        is Resource.Loading -> {
                            progressDialog.showDialogFragment("Verifying Email")
                        }
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
