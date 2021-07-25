package com.decagonhq.clads.ui.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.databinding.ClientsRecyclerViewItemBinding

class ClientListRecyclerViewAdapter(private var clientList: MutableList<Client>) : RecyclerView.Adapter<ClientListRecyclerViewAdapter.ClientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val binding = ClientsRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        holder.binding.clientsRecyclerViewItemClientNameTextView.text = clientList[position].fullName
        holder.binding.clientsRecyclerViewItemLocationTextView.text = clientList[position].deliveryAddresses?.get(0)?.city
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

    override fun getItemCount(): Int {
        return clientList.size
    }

    fun updateList(clientList: MutableList<Client>) {
        this.clientList = clientList
        notifyDataSetChanged()
    }

    fun deleteItem(itemPosition: Int) {
        this.clientList.removeAt(itemPosition)
    }

    fun addItem(client: Client) {
        this.clientList.add(client)
        notifyDataSetChanged()
    }

    fun getItem(itemPosition: Int): Client {
        return this.clientList[itemPosition]
    }

    inner class ClientsViewHolder(val binding: ClientsRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root)
}
