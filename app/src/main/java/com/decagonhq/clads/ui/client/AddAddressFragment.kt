package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.databinding.AddAddressFragmentBinding
import com.decagonhq.clads.ui.client.model.DeliveryAddressModel

class AddAddressFragment :Fragment() {
    private var _binding: AddAddressFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var addAddress:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddAddressFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAddress = binding.addAddressFragmentSaveAddressButton
        addAddress.setOnClickListener {
            val enterDeliveryAddress = binding.addAddressFragmentEnterDeliveryAddressEditText.text.toString()
            val cityAddress = binding.addAddressFragmentCityAddressEditText.text.toString()
            val stateAddress = binding.addAddressFragmentStateAddressEditText.text.toString()
            val deliveryAddressModel = DeliveryAddressModel(enterDeliveryAddress, cityAddress, stateAddress)
            val action = AddAddressFragmentDirections.actionAddAddressFragmentToDeliveryAddressFragment(deliveryAddressModel)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}





