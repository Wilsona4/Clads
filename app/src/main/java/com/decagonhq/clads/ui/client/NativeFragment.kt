package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.clads.databinding.NativeFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NativeFragment : Fragment() {
    private var _binding: NativeFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var addNativeMeasurementFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = NativeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Initialize Views*/
        addNativeMeasurementFab = binding.nativeMeasurementFragmentAddMeasurementFab

        addNativeMeasurementFab.setOnClickListener {
            /*Open Dialog Fragment*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
