package com.decagonhq.clads.ui.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.EditMeasurementFragmentBinding
import com.decagonhq.clads.ui.client.model.DressMeasurementModel
import com.google.android.material.textfield.TextInputEditText


class EditMeasurementDialogFragment : DialogFragment() {

    private var _binding: EditMeasurementFragmentBinding? = null
    private lateinit var editMeasurementButton: Button
    private lateinit var measurementName: TextInputEditText
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = EditMeasurementFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editMeasurementButton = binding.editMeasurementFragmentSaveButton
        editMeasurementButton.setOnClickListener {
            val measurementName = binding.editAddressFragmentMeasurementNameEditText.text.toString()
            val measurement = binding.editMeasurementFragmentAddMeasureEditText.text.toString().toBigDecimal()
            val action = EditMeasurementDialogFragmentDirections
                .actionEditMeasurementDialogFragmentToMeasurementsFragment2(DressMeasurementModel(measurementName, measurement))
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}