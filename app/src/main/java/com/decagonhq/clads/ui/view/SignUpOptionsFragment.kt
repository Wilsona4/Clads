package com.decagonhq.clads.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.FragmentSignUpOptionsBinding

class SignUpOptionsFragment : Fragment() {

    private var _binding: FragmentSignUpOptionsBinding? = null
    private val binding get() = _binding!!

    lateinit var emailSignUpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*Inflate the layout for this fragment*/
        _binding = FragmentSignUpOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailSignUpButton = binding.signUpOptionsFragmentSignUpWithEmailButton

        emailSignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpOptionsFragment_to_emailSignUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
