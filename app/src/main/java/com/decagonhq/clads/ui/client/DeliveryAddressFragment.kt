package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.DeliveryAddressFragmentBinding

class DeliveryAddressFragment : Fragment() {
    private var _binding: DeliveryAddressFragmentBinding? = null
    private lateinit var addDeliveryAddressButton: Button
//    private val args:DeliveryAddressFragmentArgs by navArgs()
    private lateinit var addressTextView: TextView

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
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

        //checking if the data has been entered
        val args = arguments
        if (args != null) {
            val addressFragmentArgs = DeliveryAddressFragmentArgs.fromBundle(args)
            // parse measurementArgs
            addressTextView = binding.deliveryAddressFragmentAddressTextView
            addressTextView.text = "${addressFragmentArgs.deliveryAddress!!.deliveryAddress}," +
                    " ${addressFragmentArgs.deliveryAddress.state}, ${addressFragmentArgs.deliveryAddress.city}, Nigeria"
        }



        addDeliveryAddressButton = binding.deliveryAddressFragmentAddButton
        addDeliveryAddressButton.setOnClickListener {
            findNavController().navigate(R.id.addAddressFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
