package com.decagonhq.clads.ui.client

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.databinding.ClientFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.client.adapter.ClientListRecyclerViewAdapter
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.viewmodels.ClientViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientFragment : BaseFragment() {

    private var _binding: ClientFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: ClientListRecyclerViewAdapter
    private val clientViewModel: ClientViewModel by activityViewModels()
    private var swiped = false
    private var itemToDeletePosition: Int? = null

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
        setObservers()
        setEventListeners()
    }

    private fun displayRecyclerviewOrNoClientText(clients: List<Client>) {
        if (clients.isEmpty()) {
            binding.clientListScreenMaleEmoji.isVisible = true
            binding.clientListScreenFemaleEmoji.isVisible = true
            binding.clientListScreenNoClientText.isVisible = true
            binding.clientListScreenRecyclerView.isVisible = false
        } else {
            adapter.updateList(clients.toMutableList())
            binding.clientListScreenRecyclerView.adapter?.notifyDataSetChanged()
            binding.clientListScreenMaleEmoji.isVisible = false
            binding.clientListScreenFemaleEmoji.isVisible = false
            binding.clientListScreenNoClientText.isVisible = false
            binding.clientListScreenRecyclerView.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {

        clientViewModel.client.observe(
            viewLifecycleOwner
        ) {
            it.data?.let { it1 -> displayRecyclerviewOrNoClientText(it1) }
        }

        clientViewModel.deleteClientResponse.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Success -> {

                    it.data?.payload?.let { it1 -> clientViewModel.deleteClientFromDb(it1) }
                }

                is Resource.Loading -> {
                    progressDialog.showDialogFragment("Deleting...")
                }

                is Resource.Error -> {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView())
                }
            }
        }

        clientViewModel.deleteFromDBResponse.observe(
            viewLifecycleOwner
        ) {

                when (it) {

                    is Resource.Success -> {
                        itemToDeletePosition?.let { it1 -> adapter.deleteItem(it1)
                            binding.clientListScreenRecyclerView.adapter?.notifyDataSetChanged()
                            progressDialog.hideProgressDialog()
                        }
                        Snackbar.make(
                            requireView(),
                            "Client Deleted Successfully",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is Resource.Error -> {
                    Snackbar.make(
                        requireView(),
                        it.message!!,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    }
                    else -> {}
                }

        }


        clientViewModel.addToDBResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { it1 -> adapter.addItem(it1) }
                    binding.clientListScreenRecyclerView.adapter?.notifyDataSetChanged()
                } else -> { }
            }

        }

    }

    private fun setEventListeners() {
        binding.clientFragmentAddClientFab.setOnClickListener {
            findNavController().navigate(R.id.action_clientFragment_to_addClientFragment)
        }
    }

    private fun init() {
        binding.clientListScreenRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ClientListRecyclerViewAdapter(mutableListOf())
        binding.clientListScreenRecyclerView.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            var icon: Drawable = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.client_delete_button
            )!!
            val background: ColorDrawable = ColorDrawable(Color.RED)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                itemToDeletePosition = viewHolder.adapterPosition
                adapter.getItem(itemToDeletePosition!!).id?.let { clientViewModel.deleteClient(it) }
                swiped = true
                progressDialog.showDialogFragment("Deleting Client...")

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                val itemView = viewHolder.itemView
                val backgroundCornerOffset = 20 // so background is behind the rounded corners of itemView

                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight

                if (dX > 0) { // Swiping to the right
                    val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
                    val iconRight = itemView.left + iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    background.setBounds(
                        itemView.left, itemView.top,
                        itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
                    )
                } else if (dX < 0) { // Swiping to the left
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    background.setBounds(
                        itemView.right + dX.toInt() - backgroundCornerOffset,
                        itemView.top, itemView.right, itemView.bottom
                    )
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0)
                }

                background.draw(c)
                icon.draw(c)
            }
        }).attachToRecyclerView(_binding?.clientListScreenRecyclerView)
    }
}
