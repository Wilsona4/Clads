package com.decagonhq.clads.ui.profile.bottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.HomeFragmentBinding
import com.decagonhq.clads.util.ChartData.chartData
import com.decagonhq.clads.util.ClientsListModel

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeFragmentYearDropdown: AutoCompleteTextView
    private lateinit var clientList: ArrayList<ClientsListModel>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            homeFragmentClientListRecyclerView.apply {
                populateClient()
                adapter = HomeFragmentClientsRecyclerAdapter(clientList)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
        }
        homeFragmentYearDropdown = binding.homeFragmentYearDropdownAutocompleteTextView
//        val items = arrayListOf(2020,2021,2022,2023,2016,2015,2014,2013,2012,2011,2010)
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
//        yearSpinner.adapter = adapter
//
       chartData(view)

    }

    override fun onResume() {
        super.onResume()
        /*Set up Account Category Dropdown*/
        val chartYear = resources.getStringArray(R.array.Year)
        val accountCategoriesArrayAdapter = ArrayAdapter(requireContext(), R.layout.chart_year_dropdown_item, chartYear)
        homeFragmentYearDropdown.setAdapter(accountCategoriesArrayAdapter)
    }

    // dummy data to populate the recycler view
    private fun populateClient() {
        clientList = arrayListOf(
            ClientsListModel("Ruth", "Unoka", "Lagos"),
            ClientsListModel("Ezekiel", "Olufemi", "Benin"),
            ClientsListModel("Olufemi", "Ogundipe", "Abeokuta"),
            ClientsListModel("Adebayo", "Kings", "Lagos"),
            ClientsListModel("Abdul", "Salawu", "Benin"),
            ClientsListModel("Hope", "Omoruyi", "Abeokuta")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
