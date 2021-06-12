package com.decagonhq.clads.ui.profile.editprofile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.* // ktlint-disable no-wildcard-imports
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AddPaymentTermsDialogFragmentBinding
import com.decagonhq.clads.databinding.PaymentMethodFragmentBinding
import com.decagonhq.clads.databinding.PaymentOptionsDialogFragmentBinding
import com.decagonhq.clads.databinding.PaymentTermsDialogFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PaymentMethodFragment : Fragment() {
    private var _binding: PaymentMethodFragmentBinding? = null
    private lateinit var paymentTermsList: TextView
    private lateinit var paymentOptionsList: TextView
    private lateinit var paymentMethodFab: FloatingActionButton
    private lateinit var addPaymentTermsDialogBinding: AddPaymentTermsDialogFragmentBinding
    private lateinit var paymentOptionsDialogBinding: PaymentOptionsDialogFragmentBinding
    private lateinit var paymentTermsDialogBinding: PaymentTermsDialogFragmentBinding
    private lateinit var selectedPaymentOptions: MutableSet<String>
    private lateinit var nairaCheckBox: CheckBox
    private lateinit var usdCheckBox: CheckBox
    private lateinit var cashCheckBox: CheckBox
    private lateinit var vCashCheckBox: CheckBox
    private lateinit var payoneerCheckBox: CheckBox
    private lateinit var paymentTermsRadioGroup: RadioGroup
    private var selectedPaymentTerm = ""

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PaymentMethodFragmentBinding.inflate(inflater, container, false)

        // fake payment terms
        val availablePaymentTerms = arrayListOf("100% Deposit", "50% Deposit and 50% balance on delivery", "0% Deposit and 100% balance on delivery")

        // creating an instance of my alert dialog builder 
        addPaymentTermsDialogBinding = AddPaymentTermsDialogFragmentBinding.inflate(layoutInflater)

        // getting reference with the payment options textView
        paymentOptionsList = binding.paymentMethodFragmentPaymentOptionsListTextView

        // creating the alert dialog builder
        val addPaymentTermsDialogBuilder = AlertDialog.Builder(requireContext())
        addPaymentTermsDialogBuilder.setView(addPaymentTermsDialogBinding.root)
        val addPaymentTermsDialog = addPaymentTermsDialogBuilder.create()

        // initializing my variables inside my onViewCreated
        paymentTermsList = binding.paymentMethodFragmentPaymentTermsListTextView
        paymentOptionsList = binding.paymentMethodFragmentPaymentOptionsListTextView

        // Initializing the variable for storing payment options
        selectedPaymentOptions = mutableSetOf()
        selectedPaymentOptions.add("Bank deposit (Naira)")
        paymentOptionsList.text = selectedPaymentOptions.joinToString(
            separator = ",",
            limit = 3,
            truncated = "..."
        )

        paymentTermsList = binding.paymentMethodFragmentPaymentTermsListTextView

        paymentTermsList.setOnClickListener() {

            paymentTermsDialogBinding = PaymentTermsDialogFragmentBinding.inflate(layoutInflater)

            // initialising the payment terms radio group
            paymentTermsRadioGroup = paymentTermsDialogBinding.paymentTermsFragmentRadioGroup

            for (i in availablePaymentTerms) {
                var paymentTermsRadioButton = RadioButton(requireContext())
                paymentTermsRadioButton.text = i
                paymentTermsRadioButton.setPadding(16, 7, 0, 7)
                paymentTermsRadioButton.setTextColor(resources.getColor(R.color.navy_blue))
                paymentTermsRadioButton.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.deep_sky_blue))
                paymentTermsRadioButton.setOnClickListener() {
                    selectedPaymentTerm = i
                }
                paymentTermsRadioGroup.addView(paymentTermsRadioButton)
            }

            // Dialog for payment Terms
            val paymentTermsDialogBuilder = AlertDialog.Builder(requireContext())
            paymentTermsDialogBuilder.setView(paymentTermsDialogBinding.root)

            paymentTermsDialogBuilder.setPositiveButton(
                R.string.ok
            ) { _: DialogInterface, _: Int ->
                paymentTermsList.text = selectedPaymentTerm
            }

            paymentTermsDialogBuilder.setNegativeButton(R.string.cancel) { _: DialogInterface, _: Int ->
            }

            paymentTermsDialogBuilder.create().show()
        }

        // set on click listener on my fab button
        paymentMethodFab = binding.paymentMethodFragmentFab
        paymentMethodFab.setOnClickListener {
            addPaymentTermsDialog.show()
        }

        // Setting onClick listener to the payment options textView
        paymentOptionsList.setOnClickListener() {
            paymentOptionsDialogBinding = PaymentOptionsDialogFragmentBinding.inflate(layoutInflater)

            // Initializing payment options checkBoxes
            nairaCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox1
            usdCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox2
            cashCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox3
            vCashCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox4
            payoneerCheckBox = paymentOptionsDialogBinding.paymentOptionsFragmentCheckbox5

            // Dialog for Payment options
            val paymentOptionsDialogBuilder = AlertDialog.Builder(requireContext())
            paymentOptionsDialogBuilder.setView(paymentOptionsDialogBinding.root)

            // Setting the positive button for the payment options dialog builder
            paymentOptionsDialogBuilder.setPositiveButton(
                R.string.ok
            ) { _: DialogInterface, _: Int ->
                addSelectedCheckBox(nairaCheckBox)
                addSelectedCheckBox(usdCheckBox)
                addSelectedCheckBox(cashCheckBox)
                addSelectedCheckBox(vCashCheckBox)
                addSelectedCheckBox(payoneerCheckBox)
                if (selectedPaymentOptions.isEmpty()) {
                    selectedPaymentOptions.add("Bank deposit (Naira)")
                }
                // updating the payment options TextView 
                paymentOptionsList.text = selectedPaymentOptions.joinToString(
                    separator = ", ",
                    limit = 3,
                    truncated = "..."
                )
            }
            // Setting the Negative button for the payment options dialog builder            
            paymentOptionsDialogBuilder.setNegativeButton(
                R.string.cancel
            ) { _: DialogInterface, _: Int -> }
            paymentOptionsDialogBuilder.create().show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addSelectedCheckBox(checkBox: CheckBox) {
        if (checkBox.isChecked) {
            selectedPaymentOptions.add(checkBox.text.toString())
        } else {
            selectedPaymentOptions.remove(checkBox.text.toString())
        }
    }
}
