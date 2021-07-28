package com.decagonhq.clads.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.domain.MessagesNotificationModel
import com.decagonhq.clads.databinding.MessagesFragmentBinding
import com.decagonhq.clads.ui.profile.adapter.MessagesFragmentClientsRecyclerAdapter

class MessagesFragment : Fragment() {

    private var _binding: MessagesFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: MessagesFragmentClientsRecyclerAdapter
    private lateinit var messageNotificationList: ArrayList<MessagesNotificationModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MessagesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNotification()
        notificationRecyclerView = binding.messagesFragmentClientMessagesRecyclerView
        notificationAdapter = MessagesFragmentClientsRecyclerAdapter(messageNotificationList)
        notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        notificationRecyclerView.adapter = notificationAdapter
    }

    private fun getNotification() {
        messageNotificationList = arrayListOf(
            MessagesNotificationModel(
                "Ola",
                "Michavelli",
                "Today",
                "Lorem Ipsum"
            ),
            MessagesNotificationModel(
                "Ruth",
                "Unoka",
                "Yesterday",
                "My name is Ruth. I need a dress for sunday"
            ),
            MessagesNotificationModel(
                "Michael",
                "Isesele",
                "Yesterday",
                "Hi, I have an event next month"
            ),
            MessagesNotificationModel(
                "Ola",
                "Michavelli",
                "Today",
                "Lorem Ipsum"
            ),
            MessagesNotificationModel(
                "Ruth",
                "Unoka",
                "Yesterday",
                "My name is Ruth. I need a dress for sunday"
            ),
            MessagesNotificationModel(
                "Michael",
                "Isesele",
                "Yesterday",
                "Hi, I have an event next month"
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
