package com.decagonhq.clads.ui.profile.bottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.databinding.HomeFragmentBinding
import com.decagonhq.clads.util.ClientsListModel

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private lateinit var clientList: ArrayList<ClientsListModel>
    private lateinit var clientsAdapter: HomeFragmentClientsRecyclerAdapter
    private lateinit var clientsRecyclerView: RecyclerView

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

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

        clientsRecyclerView = binding.homeFragmentClientListRecyclerView
        populateClient()
        clientsAdapter = HomeFragmentClientsRecyclerAdapter(clientList)
        clientsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        clientsRecyclerView.adapter = clientsAdapter
    }

    private fun populateClient() {
        clientList = arrayListOf(
            ClientsListModel("Ruth", "Unoka", "Lagos"),
            ClientsListModel("Ezekiel", "Olufemi", "Benin"),
            ClientsListModel("Olufemi", "Ogundipe", "Abeokuta")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
