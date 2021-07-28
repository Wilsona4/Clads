package com.decagonhq.clads.ui.profile.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AccountEmployeeNumberDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountFirstNameDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountGenderDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountLastNameDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountLegalStatusDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountPhoneNumberDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountShowroomAddressDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountUnionLgaDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountUnionNameDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountUnionStateDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountUnionWardDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountWorkshopCityDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountWorkshopStateDialogFragmentBinding
import com.decagonhq.clads.databinding.AccountWorkshopStreetDialogFragmentBinding
import com.decagonhq.clads.databinding.RenameGalleryImageDialogFragmentBinding
import com.decagonhq.clads.databinding.SpecialtyAddSpecialtyDialogFragmentBinding
import com.decagonhq.clads.databinding.SpecialtyDeliveryTimeDialogFragmentBinding
import com.decagonhq.clads.databinding.SpecialtyObiomaTrainedDialogFragmentBinding
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_EMPLOYEE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_EMPLOYEE_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_FIRST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_FIRST_NAME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_GENDER_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_GENDER_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_LAST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_LAST_NAME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_LEGAL_STATUS_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_LEGAL_STATUS_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_OTHER_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_OTHER_NAME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_SHOWROOM_ADDRESS_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_LGA_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_LGA_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_NAME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_STATE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_STATE_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_WARD_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_UNION_WARD_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_WORKSHOP_CITY_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_WORKSHOP_STATE_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.ACCOUNT_WORKSHOP_STREET_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_GENDER_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_LAST_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_LEGAL_STATUS_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_RENAME_DESCRIPTION_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_STATE_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_UNION_LGA_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_UNION_NAME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_UNION_WARD_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.CURRENT_ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.RENAME_DESCRIPTION_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment.Companion.RENAME_DESCRIPTION_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.ADD_NEW_SPECIALTY_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.ADD_NEW_SPECIALTY_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.CURRENT_SPECIAL_DELIVERY_TIME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.CURRENT_SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.SPECIAL_DELIVERY_TIME_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.SPECIAL_DELIVERY_TIME_REQUEST_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY
import com.decagonhq.clads.ui.profile.editprofile.SpecialtyFragment.Companion.SPECIAL_OBIOMA_TRAINED_REQUEST_KEY

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

                val retrievedArgs = bundle?.getString(CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY)

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
                                ACCOUNT_EMPLOYEE_REQUEST_KEY,
                                bundleOf(
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
                                ACCOUNT_FIRST_NAME_REQUEST_KEY,
                                bundleOf(
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
                                ACCOUNT_LAST_NAME_REQUEST_KEY,
                                bundleOf(
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
            R.layout.account_phone_number_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountPhoneNumberDialogFragmentBinding.bind(view)
                val phoneNumberEditText =
                    binding.accountPhoneNumberDialogFragmentOtherNameEditTextView
                val okButton = binding.accountPhoneNumberDialogFragmentOkButton
                val cancelButton = binding.accountPhoneNumberDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY)

                /*Attaching the data*/
                phoneNumberEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        phoneNumberEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            setFragmentResult(
                                ACCOUNT_OTHER_NAME_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_OTHER_NAME_BUNDLE_KEY to null
                                )
                            )
                            dismiss()
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_OTHER_NAME_REQUEST_KEY,
                                bundleOf(
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
                /*Validate Dialog Fields onTextChange*/
                phoneNumberEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        phoneNumberEditText.text!!.trim().isEmpty() -> {
                            binding.accountPhoneNumberDialogFragmentOtherNameEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountPhoneNumberDialogFragmentOtherNameEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountPhoneNumberDialogFragmentOtherNameEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_gender_dialog_fragment -> {

                /*Initialise binding*/
                val binding = AccountGenderDialogFragmentBinding.bind(view)

                val okButton = binding.accountGenderDialogFragmentOkButton
                val cancelButton = binding.accountGenderDialogFragmentCancelButton
                val radioGroup = binding.accountGenderDialogFragmentRadioGroup

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_GENDER_BUNDLE_KEY)

                /*Attaching the data*/
                if (retrievedArgs == "Male") {
                    radioGroup.check(R.id.account_gender_dialog_fragment_male_radio_button)
                } else {
                    radioGroup.check(R.id.account_gender_dialog_fragment_female_radio_button)
                }

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val selectedID = radioGroup.checkedRadioButtonId
                    val inputValue =
                        view.findViewById<RadioButton>(selectedID).text.toString()

                    setFragmentResult(
                        ACCOUNT_GENDER_REQUEST_KEY,
                        bundleOf(
                            ACCOUNT_GENDER_BUNDLE_KEY to inputValue
                        )
                    )
                    dismiss()
                }
                /*when the dialog cancel button is clicked*/
                cancelButton.setOnClickListener {
                    dismiss()
                }
            }
            R.layout.account_workshop_state_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountWorkshopStateDialogFragmentBinding.bind(view)
                val stateEditText =
                    binding.accountWorkshopStateDialogFragmentWorkshopStateEditTextView
                val okButton = binding.accountWorkshopStateDialogFragmentOkButton
                val cancelButton = binding.accountWorkshopStateDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY)

                /*Attaching the data*/
                stateEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        stateEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountWorkshopStateDialogFragmentWorkshopStateEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountWorkshopStateDialogFragmentWorkshopStateEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_WORKSHOP_STATE_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY to inputValue
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
                stateEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        stateEditText.text!!.trim().isEmpty() -> {
                            binding.accountWorkshopStateDialogFragmentWorkshopStateEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountWorkshopStateDialogFragmentWorkshopStateEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountWorkshopStateDialogFragmentWorkshopStateEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_workshop_city_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountWorkshopCityDialogFragmentBinding.bind(view)
                val cityEditText =
                    binding.accountWorkshopCityDialogFragmentWorkshopCityEditTextView
                val okButton = binding.accountWorkshopCityDialogFragmentOkButton
                val cancelButton = binding.accountWorkshopCityDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY)

                /*Attaching the data*/
                cityEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        cityEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountWorkshopCityDialogFragmentWorkshopCityEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountWorkshopCityDialogFragmentWorkshopCityEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_WORKSHOP_CITY_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY to inputValue
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
                cityEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        cityEditText.text!!.trim().isEmpty() -> {
                            binding.accountWorkshopCityDialogFragmentWorkshopCityEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountWorkshopCityDialogFragmentWorkshopCityEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountWorkshopCityDialogFragmentWorkshopCityEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_workshop_street_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountWorkshopStreetDialogFragmentBinding.bind(view)
                val streetEditText =
                    binding.accountWorkshopStreetDialogFragmentWorkshopStreetEditTextView
                val okButton = binding.accountWorkshopStreetDialogFragmentOkButton
                val cancelButton = binding.accountWorkshopStreetDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY)

                /*Attaching the data*/
                streetEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        streetEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountWorkshopStreetDialogFragmentWorkshopStreetEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountWorkshopStreetDialogFragmentWorkshopStreetEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_WORKSHOP_STREET_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY to inputValue
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
                streetEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        streetEditText.text!!.trim().isEmpty() -> {
                            binding.accountWorkshopStreetDialogFragmentWorkshopStreetEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountWorkshopStreetDialogFragmentWorkshopStreetEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountWorkshopStreetDialogFragmentWorkshopStreetEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_showroom_address_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountShowroomAddressDialogFragmentBinding.bind(view)
                val showroomAddressEditText =
                    binding.accountShowroomAddressDialogFragmentShowroomAddressEditTextView
                val okButton = binding.accountShowroomAddressDialogFragmentOkButton
                val cancelButton = binding.accountShowroomAddressDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY)

                /*Attaching the data*/
                showroomAddressEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        showroomAddressEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountShowroomAddressDialogFragmentShowroomAddressEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountShowroomAddressDialogFragmentShowroomAddressEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_SHOWROOM_ADDRESS_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY to inputValue
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
                showroomAddressEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        showroomAddressEditText.text!!.trim().isEmpty() -> {
                            binding.accountShowroomAddressDialogFragmentShowroomAddressEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountShowroomAddressDialogFragmentShowroomAddressEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountShowroomAddressDialogFragmentShowroomAddressEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_union_name_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountUnionNameDialogFragmentBinding.bind(view)
                val unionNameEditText =
                    binding.accountUnionNameDialogFragmentUnionNameEditTextView
                val okButton = binding.accountUnionNameDialogFragmentOkButton
                val cancelButton = binding.accountUnionNameDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_UNION_NAME_BUNDLE_KEY)

                /*Attaching the data*/
                unionNameEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        unionNameEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountUnionNameDialogFragmentUnionNameEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionNameDialogFragmentUnionNameEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_UNION_NAME_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_UNION_NAME_BUNDLE_KEY to inputValue
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
                unionNameEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        unionNameEditText.text!!.trim().isEmpty() -> {
                            binding.accountUnionNameDialogFragmentUnionNameEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionNameDialogFragmentUnionNameEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountUnionNameDialogFragmentUnionNameEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_union_ward_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountUnionWardDialogFragmentBinding.bind(view)
                val unionWardEditText =
                    binding.accountUnionWardDialogFragmentUnionWardEditTextView
                val okButton = binding.accountUnionWardDialogFragmentOkButton
                val cancelButton = binding.accountUnionWardDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_UNION_WARD_BUNDLE_KEY)

                /*Attaching the data*/
                unionWardEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        unionWardEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountUnionWardDialogFragmentUnionWardEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionWardDialogFragmentUnionWardEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_UNION_WARD_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_UNION_WARD_BUNDLE_KEY to inputValue
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
                unionWardEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        unionWardEditText.text!!.trim().isEmpty() -> {
                            binding.accountUnionWardDialogFragmentUnionWardEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionWardDialogFragmentUnionWardEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountUnionWardDialogFragmentUnionWardEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }

            R.layout.account_union_state_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountUnionStateDialogFragmentBinding.bind(view)
                val unionStateEditText =
                    binding.accountUnionStateDialogFragmentUnionStateEditTextView
                val okButton = binding.accountUnionStateDialogFragmentOkButton
                val cancelButton = binding.accountUnionStateDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_STATE_NAME_BUNDLE_KEY)

                /*Attaching the data*/
                unionStateEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        unionStateEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountUnionStateDialogFragmentUnionStateEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionStateDialogFragmentUnionStateEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_UNION_STATE_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_UNION_STATE_BUNDLE_KEY to inputValue
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
                unionStateEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        unionStateEditText.text!!.trim().isEmpty() -> {
                            binding.accountUnionStateDialogFragmentUnionStateEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionStateDialogFragmentUnionStateEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountUnionStateDialogFragmentUnionStateEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_union_lga_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AccountUnionLgaDialogFragmentBinding.bind(view)
                val unionLgaEditText =
                    binding.accountUnionLgaDialogFragmentUnionLgaEditTextView
                val okButton = binding.accountUnionLgaDialogFragmentOkButton
                val cancelButton = binding.accountUnionLgaDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_UNION_LGA_BUNDLE_KEY)

                /*Attaching the data*/
                unionLgaEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        unionLgaEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.accountUnionLgaDialogFragmentUnionLgaEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionLgaDialogFragmentUnionLgaEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ACCOUNT_UNION_LGA_REQUEST_KEY,
                                bundleOf(
                                    ACCOUNT_UNION_LGA_BUNDLE_KEY to inputValue
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
                unionLgaEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        unionLgaEditText.text!!.trim().isEmpty() -> {
                            binding.accountUnionLgaDialogFragmentUnionLgaEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.accountUnionLgaDialogFragmentUnionLgaEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.accountUnionLgaDialogFragmentUnionLgaEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.account_legal_status_dialog_fragment -> {

                /*Initialise binding*/
                val binding = AccountLegalStatusDialogFragmentBinding.bind(view)

                val okButton = binding.accountLegalStatusDialogFragmentOkButton
                val cancelButton = binding.accountLegalStatusDialogFragmentCancelButton
                val radioGroup = binding.accountLegalStatusDialogFragmentRadioGroup

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_LEGAL_STATUS_BUNDLE_KEY)

                /*Attaching the data*/
                if (retrievedArgs == "Individual") {
                    radioGroup.check(R.id.account_legal_status_dialog_fragment_individual_radio_button)
                } else {
                    radioGroup.check(R.id.account_legal_status_dialog_fragment_coporate_radio_button)
                }

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val selectedID = radioGroup.checkedRadioButtonId
                    val inputValue =
                        view.findViewById<RadioButton>(selectedID).text.toString()

                    setFragmentResult(
                        ACCOUNT_LEGAL_STATUS_REQUEST_KEY,
                        bundleOf(
                            ACCOUNT_LEGAL_STATUS_BUNDLE_KEY to inputValue
                        )
                    )
                    dismiss()
                }
                /*when the dialog cancel button is clicked*/
                cancelButton.setOnClickListener {
                    dismiss()
                }
            }
            R.layout.specialty_delivery_time_dialog_fragment -> {
                /*Initialise binding*/
                val binding = SpecialtyDeliveryTimeDialogFragmentBinding.bind(view)
                val deliveryTimeEditText =
                    binding.specialtyDeliveryTimeDialogFragmentDeliveryTimeEditTextView
                val okButton = binding.specialtyDeliveryTimeDialogFragmentOkButton
                val cancelButton = binding.specialtyDeliveryTimeDialogFragmentCancelButton
                val radioGroup = binding.specialtyDeliveryTimeDialogFragmentRadioGroup

                val retrievedArgs =
                    bundle?.getString(CURRENT_SPECIAL_DELIVERY_TIME_BUNDLE_KEY)

                val retrievedList = retrievedArgs?.split("\\s".toRegex(), 2)

                val retrievedInt = retrievedList?.get(0)?.trim()
                val retrievedTime = retrievedList?.get(1)?.trim()

                /*Attaching the data*/
                when (retrievedTime) {
                    getString(R.string.days) -> {
                        radioGroup.check(R.id.specialty_delivery_time_dialog_fragment_days_radio_button)
                    }
                    getString(R.string.weeks) -> {
                        radioGroup.check(R.id.specialty_delivery_time_dialog_fragment_weeks_radio_button)
                    }
                    else -> {
                        radioGroup.check(R.id.specialty_delivery_time_dialog_fragment_months_radio_button)
                    }
                }

                /*Attaching the data*/
                deliveryTimeEditText.setText(retrievedInt)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val selectedID = radioGroup.checkedRadioButtonId
                    val inputRadioValue = view.findViewById<RadioButton>(selectedID).text.toString()
                    val inputTextValue =
                        deliveryTimeEditText.text.toString()
                    val result = "$inputTextValue $inputRadioValue"

                    when {
                        inputTextValue.isEmpty() -> {
                            binding.specialtyDeliveryTimeDialogFragmentDeliveryTimeEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.specialtyDeliveryTimeDialogFragmentDeliveryTimeEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                SPECIAL_DELIVERY_TIME_REQUEST_KEY,
                                bundleOf(
                                    SPECIAL_DELIVERY_TIME_BUNDLE_KEY to result
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
                deliveryTimeEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        deliveryTimeEditText.text!!.trim().toString().isEmpty() -> {
                            binding.specialtyDeliveryTimeDialogFragmentDeliveryTimeEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.specialtyDeliveryTimeDialogFragmentDeliveryTimeEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.specialtyDeliveryTimeDialogFragmentDeliveryTimeEditTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.specialty_obioma_trained_dialog_fragment -> {

                /*Initialise binding*/
                val binding = SpecialtyObiomaTrainedDialogFragmentBinding.bind(view)

                val okButton = binding.specialtyObiomaTrainedDialogFragmentOkButton
                val cancelButton = binding.specialtyObiomaTrainedDialogFragmentCancelButton
                val radioGroup = binding.specialtyObiomaTrainedDialogFragmentRadioGroup

                val retrievedArgs =
                    bundle?.getString(CURRENT_SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY)

                /*Attaching the data*/
                if (retrievedArgs == "Yes") {
                    radioGroup.check(R.id.specialty_obioma_trained_dialog_fragment_yes_radio_button)
                } else {
                    radioGroup.check(R.id.specialty_obioma_trained_dialog_fragment_no_radio_button)
                }

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val selectedID = radioGroup.checkedRadioButtonId
                    val inputValue =
                        view.findViewById<RadioButton>(selectedID).text.toString()

                    setFragmentResult(
                        SPECIAL_OBIOMA_TRAINED_REQUEST_KEY,
                        bundleOf(
                            SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY to inputValue
                        )
                    )
                    dismiss()
                }
                /*when the dialog cancel button is clicked*/
                cancelButton.setOnClickListener {
                    dismiss()
                }
            }
            R.layout.specialty_add_specialty_dialog_fragment -> {
                /*Initialise binding*/
                val binding = SpecialtyAddSpecialtyDialogFragmentBinding.bind(view)
                val addSpecialtyEditText =
                    binding.specialtyAddSpecialtyDialogFragmentAddSpecialtyEditText
                val addButton = binding.specialtyAddSpecialtyDialogFragmentAddButton

                /*when the dialog ok button is clicked*/
                addButton.setOnClickListener {
                    val inputValue =
                        addSpecialtyEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.specialtyAddSpecialtyDialogFragmentAddSpecialtyTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.specialtyAddSpecialtyDialogFragmentAddSpecialtyTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                ADD_NEW_SPECIALTY_REQUEST_KEY,
                                bundleOf(
                                    ADD_NEW_SPECIALTY_BUNDLE_KEY to inputValue
                                )
                            )
                            dismiss()
                        }
                    }
                }

                /*Validate Dialog Fields onTextChange*/
                addSpecialtyEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        addSpecialtyEditText.text!!.trim().isEmpty() -> {
                            binding.specialtyAddSpecialtyDialogFragmentAddSpecialtyTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.specialtyAddSpecialtyDialogFragmentAddSpecialtyTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.specialtyAddSpecialtyDialogFragmentAddSpecialtyTextInputLayout.error =
                                null
                        }
                    }
                }
            }
            R.layout.rename_gallery_image_dialog_fragment -> {
                /*Initialise binding*/
                val binding = RenameGalleryImageDialogFragmentBinding.bind(view)
                val renameDescriptionEditText =
                    binding.renameGalleryDescriptionDialogFragmentRenameDescriptionEditTextView
                val okButton = binding.renameGalleryDescriptionDialogFragmentOkButton
                val cancelButton = binding.renameGalleryDescriptionDialogFragmentCancelButton

                val retrievedArgs =
                    bundle?.getString(CURRENT_ACCOUNT_RENAME_DESCRIPTION_BUNDLE_KEY)

                /*Attaching the data*/
                renameDescriptionEditText.setText(retrievedArgs)

                /*when the dialog ok button is clicked*/
                okButton.setOnClickListener {
                    val inputValue =
                        renameDescriptionEditText.text.toString()

                    when {
                        inputValue.isEmpty() -> {
                            binding.renameGalleryDescriptionDialogFragmentRenameDescriptionEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.renameGalleryDescriptionDialogFragmentRenameDescriptionEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            setFragmentResult(
                                RENAME_DESCRIPTION_REQUEST_KEY,
                                bundleOf(
                                    RENAME_DESCRIPTION_BUNDLE_KEY to inputValue
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
                renameDescriptionEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        renameDescriptionEditText.text!!.trim().isEmpty() -> {
                            binding.renameGalleryDescriptionDialogFragmentRenameDescriptionEditTextInputLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.renameGalleryDescriptionDialogFragmentRenameDescriptionEditTextInputLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.renameGalleryDescriptionDialogFragmentRenameDescriptionEditTextInputLayout.error =
                                null
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
