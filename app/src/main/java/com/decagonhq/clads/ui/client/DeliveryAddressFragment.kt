package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.databinding.DeliveryAddressFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.client.dialogfragment.ClientManagementDialogFragments
import java.util.Locale

class DeliveryAddressFragment : BaseFragment() {
    private var _binding: DeliveryAddressFragmentBinding? = null
    private lateinit var addDeliveryAddressButton: Button

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var clientAddress: DeliveryAddress

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DeliveryAddressFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setEventListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEventListeners() {
        addAddressDialogFragment()
        toggleButtonText()
    }

    private fun init() {
        addDeliveryAddressButton = binding.deliveryAddressFragmentAddButton
    }

    fun saveToViewModel(): Boolean {
        var isSaved = false
        if (isAdded && isVisible) {
        }
        return false
    }

    /*Toggle Button Text View btw Add and Edit Address*/
    private fun toggleButtonText() {
        if (binding.deliveryAddressFragmentAddressTextView.text.toString() != getString(R.string.there_are_no_delivery_address)) {
            binding.deliveryAddressFragmentAddButton.text = getString(R.string.edit_address)
        } else {
            binding.deliveryAddressFragmentAddButton.text =
                getString(R.string.fragment_add_delivery_address_save_text_on_button)
        }
    }

    // add delivery address Dialog
    private fun addAddressDialogFragment() {
        // when first name value is clicked
        childFragmentManager.setFragmentResultListener(
            DELIVERY_ADDRESS_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the delivery address text of user
            val addressBundle = bundle.getParcelable<DeliveryAddress>(DELIVERY_ADDRESS_BUNDLE_KEY)
            addressBundle?.let {
                clientAddress = DeliveryAddress(
                    it.street?.capitalize(Locale.ROOT),
                    it.city?.capitalize(Locale.ROOT),
                    it.state
                )
                binding.deliveryAddressFragmentAddressTextView.text =
                    "${it.street?.capitalize(Locale.ROOT)} ~ ${it.city?.capitalize(Locale.ROOT)} ~ ${it.state}"
                toggleButtonText()
            }
        }

        // when first name value is clicked
        addDeliveryAddressButton.setOnClickListener {

            val currentAddressText =
                if (binding.deliveryAddressFragmentAddressTextView.text.toString() != getString(R.string.there_are_no_delivery_address)) {
                    binding.deliveryAddressFragmentAddressTextView.text.toString()
                } else {
                    null
                }

            val bundle = bundleOf(CURRENT_DELIVERY_ADDRESS_BUNDLE_KEY to currentAddressText)
            ClientManagementDialogFragments.createClientDialogFragment(
                R.layout.add_address_fragment,
                bundle
            ).show(
                childFragmentManager, DeliveryAddressFragment::class.java.simpleName
            )
        }
    }

    companion object {
        const val DELIVERY_ADDRESS_REQUEST_KEY = "DELIVERY ADDRESS REQUEST KEY"
        const val DELIVERY_ADDRESS_BUNDLE_KEY = "DELIVERY ADDRESS BUNDLE KEY"
        const val CURRENT_DELIVERY_ADDRESS_BUNDLE_KEY = "CURRENT DELIVERY ADDRESS BUNDLE KEY"
    }
}
