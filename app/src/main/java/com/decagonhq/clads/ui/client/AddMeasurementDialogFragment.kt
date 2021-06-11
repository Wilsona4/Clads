package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AddMeasurementDialogFragmentBinding
import com.decagonhq.clads.ui.client.model.DressMeasurementModel
import com.decagonhq.clads.util.errorSnack
import com.google.android.material.snackbar.Snackbar

class AddMeasurementDialogFragment : DialogFragment() {
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
        // Adding new measurement
        addMeasurementButton = binding.addMeasurementFragmentAddMeasurementButton
        addMeasurementButton.setOnClickListener {
            val measurementName = binding.addAddressFragmentMeasurementNameEditText.text
            val measurement = binding.addMeasurementFragmentAddMeasureEditText.text

            if (measurementName!!.isEmpty()) {
                binding.addAddressFragmentMeasurementNameEditTextLayout.errorSnack(getString(R.string.enter_name_validation), Snackbar.LENGTH_LONG)
            } else if (measurement.toString().isEmpty()) {
                binding.addMeasurementFragmentAddMeasurementEditTextLayout.errorSnack(getString(R.string.enter_measurement_validation), Snackbar.LENGTH_LONG)
            } else {
                val bundle = DressMeasurementModel(measurementName.toString(), measurement.toString().toBigDecimal())
                setFragmentResult(getString(R.string.request_key_keyClicked), bundleOf(getString(R.string.key_bundleKey) to bundle))
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
