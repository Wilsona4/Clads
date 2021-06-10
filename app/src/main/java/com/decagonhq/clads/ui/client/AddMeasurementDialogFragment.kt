package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.decagonhq.clads.databinding.AddMeasurementDialogFragmentBinding
import com.decagonhq.clads.ui.client.model.DressMeasurementModel

class AddMeasurementDialogFragment : DialogFragment(){
    private var _binding: AddMeasurementDialogFragmentBinding? = null
    private lateinit var addMeasurementButton: Button
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddMeasurementDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addMeasurementButton = binding.addMeasurementFragmentAddMeasurementButton
        addMeasurementButton.setOnClickListener {
            val measurementName = binding.addAddressFragmentMeasurementNameEditText.text.toString()
            val measurement = binding.addMeasurementFragmentAddMeasureEditText.text.toString().toBigDecimal()
            val bundle = DressMeasurementModel(measurementName, measurement)
            setFragmentResult("keyClicked", bundleOf("bundleKey" to bundle))
            dismiss()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
