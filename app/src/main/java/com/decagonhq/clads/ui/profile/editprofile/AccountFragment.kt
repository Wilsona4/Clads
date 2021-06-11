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
        accountWorkShopDialogFragment()
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

    // Account_workshop_dialog_fragment_logic
    fun accountWorkShopDialogFragment() {
        binding.accountFragmentWorkshopAddressTextView.setOnClickListener {
            val accountWorkShopDialogFragmentInstance = AccountWorkShopDialogFragment()
            accountWorkShopDialogFragmentInstance.show(requireActivity().supportFragmentManager, "Account workshop dialog fragment")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
