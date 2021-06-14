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
//        accountlastNameEditDialog()
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
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_FIRST_NAME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the firstname text of user
            val firstName = bundle.getString(ACCOUNT_FIRST_NAME_BUNDLE_KEY)
            binding.accountFragmentFirstNameValueTextView.text = firstName
        }

        // when employee number name value is clicked
        binding.accountFragmentFirstNameValueTextView.setOnClickListener {
            val currentFirstName = binding.accountFragmentFirstNameValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY to currentFirstName)
            createProfileDialogFragment(R.layout.account_first_name_dialog_fragment, bundle).show(
                childFragmentManager, getString(R.string.frstname_dialog_fragment)
            )
        }
    }

    private fun accountLastNameDialogFragment() {
        // when last name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_LAST_NAME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the firstname text of user
            val lastName = bundle.getString(ACCOUNT_LAST_NAME_BUNDLE_KEY)
            binding.accountFragmentLastNameValueTextView.text = lastName
        }

        // when last Name name value is clicked
        binding.accountFragmentLastNameValueTextView.setOnClickListener {
            val currentLastName = binding.accountFragmentLastNameValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_LAST_NAME_BUNDLE_KEY to currentLastName)
            createProfileDialogFragment(R.layout.account_last_name_dialog_fragment, bundle).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }


    // Other name Dialog
    private fun accountOtherNameEditDialog() {
        // when other name name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_OTHER_NAME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the otherName text of user
            val otherName = bundle.getString(ACCOUNT_OTHER_NAME_BUNDLE_KEY)
            binding.accountFragmentOtherNameValueTextView.text = otherName
        }

        // when last Name name value is clicked
        binding.accountFragmentOtherNameValueTextView.setOnClickListener {
            val currentOtherName = binding.accountFragmentOtherNameValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY to currentOtherName)
            createProfileDialogFragment(R.layout.account_other_name_dialog_fragment, bundle).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    // Workshop state Dialog
    private fun accountWorkshopStateDialog() {
        // when account shop name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_WORKSHOP_STATE_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the state text of user
            val workshopState = bundle.getString(ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY)
            binding.accountFragmentWorkshopAddressStateValueTextView.text = workshopState
        }

        // when state value is clicked
        binding.accountFragmentWorkshopAddressStateValueTextView.setOnClickListener {
            val currentState = binding.accountFragmentWorkshopAddressStateValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY to currentState)
            createProfileDialogFragment(R.layout.account_workshop_state_dialog_fragment, bundle).show(
                childFragmentManager, AccountFragment::class.java.simpleName
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
            val currentEmployeeNumber =
                binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY to currentEmployeeNumber)
            createProfileDialogFragment(
                R.layout.account_employee_number_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                getString(R.string.tag_employee_number_dialog_fragment)
            )
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
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_GENDER_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the text of user
            val gender = bundle.getString(ACCOUNT_GENDER_BUNDLE_KEY)
            binding.accountFragmentGenderValueTextView.text = gender
        }

        // when employee number name value is clicked
        binding.accountFragmentGenderValueTextView.setOnClickListener {
            val currentGender = binding.accountFragmentGenderValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_GENDER_BUNDLE_KEY to currentGender)
            createProfileDialogFragment(R.layout.account_gender_dialog_fragment, bundle).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
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

        const val ACCOUNT_FIRST_NAME_REQUEST_KEY = "ACCOUNT FIRST NAME REQUEST KEY"
        const val ACCOUNT_FIRST_NAME_BUNDLE_KEY = "ACCOUNT FIRST NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY = "CURRENT ACCOUNT FIRST NAME BUNDLE KEY"

        const val ACCOUNT_LAST_NAME_REQUEST_KEY = "ACCOUNT LAST NAME REQUEST KEY"
        const val ACCOUNT_LAST_NAME_BUNDLE_KEY = "ACCOUNT LAST NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_LAST_NAME_BUNDLE_KEY = "CURRENT ACCOUNT LAST NAME BUNDLE KEY"

        const val ACCOUNT_OTHER_NAME_REQUEST_KEY = "ACCOUNT OTHER NAME REQUEST KEY"
        const val ACCOUNT_OTHER_NAME_BUNDLE_KEY = "ACCOUNT OTHER NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY = "CURRENT ACCOUNT OTHER NAME BUNDLE KEY"

        const val ACCOUNT_GENDER_REQUEST_KEY = "ACCOUNT GENDER REQUEST KEY"
        const val ACCOUNT_GENDER_BUNDLE_KEY = "ACCOUNT GENDER BUNDLE KEY"
        const val CURRENT_ACCOUNT_GENDER_BUNDLE_KEY = "CURRENT ACCOUNT GENDER BUNDLE KEY"

        const val ACCOUNT_WORKSHOP_STATE_REQUEST_KEY = "ACCOUNT WORKSHOP STATE REQUEST KEY"
        const val ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY = "ACCOUNT WORKSHOP STATE BUNDLE KEY"
        const val CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY = "CURRENT ACCOUNT WORKSHOP STATE BUNDLE KEY"
    }
}
