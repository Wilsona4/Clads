package com.decagonhq.clads.ui.profile.bottomnav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.databinding.MessagesRecyclerViewItemBinding
import com.decagonhq.clads.util.MessagesNotificationModel

class MessagesFragmentClientsRecyclerAdapter(private var messageNotificationList: ArrayList<MessagesNotificationModel>) : RecyclerView.Adapter<MessagesFragmentClientsRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: MessagesRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val clientName = binding.messagesFragmentClientNameTextView
        val notificationBody = binding.messagesFragmentLoremIpsumTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MessagesRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messageNotificationList.size
    }

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
