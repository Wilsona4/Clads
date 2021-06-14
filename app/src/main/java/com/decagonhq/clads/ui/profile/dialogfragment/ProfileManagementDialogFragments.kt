package com.decagonhq.clads.ui.profile.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AccountEmployeeNumberDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountFirstNameDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountLastNameDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountOtherNameDialogFragmentBinding
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_EMPLOYEE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_EMPLOYEE_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_FIRST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_FIRST_NAME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_LAST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_LAST_NAME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_OTHER_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_OTHER_NAME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_LAST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY

class ProfileManagementDialogFragments(
    private var dialogLayoutId: Int,
    private var bundle: Bundle? = null
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(dialogLayoutId, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Inflate Dialog Fragment Layouts based on Id*/
        when (dialogLayoutId) {
            R.layout.account_employee_number_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountEmployeeNumberDialogFragmentBinding.bind(view)
                val employeeNumberEditText =
                    binding.accountEmployeeNumberDialogFragmentEmployeeNumberEditTextView
                val okButton = binding.accountEmployeeNumberDialogFragmentOkButton
                val cancelButton = binding.accountEmployeeNumberDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY)

                /*Attaching the data*/
                employeeNumberEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        employeeNumberEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountEmployeeNumberDialogFragmentEmployeeNumberEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountEmployeeNumberDialogFragmentEmployeeNumberEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_EMPLOYEE_REQUEST_KEY, bundleOf(
                                    ACCOUNT_EMPLOYEE_BUNDLE_KEY to inputValue
                                )
                            )
                            dismiss()
                        }
                    }
                }
                /*when the dialog cancel button is clicked*/
                cancelButton.setOnClickListener {
                    dismiss()
                }

                /*Validate Dialog Fields onTextChange*/
                employeeNumberEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        employeeNumberEditText.text!!.trim().isEmpty() -> {
                            binding.accountEmployeeNumberDialogFragmentEmployeeNumberEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountEmployeeNumberDialogFragmentEmployeeNumberEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountEmployeeNumberDialogFragmentEmployeeNumberEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_first_name_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountFirstNameDialogFragmentBinding.bind(view)
                val firstNameEditText =
                    binding.accountFirstNameDialogFragmentFirstNameEditTextView
                val okButton = binding.accountFirstNameDialogFragmentOkButton
                val cancelButton = binding.accountFirstNameDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY)

                /*Attaching the data*/
                firstNameEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        firstNameEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountFirstNameDialogFragmentFirstNameEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountFirstNameDialogFragmentFirstNameEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_FIRST_NAME_REQUEST_KEY, bundleOf(
                                    ACCOUNT_FIRST_NAME_BUNDLE_KEY to inputValue
                                )
                            )
                            dismiss()
                        }
                    }
                }
                /*when the dialog cancel button is clicked*/
                cancelButton.setOnClickListener {
                    dismiss()
                }

                /*Validate Dialog Fields onTextChange*/
                firstNameEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        firstNameEditText.text!!.trim().isEmpty() -> {
                            binding.accountFirstNameDialogFragmentFirstNameEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountFirstNameDialogFragmentFirstNameEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountFirstNameDialogFragmentFirstNameEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_last_name_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountLastNameDialogFragmentBinding.bind(view)
                val lastNameEditText =
                    binding.accountLastNameDialogFragmentLastNameEditTextView
                val okButton = binding.accountLastNameDialogFragmentOkButton
                val cancelButton = binding.accountLastNameDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_LAST_NAME_BUNDLE_KEY)

                /*Attaching the data*/
                lastNameEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        lastNameEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountLastNameDialogFragmentLastNameEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountLastNameDialogFragmentLastNameEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_LAST_NAME_REQUEST_KEY, bundleOf(
                                    ACCOUNT_LAST_NAME_BUNDLE_KEY to inputValue
                                )
                            )
                            dismiss()
                        }
                    }
                }
                /*when the dialog cancel button is clicked*/
                cancelButton.setOnClickListener {
                    dismiss()
                }

                /*Validate Dialog Fields onTextChange*/
                lastNameEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        lastNameEditText.text!!.trim().isEmpty() -> {
                            binding.accountLastNameDialogFragmentLastNameEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountLastNameDialogFragmentLastNameEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountLastNameDialogFragmentLastNameEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_other_name_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountOtherNameDialogFragmentBinding.bind(view)
                val otherNameEditText =
                    binding.accountOtherNameDialogFragmentOtherNameEditTextView
                val okButton = binding.accountOtherNameDialogFragmentOkButton
                val cancelButton = binding.accountOtherNameDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY)

                /*Attaching the data*/
                otherNameEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        otherNameEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            setFragmentResult(
                                ACCOUNT_OTHER_NAME_REQUEST_KEY, bundleOf(
                                    ACCOUNT_OTHER_NAME_BUNDLE_KEY to null
                                )
                            )
                            dismiss()
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_OTHER_NAME_REQUEST_KEY, bundleOf(
                                    ACCOUNT_OTHER_NAME_BUNDLE_KEY to inputValue
                                )
                            )
                            dismiss()
                        }
                    }
                }
                /*when the dialog cancel button is clicked*/
                cancelButton.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    companion object {

        fun createProfileDialogFragment(
            layoutId: Int,
            bundle: Bundle? = null
        ): ProfileManagementDialogFragments {
            return ProfileManagementDialogFragments(layoutId, bundle)
        }
    }
}