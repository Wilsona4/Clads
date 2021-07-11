package com.decagonhq.clads.ui.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.databinding.ClientFragmentBinding



class ClientListRecyclerViewAdapter(var clientList:List<Client>):RecyclerView.Adapter<ClientListRecyclerViewAdapter.ClientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val binding = ClientFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
     holder.itemView
    }

    override fun getItemCount(): Int {
       return clientList.size
    }

    private fun updateList(clientList:List<Client>){
        this.clientList = clientList
    }

    inner class ClientsViewHolder(val binding: ClientFragmentBinding): RecyclerView.ViewHolder(binding.root){
        val clientName = binding.
        val notificationBody = binding.messagesFragmentLoremIpsumTextView
    }
}