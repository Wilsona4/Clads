package com.decagonhq.clads.ui.client

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.databinding.ClientFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.client.adapter.ClientListRvAdapter
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.viewmodels.ClientViewModel
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientFragment : BaseFragment(), ClientListRvAdapter.Interaction {

    private var _binding: ClientFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var clientListRvAdapter: ClientListRvAdapter
    private val clientViewModel: ClientViewModel by activityViewModels()
    private val clientsRegisterViewModel: ClientsRegisterViewModel by activityViewModels()

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
        init()
        setEventListeners()
        setObservers()

        // implement swipe to refresh
        binding.clientFragmentSwipeRefreshLayout.setOnRefreshListener {
            clientViewModel.getClients()
            binding.clientFragmentSwipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        clientsRegisterViewModel.clearMeasurement()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayRecyclerviewOrNoClientText(clients: List<Client>) {
        if (clients.isEmpty()) {
            binding.clientListScreenMaleEmoji.isVisible = true
            binding.clientListScreenFemaleEmoji.isVisible = true
            binding.clientListScreenNoClientText.isVisible = true
            binding.clientListScreenRecyclerView.isVisible = false
        } else {
            clientListRvAdapter.submitList(clients)
            binding.clientListScreenRecyclerView.adapter?.notifyDataSetChanged()
            binding.clientListScreenMaleEmoji.isVisible = false
            binding.clientListScreenFemaleEmoji.isVisible = false
            binding.clientListScreenNoClientText.isVisible = false
            binding.clientListScreenRecyclerView.isVisible = true
        }
    }

    private fun setObservers() {

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
                    it.data?.let { clientList ->
                        displayRecyclerviewOrNoClientText(clientList)
                    }
                }
            }
        )
    }

    private fun setEventListeners() {
        binding.clientFragmentAddClientFab.setOnClickListener {
            findNavController().navigate(R.id.addClientFragment)
        }
    }

    private fun init() {
        binding.clientListScreenRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            clientListRvAdapter = ClientListRvAdapter(this@ClientFragment)
            adapter = clientListRvAdapter
        }

        /*Attach our Swipe Implementation to our Recycler View*/
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.clientListScreenRecyclerView)
    }

    private var simpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val swipedClient = clientListRvAdapter.differ.currentList[position]

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        AlertDialog.Builder(requireContext()).also { it ->
                            it.setTitle(R.string.delete_image_confirmation)
                            it.setPositiveButton(R.string.yes) { dialog, which ->
                                swipedClient.id?.let { id ->
                                    clientViewModel.deleteClient(id)
                                    progressDialog.showDialogFragment(getString(R.string.deleting_client))
                                }
                            }
                            it.setNegativeButton(R.string.no) { dialog, which ->
                                dialog.cancel()
                            }
                        }.create().show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        /*Edit Client*/
                    }
                }
                binding.clientListScreenRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

    override fun onItemSelected(position: Int, item: Client) {
        val selectedClient = clientListRvAdapter.differ.currentList[position]
        val action =
            ClientFragmentDirections.actionClientFragmentToClientDetailsFragment(selectedClient)
        findNavController().navigate(action)
    }
}
