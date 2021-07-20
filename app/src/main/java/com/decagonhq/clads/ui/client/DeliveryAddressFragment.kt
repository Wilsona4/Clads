package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.databinding.DeliveryAddressFragmentBinding
import com.decagonhq.clads.viewmodels.ClientViewModel

class DeliveryAddressFragment : Fragment() {
    private var _binding: DeliveryAddressFragmentBinding? = null
    private lateinit var addDeliveryAddressButton: Button
//    private val args:DeliveryAddressFragmentArgs by navArgs()
    private lateinit var addressTextView: TextView
    private lateinit var addressViewModel: ClientViewModel

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DeliveryAddressFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        addressViewModel = ViewModelProvider(requireActivity()).get(ClientViewModel::class.java)

        addressViewModel.clientAddress.observe(
            viewLifecycleOwner,
            Observer {
                addressTextView = binding.deliveryAddressFragmentAddressTextView

                addressTextView.text = "${it.deliveryAddress}, ${it.city}, ${it.state}"
            }
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // checking if the data has been entered
        addDeliveryAddressButton = binding.deliveryAddressFragmentAddButton
        addDeliveryAddressButton.setOnClickListener {
            var dialog = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.add_address_fragment, null)
            val states = resources.getStringArray(R.array.states)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.state_drop_down_item, states)
            val autoCompleteStateView = view.findViewById<AutoCompleteTextView>(R.id.add_address_fragment_state_auto_complete)
            autoCompleteStateView.setAdapter(arrayAdapter)
            dialog.setView(view)
            val addDialog = dialog.create()
            addDialog.show()
            var enterDeliveryAddress: EditText =
                view.findViewById(R.id.add_address_fragment_enter_delivery_address_edit_text)
            var city: EditText =
                view.findViewById(R.id.add_address_fragment_city_address_edit_text)
            var addAddressbutton: Button = view.findViewById(R.id.add_address_fragment_save_address_button)
            addAddressbutton.setOnClickListener {
                val addressName = enterDeliveryAddress.text.toString()
                val cityName = city.text.toString()
                val state = autoCompleteStateView.text.toString()
                var clientAddress = DeliveryAddressModel(addressName, cityName, state)
                addressViewModel.clientNewAddress(clientAddress)
                addDialog.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
// findNavController().navigate(R.id.addAddressFragment)
