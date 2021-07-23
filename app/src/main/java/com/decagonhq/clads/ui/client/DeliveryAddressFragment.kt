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
import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.databinding.DeliveryAddressFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel

class DeliveryAddressFragment : BaseFragment() {
    private var _binding: DeliveryAddressFragmentBinding? = null
    private lateinit var addDeliveryAddressButton: Button

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val backingFieldViewModel: ClientsRegisterViewModel by activityViewModels()
    private lateinit var clientAddress: DeliveryAddressModel

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
            val view =
                LayoutInflater.from(requireContext()).inflate(R.layout.add_address_fragment, null)
            val states = resources.getStringArray(R.array.states)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.state_drop_down_item, states)
            val autoCompleteStateView =
                view.findViewById<AutoCompleteTextView>(R.id.add_address_fragment_state_auto_complete)
            autoCompleteStateView.setAdapter(arrayAdapter)
            dialog.setView(view)
            val addDialog = dialog.create()
            addDialog.show()
            val enterDeliveryAddress: EditText =
                view.findViewById(R.id.add_address_fragment_enter_delivery_address_edit_text)
            val city: EditText = view.findViewById(R.id.add_address_fragment_city_address_edit_text)
            val addAddressbutton: Button =
                view.findViewById(R.id.add_address_fragment_save_address_button)
            addAddressbutton.setOnClickListener {
                val addressName = enterDeliveryAddress.text.toString()
                val cityName = city.text.toString()
                val state = autoCompleteStateView.text.toString()
                clientAddress = DeliveryAddressModel(addressName, cityName, state)
                binding.deliveryAddressFragmentAddressTextView.text =
                    "$addressName,$cityName, $state"
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
}
