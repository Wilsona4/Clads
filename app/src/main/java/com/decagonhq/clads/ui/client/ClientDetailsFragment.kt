package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagonhq.clads.databinding.ClientDetailsFragmentBinding
import com.decagonhq.clads.ui.BaseFragment

class ClientDetailsFragment : BaseFragment() {
    private var _binding: ClientDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ClientDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}