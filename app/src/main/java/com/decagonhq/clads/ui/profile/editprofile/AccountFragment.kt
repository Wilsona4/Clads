package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.clads.databinding.AccountFragmentBinding

class AccountFragment : Fragment() {
    private var _binding: AccountFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountFirstNameEditDialog()
        accountGenderSelectDialog()
        accountUnionStateDialogFragment()
        accountUnionLGADialogFragment()
        accountUnionWardDialogFragment()
        accountUnionNameDialogFragment()
        accountLastNameDialogFragment()
        accountEmployeeNumberDialogFragment()
        accountShowRoomAddressDialog()
        accountWorkshopStreetDialog()
        accountWorkshopCityDialog()
        accountWorkshopStateDialog()
        accountOtherNameEditDialog()
        accountlastNameEditDialog()
        accountlegalStatusdialog()
    }

    fun accountlegalStatusdialog() {
        binding.accountFragmentLegalStatusValueTextView.setOnClickListener {
            val accountlegalStatusdialog = AccountLegalStatusDialogFragment()
            val accountlegalStatusdialogInput = accountlegalStatusdialog.legalStatusInput
            accountlegalStatusdialog.show(requireActivity().supportFragmentManager, "legal status fragment")
            accountlegalStatusdialogInput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentLegalStatusValueTextView.text = it.toString()
                }
            )
        }
    }

    // Firsname Dialog
    fun accountFirstNameEditDialog() {
        // when first name value is clicked
        binding.accountFragmentFirstNameValueTextView.setOnClickListener {
            val firstNameDialogFragment = AccountFirstNameDialogFragment()
            val firstNameFromDialogFragment = firstNameDialogFragment.firstNameInput
            firstNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "firstName dialog fragment"
            )
            // collect input values from dialog fragment and update the firstname text of user
            firstNameFromDialogFragment.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentFirstNameValueTextView.text = it.toString()
                }
            )
        }
    }

    // Last Dialog
    fun accountlastNameEditDialog() {
        // when last name value is clicked
        binding.accountFragmentLastNameValueTextView.setOnClickListener {
            val lastNameDialogFragment = AccountLastNameDialogFragment()
            val lastNameFromDialogFragment = lastNameDialogFragment.lastNameInput
            lastNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Lastname dialog fragment"
            )
            // collect input values from dialog fragment and update the lastname text of user
            lastNameFromDialogFragment.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentLastNameValueTextView.text = it.toString()
                }
            )
        }
    }

    // Other name Dialog
    fun accountOtherNameEditDialog() {
        // when othername name value is clicked
        binding.accountFragmentOtherNameValueTextView.setOnClickListener {
            val otherNameDialogFragment = AccountOtherNameDialogFragment()
            val otherNameFromDialogFragment = otherNameDialogFragment.otherNameInput
            otherNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Other name dialog fragment"
            )
            // collect input values from dialog fragment and update the othername text of user
            otherNameFromDialogFragment.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentOtherNameValueTextView.text = it.toString()
                }
            )
        }
    }

    // Workshop state Dialog
    fun accountWorkshopStateDialog() {
        // when acciunt shop name value is clicked
        binding.accountFragmentWorkshopAddressStateValueTextView.setOnClickListener {
            val accountWorkshopStateDialogFragment = AccountWorkshopStateDialogFragment()
            val accountWorkshopState = accountWorkshopStateDialogFragment.workshopStateInput
            accountWorkshopStateDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Work shop state dialog fragment"
            )
            // collect input values from dialog fragment and update the accountshop text of user
            accountWorkshopState.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWorkshopAddressStateValueTextView.text = it.toString()
                }
            )
        }
    }

    // Workshop state Dialog
    fun accountWorkshopCityDialog() {
        // when workshopcity name value is clicked
        binding.accountFragmentWorkshopAddressCityValueTextView.setOnClickListener {
            val accountWorkshopCityDialogFragment = AccountWorkshopCityDialogFragment()
            val accountWorkshopCity = accountWorkshopCityDialogFragment.workshopCityInput
            accountWorkshopCityDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Workshop city dialog fragment"
            )
            // collect input values from dialog fragment and update the workshopcity text of user
            accountWorkshopCity.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWorkshopAddressCityValueTextView.text = it.toString()
                }
            )
        }
    }

    // Workshop state Dialog
    fun accountWorkshopStreetDialog() {
        // when workshop name value is clicked
        binding.accountFragmentWorkshopAddressStreetValueTextView.setOnClickListener {
            val accountWorkshopStreetDialogFragment = AccountWorkshopStreetDialogFragment()
            val accountWorkshopStreet = accountWorkshopStreetDialogFragment.workshopStreetInput
            accountWorkshopStreetDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Workshop street dialog fragment"
            )
            // collect input values from dialog fragment and update the workshop text of user
            accountWorkshopStreet.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWorkshopAddressStreetValueTextView.text = it.toString()
                }
            )
        }
    }

    fun accountShowRoomAddressDialog() {
        // when showroom name value is clicked
        binding.accountFragmentShowroomAddressValueTextView.setOnClickListener {
            val accountShowroomAddressDialogFragment = AccountShowroomAddressDialogFragment()
            val accountShowroomAddressinput = accountShowroomAddressDialogFragment.showroomInput
            accountShowroomAddressDialogFragment.show(
                requireActivity().supportFragmentManager,
                "ShowRoom Address dialog fragment"
            )
            // collect input values from dialog fragment and update the showroom text of user
            accountShowroomAddressinput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentShowroomAddressValueTextView.text = it.toString()
                }
            )
        }
    }

    fun accountEmployeeNumberDialogFragment() {
        // when employee number name value is clicked
        binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.setOnClickListener {
            val accountEmployeeNumberDialogFragment = AccountEmployeeNumberDialogFragment()
            val accountEmployeeNumberInput = accountEmployeeNumberDialogFragment.numberOfEmployeeInput
            accountEmployeeNumberDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Employer Number dialog fragment"
            )
            // collect input values from dialog fragment and update the employee number text of user
            accountEmployeeNumberInput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.text = it.toString()
                }
            )
        }
    }

    fun accountLastNameDialogFragment() {
        // when last name value is clicked
        binding.accountFragmentLastNameValueTextView.setOnClickListener {
            val lastNameDialogFragment = AccountLastNameDialogFragment()
            val lastNameInput = lastNameDialogFragment.lastNameInput
            lastNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Legal status dialog fragment"
            )
            // collect input values from dialog fragment and update the lasyname text of user
            lastNameInput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentLastNameValueTextView.text = it.toString()
                }
            )
        }
    }

    fun accountUnionNameDialogFragment() {
        // when union name value is clicked
        binding.accountFragmentNameOfUnionValueTextView.setOnClickListener {
            val accountUnionNameDialogFragment = AccountUnionNameDialogFragment()
            val accountUnionNameinput = accountUnionNameDialogFragment.unionNameInput
            accountUnionNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union Name dialog fragment"
            )
            // collect input values from dialog fragment and update the union name text of user
            accountUnionNameinput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentNameOfUnionValueTextView.text = it.toString()
                }
            )
        }
    }

    fun accountUnionWardDialogFragment() {
        // when ward name value is clicked
        binding.accountFragmentWardValueTextView.setOnClickListener {
            val accountUnionWardDialogFragment = AccountUnionWardDialogFragment()
            val accountUnionWardInput = accountUnionWardDialogFragment.unionWardInput
            accountUnionWardDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union Ward dialog fragment"
            )
            // collect input values from dialog fragment and update the ward text of user
            accountUnionWardInput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWardValueTextView.text = it.toString()
                }
            )
        }
    }

    fun accountUnionLGADialogFragment() {
        // when lga name value is clicked
        binding.accountFragmentLocalGovtAreaValueTextView.setOnClickListener {
            val accountUnionLGADialogFragment = AccountUnionLGADialogFragment()
            val accountUnionLGAInput = accountUnionLGADialogFragment.unionLGAInput
            accountUnionLGADialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union LGA dialog fragment"
            )
            // collect input values from dialog fragment and update the lgs text of user
            accountUnionLGAInput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentLocalGovtAreaValueTextView.text = it.toString()
                }
            )
        }
    }

    fun accountUnionStateDialogFragment() {
        binding.accountFragmentStateValueTextView.setOnClickListener {
            val accountUnionStateDialogFragment = AccountUnionStateDialogFragment()
            val accountUnionStateInput = accountUnionStateDialogFragment.unionStateInput
            accountUnionStateDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union State dialog fragment"
            )
            accountUnionStateInput.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentStateValueTextView.text = it.toString()
                }
            )
        }
    }

    // Gender Dialog
    fun accountGenderSelectDialog() {
        // when gender value is clicked
        binding.accountFragmentGenderValueTextView.setOnClickListener {
            val genderDialogFragment = AccountGenderDialogFragment()
            val genderFromDialogFragment = genderDialogFragment.genderInputData
            genderDialogFragment.show(
                requireActivity().supportFragmentManager,
                "gender dialog fragment"
            )
            // collect input values from dialog fragment and update the gender value of user
            genderFromDialogFragment.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentGenderValueTextView.text = it.toString()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
