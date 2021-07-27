package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.databinding.DeliveryAddressFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel

class DeliveryAddressFragment : BaseFragment() {
    private var _binding: DeliveryAddressFragmentBinding? = null
    private lateinit var addDeliveryAddressButton: Button
    private val args = parentFragment?.arguments

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val backingFieldViewModel: ClientsRegisterViewModel by activityViewModels()
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
        addDeliveryAddressButton.setOnClickListener {

            val dialog = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.add_address_fragment, null)
            val states = resources.getStringArray(R.array.states)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.state_drop_down_item, states)
            val autoCompleteStateView =
                view.findViewById<AutoCompleteTextView>(R.id.add_address_fragment_state_auto_complete)
            autoCompleteStateView.setAdapter(arrayAdapter)
            dialog.setView(view)
            val addDialog = dialog.create()
            addDialog.show()
            val enterDeliveryAddress: EditText = view.findViewById(R.id.add_address_fragment_enter_delivery_address_edit_text)
            val city: EditText = view.findViewById(R.id.add_address_fragment_city_address_edit_text)
            val addAddressButton: Button = view.findViewById(R.id.add_address_fragment_save_address_button)
            addAddressButton.setOnClickListener {
                val addressName = enterDeliveryAddress.text.toString()
                val cityName = city.text.toString()
                val state = autoCompleteStateView.text.toString()
                clientAddress = DeliveryAddress(addressName, cityName, state)
                binding.deliveryAddressFragmentAddressTextView.text =
                    "$addressName, $cityName, $state"
                backingFieldViewModel.clientNewAddress(clientAddress)
                addDialog.dismiss()
            }
        }
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
    //    // add delivery address Dialog
//    private fun addAddressDialogFragment() {
//        // when first name value is clicked
//        childFragmentManager.setFragmentResultListener(
//            DeliveryAddressFragment.DELIVERY_ADDRESS_REQUEST_KEY,
//            requireActivity()
//        ) { key, bundle ->
//            // collect input values from dialog fragment and update the delivery address text of user
//            val addressText = bundle.getParcelable<DeliveryAddressModel>(DELIVERY_ADDRESS_BUNDLE_KEY)
//            binding.deliveryAddressFragmentAddressTextView.text = addressText
//        }
//
//        // when first name value is clicked
//        binding.deliveryAddressFragmentAddressTextView.setOnClickListener {
//            val currentAddressText = binding.deliveryAddressFragmentAddressTextView.text.toString()
// //            val splited = currentAddressText.split("-")
// //            val currentAddressModel = DeliveryAddressModel(
// //                splited[0],
// //            )
//            val bundle = bundleOf(CURRENT_DELIVERY_ADDRESS_BUNDLE_KEY to currentAddressText)
//            ClientManagementDialogFragments.createClientDialogFragment(
//                R.layout.add_address_fragment,
//                bundle
//            ).show(
//                childFragmentManager, DeliveryAddressFragment::class.java.simpleName
//            )
//        }
//    }

    companion object {
        const val DELIVERY_ADDRESS_REQUEST_KEY = "DELIVERY ADDRESS REQUEST KEY"
        const val DELIVERY_ADDRESS_BUNDLE_KEY = "DELIVERY ADDRESS BUNDLE KEY"
        const val CURRENT_DELIVERY_ADDRESS_BUNDLE_KEY = "CURRENT DELIVERY ADDRESS BUNDLE KEY"
    }
}
