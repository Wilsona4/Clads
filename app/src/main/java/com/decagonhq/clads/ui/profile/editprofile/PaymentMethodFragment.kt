package com.decagonhq.clads.ui.profile.editprofile

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.local.UserProfileEntity
import com.decagonhq.clads.databinding.PaymentMethodFragmentBinding
import com.decagonhq.clads.databinding.PaymentOptionsDialogFragmentBinding
import com.decagonhq.clads.databinding.PaymentTermsDialogFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.util.observeOnce
import com.decagonhq.clads.viewmodels.UserProfileViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PaymentMethodFragment : BaseFragment() {
    private var _binding: PaymentMethodFragmentBinding? = null
    private lateinit var paymentTermsListTextView: TextView
    private lateinit var paymentOptionsListTextView: TextView
    private lateinit var paymentMethodFab: FloatingActionButton
    private lateinit var paymentOptionsDialogBinding: PaymentOptionsDialogFragmentBinding
    private lateinit var paymentTermsDialogBinding: PaymentTermsDialogFragmentBinding
    private lateinit var selectedPaymentOptions: MutableSet<String>
    private lateinit var selectedPaymentTerms: MutableSet<String>
    private val userProfileViewModel: UserProfileViewModel by activityViewModels()
    private lateinit var nairaCheckBox: CheckBox
    private lateinit var usdCheckBox: CheckBox
    private lateinit var cashCheckBox: CheckBox
    private lateinit var vCashCheckBox: CheckBox
    private lateinit var payoneerCheckBox: CheckBox
    private lateinit var paymentTermsCheckedList: List<String>
    private lateinit var paymentOptionsCheckedList: List<String>

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PaymentMethodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Update User Profile*/
        binding.paymentMethodFragmentSaveChangesButton.setOnClickListener {
            updateUserProfile()
        }
        userProfileViewModel.getLocalDatabaseUserProfile()
        getPaymentMethod()
        // getting reference with the payment options textView
        paymentOptionsListTextView = binding.paymentMethodFragmentPaymentOptionsListTextView
        // getting reference with the payment terms textView
        paymentTermsListTextView = binding.paymentMethodFragmentPaymentTermsListTextView
        // Initializing the variable for storing payment options
        selectedPaymentOptions = mutableSetOf()
        selectedPaymentOptions.add(getString(R.string.bank_deposit_naira))
        paymentOptionsListTextView.text = selectedPaymentOptions.joinToString(
            separator = "\n\n",
            limit = 3,
            truncated = "..."
        )
        // Initializing the variable for storing payment terms
        selectedPaymentTerms = mutableSetOf()
        selectedPaymentTerms.add(getString(R.string._100_deposit))
        paymentTermsListTextView.text = selectedPaymentTerms.joinToString(
            separator = "\n\n",
            limit = 3,
            truncated = "..."
        )
        // show the payment terms dialog on click of the paymentMethodFragmentPaymentTermsListTextView
        binding.paymentMethodFragmentPaymentTermsListTextView.setOnClickListener() {
            paymentTermsDialogBinding = PaymentTermsDialogFragmentBinding.inflate(layoutInflater)
            // Initializing payment options checkBoxes
            val fullPaymentCheckBox = paymentTermsDialogBinding.paymentTermsFragmentCheckbox1
            val partPaymentCheckBox = paymentTermsDialogBinding.paymentTermsFragmentCheckbox2
            val noDepositCheckBox = paymentTermsDialogBinding.paymentTermsFragmentCheckbox3
            // Dialog for Payment options
            val paymentTermsDialogBuilder = AlertDialog.Builder(requireContext())
            paymentTermsDialogBuilder.setView(paymentTermsDialogBinding.root)
            for (item in paymentTermsCheckedList) {
                when (item) {
                    getString(R.string._100_deposit) -> {
                        fullPaymentCheckBox.isChecked = true
                    }
                    getString(R.string._50_deposit_and_50_balance_on_delivery) -> {
                        partPaymentCheckBox.isChecked = true
                    }
                    getString(R.string._0_deposit_and_100_balance_on_delivery) -> {
                        noDepositCheckBox.isChecked = true
                    }
                }
            }
            // Setting the positive button for the payment options dialog builder
            paymentTermsDialogBuilder.setPositiveButton(
                R.string.ok
            ) { _: DialogInterface, _: Int ->
                addSelectedPaymentTermsCheckBox(fullPaymentCheckBox)
                addSelectedPaymentTermsCheckBox(partPaymentCheckBox)
                addSelectedPaymentTermsCheckBox(noDepositCheckBox)
                if (selectedPaymentTerms.isEmpty()) {
                    selectedPaymentTerms.add("100% Deposit")
                }
                // updating the payment options TextView
                paymentTermsListTextView.text = selectedPaymentTerms.joinToString(
                    separator = "\n\n",
                    limit = 3,
                    truncated = "..."
                )
            }
            // Setting the Negative button for the payment options dialog builder
            paymentTermsDialogBuilder.setNegativeButton(
                R.string.cancel
            ) { _: DialogInterface, _: Int -> }
            paymentTermsDialogBuilder.create().show()
        }
        // set on click listener on my fab button
        paymentMethodFab = binding.paymentMethodFragmentFab
        // Setting onClick listener to the payment options textView
        paymentOptionsListTextView.setOnClickListener() {
            paymentOptionsDialogBinding =
                PaymentOptionsDialogFragmentBinding.inflate(layoutInflater)
            // Initializing payment options checkBoxes
            nairaCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox1
            usdCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox2
            cashCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox3
            vCashCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox4
            payoneerCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox5
            // Dialog for Payment options
            val paymentOptionsDialogBuilder = AlertDialog.Builder(requireContext())
            paymentOptionsDialogBuilder.setView(paymentOptionsDialogBinding.root)
            // pass content of textView to checkBox
            for (item in paymentOptionsCheckedList) {
                when (item) {
                    getString(R.string.bank_deposit_naira) -> {
                        nairaCheckBox.isChecked = true
                    }
                    getString(R.string.bank_deposit_usd_gpb_eur) -> {
                        usdCheckBox.isChecked = true
                    }
                    getString(R.string.cash) -> {
                        cashCheckBox.isChecked = true
                    }
                    getString(R.string.vcash) -> {
                        vCashCheckBox.isChecked = true
                    }
                    getString(R.string.payoneer) -> {
                        payoneerCheckBox.isChecked = true
                    }
                }
            }
            // Setting the positive button for the payment options dialog builder
            paymentOptionsDialogBuilder.setPositiveButton(
                R.string.ok
            ) { _: DialogInterface, _: Int ->
                addSelectedPaymentOptionsCheckBox(nairaCheckBox)
                addSelectedPaymentOptionsCheckBox(usdCheckBox)
                addSelectedPaymentOptionsCheckBox(cashCheckBox)
                addSelectedPaymentOptionsCheckBox(vCashCheckBox)
                addSelectedPaymentOptionsCheckBox(payoneerCheckBox)
                if (selectedPaymentOptions.isEmpty()) {
                    selectedPaymentOptions.add(
                        getString(R.string.bank_deposit_naira)
                    )
                }
                // updating the payment options TextView
                paymentOptionsListTextView.text = selectedPaymentOptions.joinToString(
                    separator = "\n\n",
                    limit = 5,
                )
            }
            // Setting the Negative button for the payment options dialog builder
            paymentOptionsDialogBuilder.setNegativeButton(
                R.string.cancel
            ) { _: DialogInterface, _: Int -> }
            paymentOptionsDialogBuilder.create().show()
        }
    }

    private fun getPaymentMethod() {
        userProfileViewModel.userProfile.observe(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading && it.data?.firstName.isNullOrEmpty()) {
                    progressDialog.showDialogFragment("Updating...")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                } else {
                    progressDialog.hideProgressDialog()
                    it.data.let { userProfile ->
                        binding.apply {
                            paymentMethodFragmentPaymentOptionsListTextView.text =
                                userProfile?.paymentOptions?.joinToString("\n\n")

                            paymentMethodFragmentPaymentTermsListTextView.text =
                                userProfile?.paymentTerms?.joinToString("\n\n")
                            userProfile?.paymentTerms?.let { paymentTerms ->
                                paymentTermsCheckedList = paymentTerms
                            }
                            userProfile?.paymentOptions?.let { paymentOptions ->
                                paymentOptionsCheckedList = paymentOptions
                            }
                        }
                    }
                }
            }
        )
    }

    private fun addSelectedPaymentOptionsCheckBox(checkBox: CheckBox) {
        if (checkBox.isChecked) {
            selectedPaymentOptions.add(checkBox.text.toString())
        } else {
            selectedPaymentOptions.remove(checkBox.text.toString())
        }
    }

    private fun addSelectedPaymentTermsCheckBox(checkBox: CheckBox) {
        if (checkBox.isChecked) {
            selectedPaymentTerms.add(checkBox.text.toString())
        } else {
            selectedPaymentTerms.remove(checkBox.text.toString())
        }
    }

    /*Update User Profile Picture*/
    private fun updateUserProfile() {
        val list = mutableListOf<String>()
        userProfileViewModel.userProfile.observeOnce(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading<UserProfileEntity>) {
                    progressDialog.showDialogFragment("Uploading...")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                } else {
                    progressDialog.hideProgressDialog()
                    it.data?.let { profile ->
                        val userProfile = UserProfile(
                            country = profile.country,
                            deliveryTime = profile.deliveryTime,
                            email = profile.email,
                            firstName = profile.firstName,
                            gender = profile.gender,
                            genderFocus = profile.genderFocus,
                            lastName = profile.lastName,
                            measurementOption = profile.measurementOption,
                            phoneNumber = profile.phoneNumber,
                            role = profile.role,
                            workshopAddress = profile.workshopAddress,
                            showroomAddress = profile.showroomAddress,
                            specialties = profile.specialties,
                            thumbnail = profile.thumbnail,
                            trained = profile.trained,
                            union = profile.union,
                            paymentTerms = selectedPaymentTerms.toList(),
                            paymentOptions = selectedPaymentOptions.toList()
                        )
                        userProfileViewModel.updateUserProfile(userProfile)
                    }
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
