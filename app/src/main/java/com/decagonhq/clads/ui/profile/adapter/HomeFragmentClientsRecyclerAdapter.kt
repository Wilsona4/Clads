package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.databinding.ClientsRecyclerViewItemBinding

class HomeFragmentClientsRecyclerAdapter(private var clientList: MutableList<Client>) : RecyclerView.Adapter<HomeFragmentClientsRecyclerAdapter.ViewHolder>() {
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

    fun updateList(clients: MutableList<Client>) {
        this.clientList = clients
        notifyDataSetChanged()
    }

    fun addItem(client: Client) {
        clientList.add(client)
        notifyDataSetChanged()
    }

    /*bind data with the view holder*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = clientList[position]
        holder.itemView.apply {
            with(holder) {
                with(clientList[position]) {
                    val fullName = this.fullName
                    clientName.text = fullName
                    clientLocation.text = this.deliveryAddresses?.get(0)?.city
                    val clientInitials = clientList[position].fullName.split(" ")[0].substring(0, 1) +
                        clientList[position].fullName.split(" ")[1].substring(0, 1)
                    val generator: ColorGenerator = ColorGenerator.MATERIAL
                    val color = generator.randomColor
                    val drawable = TextDrawable.builder().beginConfig()
                        .width(150)
                        .height(150)
                        .fontSize(55)
                        .endConfig()
                        .buildRound(clientInitials, color)

                    holder.binding.clientsRecyclerViewItemInitialCircleImageView.setImageDrawable(drawable)
                }
            }
        }
    }
}
