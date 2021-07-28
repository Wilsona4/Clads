package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.Locale

class ClientAccountFragment : BaseFragment() {
    private var _binding: ClientAccountFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val backingFieldViewModel: ClientsRegisterViewModel by activityViewModels()

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var phoneNumber: String
    private lateinit var email: String
    private lateinit var selectedGender: String

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
        init()
        setClientFieldsOnTextChange()
    }

    fun saveToViewModel(): Boolean {
        var isSaved = false

        if (isAdded && isVisible) {
            if (validateInputs()) {
                backingFieldViewModel.setClient(
                    client = ClientReg(
                        fullName = "${firstName.capitalize(Locale.ROOT)} ${
                        lastName.capitalize(
                            Locale.ROOT
                        )
                        }",
                        email = email,
                        phoneNumber = phoneNumber,
                        gender = selectedGender
                    )
                )
                isSaved = true
            }
        }
        return isSaved
    }

    /*Method to Validate All Fields*/
    private fun setClientFieldsOnTextChange(): Boolean {
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

        lastNameEditText.doOnTextChanged { _, _, _, _ ->
            when {
                lastNameEditText.text.toString().trim().isEmpty() -> {
                    binding.clientAccountFragmentClientLastNameInputLayout.error =
                        getString(R.string.all_please_enter_last_name)
                    isValidated = false
                }
                else -> {
                    binding.clientAccountFragmentClientLastNameInputLayout.error = null
                    isValidated = true
                }
            }
        }

        phoneNumberEditText.doOnTextChanged { _, _, _, _ ->
            when {
                phoneNumberEditText.text.toString().trim().isEmpty() -> {
                    binding.clientAccountFragmentPhoneNumberInputLayout.error =
                        getString(R.string.all_please_enter_phone_number)
                    isValidated = false
                }
                !binding.clientAccountFragmentClientPhoneNumberInput.jdValidatePhoneNumber(
                    phoneNumberEditText.text.toString().trim()
                ) -> {
                    binding.clientAccountFragmentPhoneNumberInputLayout.error =
                        getString(R.string.invalid_phone_number)
                    isValidated = false
                }
                else -> {
                    binding.clientAccountFragmentPhoneNumberInputLayout.error = null
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

    private fun validateInputs(): Boolean {
        var isValidated = true
        firstName = firstNameEditText.text.toString().trim()
        lastName = lastNameEditText.text.toString().trim()
        phoneNumber = phoneNumberEditText.text.toString().trim()
        email = emailEditText.text.toString().trim()
        selectedGender =
            _binding?.root?.findViewById<RadioButton>(genderRadioGroup.checkedRadioButtonId)?.text.toString()

        when {
            !ValidationObject.validateName(firstName) -> {
                binding.clientAccountFragmentClientFirstNameInputLayout.error =
                    getString(R.string.all_please_enter_first_name)
                isValidated = false
            }

            !ValidationObject.validateName(lastName) -> {
                binding.clientAccountFragmentClientLastNameInputLayout.error =
                    getString(R.string.all_please_enter_last_name)
                isValidated = false
            }

            !binding.clientAccountFragmentClientPhoneNumberInput.jdValidatePhoneNumber(
                phoneNumber
            ) -> {
                binding.clientAccountFragmentPhoneNumberInputLayout.error =
                    getString(R.string.invalid_phone_number)
                isValidated = false
            }

            !ValidationObject.validateEmail(email) -> {
                binding.clientAccountFragmentEmailAddressInputLayout.error =
                    getString(R.string.all_invalid_email)
                isValidated = false
            }
        }

        return isValidated
    }

    private fun init() {

        firstNameEditText = binding.clientAccountFragmentClientFirstNameInput
        lastNameEditText = binding.clientAccountFragmentClientLastNameInput
        phoneNumberEditText = binding.clientAccountFragmentClientPhoneNumberInput
        emailEditText = binding.clientAccountFragmentClientEmailInput
        genderRadioGroup = binding.clientFragmentAccountTabRadioGroup
    }
}
