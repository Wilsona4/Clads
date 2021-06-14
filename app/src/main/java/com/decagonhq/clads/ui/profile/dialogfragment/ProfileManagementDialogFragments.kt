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
import com.decagonhq.clads.ui.client.MeasurementsFragment
import com.decagonhq.clads.ui.client.model.DressMeasurementModel
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_EMPLOYEE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_EMPLOYEE_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY

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