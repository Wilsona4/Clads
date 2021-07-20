package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.data.domain.client.ClientReg
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.data.remote.client.Measurement
import com.decagonhq.clads.databinding.DeliveryAddressFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.viewmodels.ClientViewModel
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel
import com.google.android.material.snackbar.Snackbar

class DeliveryAddressFragment : BaseFragment() {
    private var _binding: DeliveryAddressFragmentBinding? = null
    private lateinit var addDeliveryAddressButton: Button
    private lateinit var saveClientButton: Button

    private lateinit var addressTextView: TextView

    private lateinit var clientObserve: ClientReg
    private lateinit var measurementObserve: List<Measurement>

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val backingFieldsViewModel: ClientsRegisterViewModel by activityViewModels()
    private val clientsRegisterViewModel: ClientViewModel by activityViewModels()

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
        setObservers()

        // checking if the data has been entered
        val args = arguments
        if (args != null) {
            val addressFragmentArgs = DeliveryAddressFragmentArgs.fromBundle(args)
            // parse measurementArgs
            addressTextView = binding.deliveryAddressFragmentAddressTextView
            addressTextView.text =
                "${addressFragmentArgs.deliveryAddress!!.street}, " +
                "${addressFragmentArgs.deliveryAddress!!.city}, ${addressFragmentArgs.deliveryAddress!!.state}, Nigeria"
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

                val newClient = Client(
                    fullName = clientObserve.fullName,
                    email = clientObserve.email,
                    phoneNumber = clientObserve.phoneNumber,
                    gender = clientObserve.gender,
                    measurements = measurementObserve,
                    deliveryAddresses = arrayListOf(
                        DeliveryAddressModel(
                            addressFragmentArgs.deliveryAddress?.street,
                            addressFragmentArgs.deliveryAddress?.city,
                            addressFragmentArgs.deliveryAddress?.state
                        )
                    )
                )
                // register client
                clientsRegisterViewModel.addClient(newClient)
                progressDialog.showDialogFragment("Saving...Please wait")
            } else {
                Snackbar.make(requireView(), "Some Fields have not been entered", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {

        clientsRegisterViewModel.addClientResponse.observe(
            viewLifecycleOwner
        ) {

            when (it) {

                is Resource.Success -> {
                    it.data?.payload?.let { it1 -> clientsRegisterViewModel.addClientToDb(it1) }
                }
                else -> {
                    progressDialog.hideProgressDialog()
                    Snackbar.make(
                        requireView(),
                        it.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        clientsRegisterViewModel.addToDBResponse.observe(
            viewLifecycleOwner
        ) {

            when (it) {

                is Resource.Success -> {

                    it.message?.let { it1 ->
                        Snackbar.make(
                            requireView(),
                            it1,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    progressDialog.hideProgressDialog()

                    findNavController().navigate(R.id.action_deliveryAddressFragment_to_clientFragment)
                }
                else -> {
                    it.message?.let { it1 ->
                        Snackbar.make(
                            requireView(),
                            it1,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    progressDialog.hideProgressDialog()
                }
            }
        }

        backingFieldsViewModel.clientData.observe(
            viewLifecycleOwner,
            Observer {
                clientObserve = it
            }
        )

        backingFieldsViewModel.measurementData.observe(
            viewLifecycleOwner,
            Observer { it ->
                measurementObserve = it
            }
        )
    }
}
