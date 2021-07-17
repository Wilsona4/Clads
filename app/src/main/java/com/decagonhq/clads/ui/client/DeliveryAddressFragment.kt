package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.DressMeasurementModel
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.data.domain.client.ClientReg
import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.databinding.DeliveryAddressFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel

class DeliveryAddressFragment : BaseFragment(){
    private var _binding: DeliveryAddressFragmentBinding? = null
    private lateinit var addDeliveryAddressButton: Button
    private  lateinit var saveClientButton: Button
//    private val args:DeliveryAddressFragmentArgs by navArgs()

    private lateinit var addressTextView: TextView

    private lateinit var clientObserve: ClientReg
    private lateinit var measurementObserve: List<DressMeasurementModel>

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val clientsRegisterViewModel: ClientsRegisterViewModel by activityViewModels()

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


        clientsRegisterViewModel.clientData.observe(viewLifecycleOwner, Observer {
            clientObserve = it
        })

        clientsRegisterViewModel.measurementData.observe(viewLifecycleOwner, Observer { it ->
            measurementObserve = it
        })


        // checking if the data has been entered
        val args = arguments
        if (args != null) {
            val addressFragmentArgs = DeliveryAddressFragmentArgs.fromBundle(args)
            // parse measurementArgs
            addressTextView = binding.deliveryAddressFragmentAddressTextView
            addressTextView.text = "${addressFragmentArgs.deliveryAddress!!.deliveryAddress}," +
                " ${addressFragmentArgs.deliveryAddress!!.state}, ${addressFragmentArgs.deliveryAddress!!.city}, Nigeria"
        }

        addDeliveryAddressButton = binding.deliveryAddressFragmentAddButton
        addDeliveryAddressButton.setOnClickListener {
            findNavController().navigate(R.id.addAddressFragment)
        }


        saveClientButton = binding.deliveryAddressFragmentSaveClientButton
        saveClientButton.setOnClickListener {

            val args = arguments
            if (args != null) {
                val addressFragmentArgs = DeliveryAddressFragmentArgs.fromBundle(args)

                //register client
                if(clientObserve!=null && measurementObserve!=null){
                val newClient = Client(
                    fullName = clientObserve.fullName,
                    email = clientObserve.email,
                    phoneNumber = clientObserve.phoneNumber,
                    gender = clientObserve.gender,
                    measurements = measurementObserve,
                    deliveryAddresses = arrayListOf(
                        DeliveryAddress(
                            addressFragmentArgs.deliveryAddress?.deliveryAddress,
                            addressFragmentArgs.deliveryAddress?.city,
                            addressFragmentArgs.deliveryAddress?.state
                        )
                    )
                )

                clientsRegisterViewModel.registerClient(newClient)


                //Log.i("See", newClient.toString())
            }}
            else{}


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
