package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.databinding.AddAddressFragmentBinding
import com.google.android.material.textfield.TextInputEditText

class AddAddressFragment : Fragment() {
    private var _binding: AddAddressFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var saveAddressButton: Button
    private lateinit var stateSelectorDropdown: AutoCompleteTextView
    private lateinit var deliveryAddress: TextInputEditText
    private lateinit var cityAddress: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddAddressFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Adding new address
        saveAddressButton = binding.addAddressFragmentSaveAddressButton
        stateSelectorDropdown = binding.addAddressFragmentStateAutoComplete
        deliveryAddress = binding.addAddressFragmentEnterDeliveryAddressEditText
        cityAddress = binding.addAddressFragmentCityAddressEditText

        /*Form submission*/
        saveAddressButton.setOnClickListener {
            val enterDeliveryAddress = deliveryAddress.text.toString()
            val cityAddress = cityAddress.text.toString()
            val stateAddress = stateSelectorDropdown.text.toString()
            when {
                enterDeliveryAddress.isEmpty() -> {
                    binding.addAddressFragmentEnterDeliveryAddressEditTextLayout.error =
                        getString(
                            R.string.required
                        )
                    return@setOnClickListener
                }
                cityAddress.isEmpty() -> {
                    binding.addAddressFragmentCityAddressEditTextLayout.error =
                        getString(
                            R.string.required
                        )
                    binding.addAddressFragmentCityAddressEditTextLayout.errorIconDrawable =
                        null

                    return@setOnClickListener
                }
                stateAddress.isEmpty() -> {
                    binding.addAddressFragmentStateAddressEditTextLayout.error =
                        getString(
                            R.string.required
                        )
                    binding.addAddressFragmentStateAddressEditTextLayout.errorIconDrawable =
                        null
                    return@setOnClickListener
                }
                else -> {
                    val deliveryAddressModel =
                        DeliveryAddressModel(
                            enterDeliveryAddress,
                            cityAddress,
                            stateAddress
                        )
                    val action =
                        AddAddressFragmentDirections.actionAddAddressFragmentToDeliveryAddressFragment(
                            deliveryAddressModel
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /*Set up States Dropdown*/
        val states = resources.getStringArray(R.array.states)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.state_drop_down_item, states)
        stateSelectorDropdown.setAdapter(arrayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
