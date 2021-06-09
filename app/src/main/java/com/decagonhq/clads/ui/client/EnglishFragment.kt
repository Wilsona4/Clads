package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.clads.databinding.EnglishFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EnglishFragment : Fragment() {
    private var _binding: EnglishFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var addEnglishMeasurementFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = EnglishFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Initialize Views*/
        addEnglishMeasurementFab = binding.englishMeasurementFragmentAddMeasurementFab

        addEnglishMeasurementFab.setOnClickListener {
            /*Open Dialog Fragment*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
