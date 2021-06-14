package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AccountFragmentBinding
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments.Companion.createProfileDialogFragment
import com.decagonhq.clads.viewmodels.ProfileManagementViewModel

class AccountFragment : Fragment() {
    private var _binding: AccountFragmentBinding? = null
    private lateinit var profileManagementViewModel: ProfileManagementViewModel

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AccountFragmentBinding.inflate(inflater, container, false)

        profileManagementViewModel =
            ViewModelProvider(requireActivity()).get(ProfileManagementViewModel::class.java)

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

    private fun accountlegalStatusdialog() {
        binding.accountFragmentLegalStatusValueTextView.setOnClickListener {
            val accountlegalStatusdialog = AccountLegalStatusDialogFragment()
            accountlegalStatusdialog.show(
                requireActivity().supportFragmentManager,
                "legal status fragment"
            )
            profileManagementViewModel.legalStatusLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentLegalStatusValueTextView.text = it.toString()
                }
            )
        }
    }

    // Firstname Dialog
    private fun accountFirstNameEditDialog() {
        // when first name value is clicked
        binding.accountFragmentFirstNameValueTextView.setOnClickListener {
            val firstNameDialogFragment = AccountFirstNameDialogFragment()

            firstNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "firstName dialog fragment"
            )
            // collect input values from dialog fragment and update the firstname text of user
            profileManagementViewModel.firstNameInputLiveData
                .observe(
                    viewLifecycleOwner,
                    {
                        binding.accountFragmentFirstNameValueTextView.text = it.toString()
                    }
                )
        }
    }

    // Last Dialog
    private fun accountlastNameEditDialog() {
        // when last name value is clicked
        binding.accountFragmentLastNameValueTextView.setOnClickListener {
            val lastNameDialogFragment = AccountLastNameDialogFragment()
            lastNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Lastname dialog fragment"
            )
            // collect input values from dialog fragment and update the lastname text of user
            profileManagementViewModel.lastNameInputLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentLastNameValueTextView.text = it.toString()
                }
            )
        }
    }

    // Other name Dialog
    private fun accountOtherNameEditDialog() {
        // when othername name value is clicked
        binding.accountFragmentOtherNameValueTextView.setOnClickListener {
            val otherNameDialogFragment = AccountOtherNameDialogFragment()
            otherNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Other name dialog fragment"
            )
            // collect input values from dialog fragment and update the othername text of user
            profileManagementViewModel.otherNameInputLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentOtherNameValueTextView.text = it.toString()
                }
            )
        }
    }

    // Workshop state Dialog
    private fun accountWorkshopStateDialog() {
        // when acciunt shop name value is clicked
        binding.accountFragmentWorkshopAddressStateValueTextView.setOnClickListener {
            val accountWorkshopStateDialogFragment = AccountWorkshopStateDialogFragment()
            accountWorkshopStateDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Work shop state dialog fragment"
            )
            // collect input values from dialog fragment and update the accountshop text of user
            profileManagementViewModel.stateLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWorkshopAddressStateValueTextView.text = it.toString()
                }
            )
        }
    }

    // Workshop state Dialog
    private fun accountWorkshopCityDialog() {
        // when workshopcity name value is clicked
        binding.accountFragmentWorkshopAddressCityValueTextView.setOnClickListener {
            val accountWorkshopCityDialogFragment = AccountWorkshopCityDialogFragment()
            accountWorkshopCityDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Workshop city dialog fragment"
            )
            // collect input values from dialog fragment and update the workshopcity text of user
            profileManagementViewModel.cityLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWorkshopAddressCityValueTextView.text = it.toString()
                }
            )
        }
    }

    // Workshop state Dialog
    private fun accountWorkshopStreetDialog() {
        // when workshop name value is clicked
        binding.accountFragmentWorkshopAddressStreetValueTextView.setOnClickListener {
            val accountWorkshopStreetDialogFragment = AccountWorkshopStreetDialogFragment()
            accountWorkshopStreetDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Workshop street dialog fragment"
            )
            // collect input values from dialog fragment and update the workshop text of user
            profileManagementViewModel.streetLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWorkshopAddressStreetValueTextView.text = it.toString()
                }
            )
        }
    }

    private fun accountShowRoomAddressDialog() {
        // when showroom name value is clicked
        binding.accountFragmentShowroomAddressValueTextView.setOnClickListener {
            val accountShowroomAddressDialogFragment = AccountShowroomAddressDialogFragment()
            accountShowroomAddressDialogFragment.show(
                requireActivity().supportFragmentManager,
                "ShowRoom Address dialog fragment"
            )
            // collect input values from dialog fragment and update the showroom text of user
            profileManagementViewModel.showroomAddressLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentShowroomAddressValueTextView.text = it.toString()
                }
            )
        }
    }

    private fun accountEmployeeNumberDialogFragment() {
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_EMPLOYEE_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the employee number text of user
            val employeeNumber = bundle.getString(ACCOUNT_EMPLOYEE_BUNDLE_KEY)
            binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.text = employeeNumber
        }

        // when employee number name value is clicked
        binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.setOnClickListener {
            val currentEmployeeNumber = binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY to currentEmployeeNumber)
            createProfileDialogFragment(R.layout.account_employee_number_dialog_fragment, bundle).show(
                childFragmentManager,
                getString(R.string.tag_employee_number_dialog_fragment)
            )
        }
    }

    private fun accountLastNameDialogFragment() {
        // when last name value is clicked
        binding.accountFragmentLastNameValueTextView.setOnClickListener {
            val lastNameDialogFragment = AccountLastNameDialogFragment()
            lastNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Legal status dialog fragment"
            )
            // collect input values from dialog fragment and update the lasyname text of user
            profileManagementViewModel.lastNameInputLiveData.observe(
                viewLifecycleOwner
            ) {
                binding.accountFragmentLastNameValueTextView.text = it.toString()
            }
        }
    }

    private fun accountUnionNameDialogFragment() {
        // when union name value is clicked
        binding.accountFragmentNameOfUnionValueTextView.setOnClickListener {
            val accountUnionNameDialogFragment = AccountUnionNameDialogFragment()
            accountUnionNameDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union Name dialog fragment"
            )
            // collect input values from dialog fragment and update the union name text of user
            profileManagementViewModel.nameOfUnionLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentNameOfUnionValueTextView.text = it.toString()
                }
            )
        }
    }

    private fun accountUnionWardDialogFragment() {
        // when ward name value is clicked
        binding.accountFragmentWardValueTextView.setOnClickListener {
            val accountUnionWardDialogFragment = AccountUnionWardDialogFragment()
            accountUnionWardDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union Ward dialog fragment"
            )
            // collect input values from dialog fragment and update the ward text of user
            profileManagementViewModel.wardOfUnionLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentWardValueTextView.text = it.toString()
                }
            )
        }
    }

    private fun accountUnionLGADialogFragment() {
        // when lga name value is clicked
        binding.accountFragmentLocalGovtAreaValueTextView.setOnClickListener {
            val accountUnionLGADialogFragment = AccountUnionLGADialogFragment()
            accountUnionLGADialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union LGA dialog fragment"
            )
            // collect input values from dialog fragment and update the lgs text of user
            profileManagementViewModel.lgaOfUnionLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.accountFragmentLocalGovtAreaValueTextView.text = it.toString()
                }
            )
        }
    }

    private fun accountUnionStateDialogFragment() {
        binding.accountFragmentStateValueTextView.setOnClickListener {
            val accountUnionStateDialogFragment = AccountUnionStateDialogFragment()
            accountUnionStateDialogFragment.show(
                requireActivity().supportFragmentManager,
                "Union State dialog fragment"
            )
            profileManagementViewModel.stateOfUnionLiveData.observe(
                viewLifecycleOwner
            ) {
                binding.accountFragmentStateValueTextView.text = it.toString()
            }
        }
    }

    // Gender Dialog
    private fun accountGenderSelectDialog() {
        // when gender value is clicked
        binding.accountFragmentGenderValueTextView.setOnClickListener {
            val genderDialogFragment = AccountGenderDialogFragment()
            genderDialogFragment.show(
                requireActivity().supportFragmentManager,
                "gender dialog fragment"
            )
            // collect input values from dialog fragment and update the gender value of user
            profileManagementViewModel.genderInputLiveData.observe(
                viewLifecycleOwner
            ) {
                binding.accountFragmentGenderValueTextView.text = it.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ACCOUNT_EMPLOYEE_REQUEST_KEY = "ACCOUNT EMPLOYEE REQUEST KEY"
        const val ACCOUNT_EMPLOYEE_BUNDLE_KEY = "ACCOUNT EMPLOYEE BUNDLE KEY"
        const val CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY = "CURRENT ACCOUNT EMPLOYEE BUNDLE KEY"

        const val EDIT_MEASUREMENT_BUNDLE_KEY = "EDIT CLIENT MEASUREMENT BUNDLE KEY"
        const val EDIT_MEASUREMENT_BUNDLE_POSITION = "EDIT CLIENT MEASUREMENT BUNDLE POSITION"

        const val EDITED_MEASUREMENT_REQUEST_KEY = "EDITED CLIENT MEASUREMENT REQUEST KEY"
        const val EDITED_MEASUREMENT_BUNDLE_KEY = "EDITED CLIENT MEASUREMENT BUNDLE KEY"
    }
}
