package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.decagonhq.clads.databinding.ClientsRecyclerViewItemBinding
import com.decagonhq.clads.model.ClientsListModel

class HomeFragmentClientsRecyclerAdapter(private var clientList: ArrayList<ClientsListModel>) : RecyclerView.Adapter<HomeFragmentClientsRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ClientsRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var clientName = binding.clientsRecyclerViewItemClientNameTextView
        var clientLocation = binding.clientsRecyclerViewItemLocationTextView
        var clientInitials = binding.clientsRecyclerViewItemInitialCircleImageView
    }

    /*inflate the views*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ClientsRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /*return size of list*/
    override fun getItemCount(): Int {
        return clientList.size
    }

    /*bind data with the view holder*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = clientList[position]
        holder.itemView.apply {
            with(holder) {
                with(clientList[position]) {
                    val fullName = "$firstName $lastName"
                    clientName.text = fullName
                    clientLocation.text = location

                    val generator: ColorGenerator = ColorGenerator.MATERIAL
                    val color = generator.randomColor
                    val drawable = TextDrawable.builder().beginConfig()
                        .width(150)
                        .height(150)
                        .fontSize(55)
                        .endConfig()
                        .buildRound("${model.firstName.substring(0, 1)}${model.lastName.substring(0, 1)}", color)

                    clientInitials.setImageDrawable(drawable)
                }
            }
        }
    }
}
