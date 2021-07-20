package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.client.ClientReg
import com.decagonhq.clads.databinding.ClientAccountFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.util.ValidationObject
import com.decagonhq.clads.util.ValidationObject.jdValidatePhoneNumber
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel
import com.google.android.material.tabs.TabLayout


class ClientAccountFragment : BaseFragment() {
    private var _binding: ClientAccountFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val clientsRegisterViewModel: ClientsRegisterViewModel by activityViewModels()

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var nextButton: Button
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ClientAccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize views
        firstNameEditText = binding.clientAccountFragmentClientFirstNameInput
        lastNameEditText = binding.clientAccountFragmentClientLastNameInput
        phoneNumberEditText = binding.clientAccountFragmentClientPhoneNumberInput
        emailEditText = binding.clientAccountFragmentClientEmailInput
        genderRadioGroup = binding.clientFragmentAccountTabRadioGroup
        nextButton = binding.clientAccountTabNextButton


//        clientsRegisterViewModel.clientRegData.observe(
//            viewLifecycleOwner,
//            Observer {
//                when (it) {
//                    is Resource.Success -> {
//                        it.data?.payload?.let {
//                            //clientsRegisterViewModel.saveClientToLocalDatabase()
//                        }
//
//                        Toast.makeText(
//                            requireContext(),
//                            "Client added successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        findNavController().navigate(R.id.action_emailSignUpFragment_to_emailConfirmationFragment)
//                    }
//                    is Resource.Error -> {
//                        progressDialog.hideProgressDialog()
//                        handleApiError(it, mainRetrofit, requireView())
//                    }
//                    is Resource.Loading -> {
//                        it.message?.let { message ->
//                            progressDialog.showDialogFragment(message)
//                        }
//                    }
//
//                }
//            }
//        )

        /*Validate Next button*/
        nextButton.setOnClickListener {

            /*Initialize User Inputs*/
            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val phoneNumber = phoneNumberEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val gender = genderRadioGroup
            val selectedGender = _binding?.root?.findViewById<RadioButton>(genderRadioGroup.checkedRadioButtonId)?.text.toString()

            when {
                firstName.isEmpty() -> {
                    binding.clientAccountFragmentClientFirstNameInputLayout.error =
                        getString(R.string.all_please_enter_first_name)
                    return@setOnClickListener
                }

                email.isEmpty() -> {
                    binding.clientAccountFragmentEmailAddressInputLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    return@setOnClickListener
                }
                !binding.clientAccountFragmentClientPhoneNumberInput.jdValidatePhoneNumber(
                    phoneNumber
                ) -> {
                    binding.clientAccountFragmentPhoneNumberInputLayout.error =
                        getString(R.string.invalid_phone_number)
                    return@setOnClickListener
                }
                phoneNumber.isEmpty() -> {
                    binding.clientAccountFragmentPhoneNumberInputLayout.error =
                        getString(R.string.all_phone_number_is_required)
                    return@setOnClickListener
                }
                !ValidationObject.validateEmail(email) -> {
                    binding.clientAccountFragmentEmailAddressInputLayout.error =
                        getString(R.string.all_invalid_email)
                    return@setOnClickListener
                }
                gender.checkedRadioButtonId == -1 -> {
                    //display msg
                    //todo display select gender
                    return@setOnClickListener
                }

                else -> {
                    if (validateClientFieldsOnTextChange()) {
//                        val newClient = Client(
//                            fullName = "$firstName $lastName",
//                            email = email,
//                            phoneNumber = phoneNumber,
//                            gender = selectedGender,
//                            measurements = arrayListOf(Measurement(
//                                //title = binding.clientAccountFragmentTitle.text.toString(),
//                                title = "top",
//                                value = 20
//                            )),
//                            deliveryAddresses = arrayListOf(DeliveryAddress(
//                                street = "2 Lagos street",
//                                city= "Lagos",
//                                state = "State"
//                            ))
//
//                        )
                        //clientsRegisterViewModel.registerClient(newClient)

                        val newClient = ClientReg(
                             fullName = "$firstName $lastName",
                             email = email,
                             phoneNumber = phoneNumber,
                             gender = selectedGender
                        )
                        clientsRegisterViewModel.setClient(newClient)

                        val tabLayout = activity?.findViewById(R.id.add_client_tab_layout) as TabLayout
                        tabLayout.getTabAt(1)?.select()

                    } else {
                        return@setOnClickListener
                    }
                }
            }
        }

    }

    /*Method to Validate All Fields*/
    private fun validateClientFieldsOnTextChange(): Boolean {
        var isValidated = true

        firstNameEditText.doOnTextChanged { _, _, _, _ ->
            when {
                firstNameEditText.text.toString().trim().isEmpty() -> {
                    binding.clientAccountFragmentClientFirstNameInputLayout.error =
                        getString(R.string.all_please_enter_first_name)
                    isValidated = false
                }
                else -> {
                    binding.clientAccountFragmentClientFirstNameInputLayout.error = null
                    isValidated = true
                }
            }
        }

        emailEditText.doOnTextChanged { _, _, _, _ ->
            when {
                emailEditText.text.toString().trim().isEmpty() -> {
                    binding.clientAccountFragmentEmailAddressInputLayout.error =
                        getString(R.string.all_email_cant_be_empty)
                    isValidated = false
                }
                !ValidationObject.validateEmail(emailEditText.text.toString().trim()) -> {
                    binding.clientAccountFragmentEmailAddressInputLayout.error =
                        getString(R.string.all_invalid_email)
                    isValidated = false
                }
                else -> {
                    binding.clientAccountFragmentEmailAddressInputLayout.error = null
                    isValidated = true
                }
            }
        }



        return isValidated
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
