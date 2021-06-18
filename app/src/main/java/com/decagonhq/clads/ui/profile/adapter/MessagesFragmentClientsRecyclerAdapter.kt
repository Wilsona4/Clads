package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.domain.MessagesNotificationModel
import com.decagonhq.clads.databinding.MessagesRecyclerViewItemBinding

class MessagesFragmentClientsRecyclerAdapter(private var messageNotificationList: ArrayList<MessagesNotificationModel>) : RecyclerView.Adapter<MessagesFragmentClientsRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MessagesRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val clientName = binding.messagesFragmentClientNameTextView
        val notificationBody = binding.messagesFragmentLoremIpsumTextView
    }
    /*inflate the views*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MessagesRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /*return the messageNotificationList*/
    override fun getItemCount(): Int {
        return messageNotificationList.size
    }

    /*bind the views with their holders*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            with(holder) {
                with(messageNotificationList[position]) {
                    val clientFullName = "$firstName $lastName"
                    clientName.text = clientFullName
                    notificationBody.text = body
                }
            }
        }
    }
}
