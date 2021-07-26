package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.data.domain.client.ClientReg
import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.data.domain.client.Measurement
import com.decagonhq.clads.databinding.AddClientFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.client.adapter.AddClientPagerAdapter
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.util.hideView
import com.decagonhq.clads.util.showView
import com.decagonhq.clads.viewmodels.ClientViewModel
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AddClientFragment : BaseFragment() {
    private var _binding: AddClientFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var nextAndSaveButton: TextView
    private lateinit var previousButton: TextView
    private lateinit var clientBio: ClientReg
    private var clientMeasurements: List<Measurement>? = null
    private lateinit var clientDeliveryAddress: DeliveryAddress
    private var isClientAccountFragmentSaved: Boolean = false
    private var pagePosition = 0

    private val clientsRegisterViewModel: ClientsRegisterViewModel by activityViewModels()
    private val clientViewModel: ClientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddClientFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setEventListeners()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setEventListeners() {

        nextAndSaveButton.setOnClickListener {

            saveChildFragmentData(pagePosition)

            if (pagePosition == 0 && isClientAccountFragmentSaved) {
                viewPager2.currentItem = 1
            } else if (pagePosition == 1) {
                viewPager2.currentItem = 2
            } else if (pagePosition == 2) {

                if (this::clientBio.isInitialized && clientMeasurements?.isNotEmpty() == true && this::clientDeliveryAddress.isInitialized) {

                    clientViewModel.addClient(
                        Client(
                            fullName = clientBio.fullName,
                            phoneNumber = clientBio.phoneNumber,
                            email = clientBio.email,
                            gender = clientBio.gender,
                            deliveryAddresses = arrayListOf(clientDeliveryAddress),
                            measurements = clientMeasurements
                        )
                    )
                    progressDialog.showDialogFragment("Saving...Please wait")

                    clientViewModel.client.observe(
                        viewLifecycleOwner,
                        Observer {
                            if (it is Resource.Loading && it.data.isNullOrEmpty()) {
                                progressDialog.showDialogFragment("Updating..")
                            } else if (it is Resource.Error) {
                                progressDialog.hideProgressDialog()
                                handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                            } else {
                                progressDialog.hideProgressDialog()
                                it.data?.let {
                                    showToast("Saved Successfully")
                                }
                                findNavController().popBackStack()
                            }
                        }
                    )
                }
            } else {
                Snackbar.make(
                    requireView(),
                    "Some Fields have not been entered",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pagePosition = position
                when (position) {
                    0 -> {
                        binding.addClientFragmentNextButton.text = getString(R.string.all_next)
                        binding.addClientFragmentPreviousButton.hideView()
                    }
                    1 -> {
                        binding.addClientFragmentNextButton.text = getString(R.string.all_next)
                        binding.addClientFragmentPreviousButton.showView()
                    }
                    2 -> {
                        binding.addClientFragmentNextButton.text = getString(R.string.all_save)
                        binding.addClientFragmentPreviousButton.showView()
                    }
                }
            }
        })

        previousButton.setOnClickListener {
            when (pagePosition) {
                2 -> {
                    viewPager2.currentItem = 1
                }
                1 -> {
                    viewPager2.currentItem = 0
                }
            }
        }
    }

    private fun init() {
        viewPager2 = binding.addClientViewPager
        tabLayout = binding.addClientTabLayout
        nextAndSaveButton = binding.addClientFragmentNextButton
        previousButton = binding.addClientFragmentPreviousButton
        val adapter = AddClientPagerAdapter(childFragmentManager, lifecycle)
        viewPager2.apply {
            this.adapter = adapter
            this.isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.client_account)
                1 -> tab.text = getString(R.string.measurements)
                2 -> tab.text = getString(R.string.delivery_addresses)
            }
        }.attach()
    }

    private fun setObservers() {
        clientsRegisterViewModel.clientData.observe(
            viewLifecycleOwner,
            Observer {
                clientBio = it
            }
        )

        clientsRegisterViewModel.measurementData.observe(
            viewLifecycleOwner,
            Observer { it ->
                clientMeasurements = it
            }
        )

        clientsRegisterViewModel.clientAddress.observe(
            viewLifecycleOwner,
            Observer { it ->
                clientDeliveryAddress = it
            }
        )
    }

    private fun saveChildFragmentData(position: Int) {
        when (val fragment = childFragmentManager.fragments[position]) {
            is ClientAccountFragment -> {
                isClientAccountFragmentSaved = fragment.saveToViewModel()
            }

            is MeasurementsFragment -> {
                fragment.saveToViewModel()
            }

            is DeliveryAddressFragment -> {
                fragment.saveToViewModel()
            }
        }
    }
}
