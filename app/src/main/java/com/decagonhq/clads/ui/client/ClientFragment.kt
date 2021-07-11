package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.ClientFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ClientFragment : Fragment() {

    private var _binding: ClientFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var addClientFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ClientFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Initialize Views*/
        addClientFab = binding.clientFragmentAddClientFab

        addClientFab.setOnClickListener {
            findNavController().navigate(R.id.action_clientFragment_to_addClientFragment)
        }

        // setting up the layout manager for the recyclerview
        _binding?.clientListScreenRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        // connecting the adapter to the recyclerview
        _binding?.clientListScreenRecyclerView?.adapter = adapter

        displayRecyclerviewOrNoClientText()
    }

    private fun displayRecyclerviewOrNoClientText() {
        if (adapter.itemCount == 0) {
            _binding?.clientListScreenMaleEmoji?.isVisible = true
            _binding?.clientListScreenFemaleEmoji?.isVisible = true
            noClientText.isVisible = true
        } else {

            maleImage.isVisible = false
            femaleImage.isVisible = false
            noClientText.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
