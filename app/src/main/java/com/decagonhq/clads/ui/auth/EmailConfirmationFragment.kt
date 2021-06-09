package com.decagonhq.clads.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.decagonhq.clads.databinding.EmailConfirmationFragmentBinding

class EmailConfirmationFragment : Fragment() {
    private var _binding: EmailConfirmationFragmentBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<EmailConfirmationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = EmailConfirmationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Navigate to Login Screen After Confirmation*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
