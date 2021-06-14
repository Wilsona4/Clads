package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AddMeasurementDialogFragmentBinding
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.ADD_MEASUREMENT_BUNDLE_KEY
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.ADD_MEASUREMENT_REQUEST_KEY
import com.decagonhq.clads.ui.client.model.DressMeasurementModel

class ClientManagementDialogFragments(private var dialogLayoutId: Int) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Inflate Dialog Fragment Layouts based on Id*/
        when (dialogLayoutId) {
            /*Add Measurement Dialog Fragment*/
            R.layout.add_measurement_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AddMeasurementDialogFragmentBinding.bind(view)
                // Adding new measurement
                val addMeasurementButton = binding.addMeasurementFragmentAddMeasurementButton
                addMeasurementButton.setOnClickListener {
                    val measurementName = binding.addAddressFragmentMeasurementNameEditText.text
                    val measurement = binding.addMeasurementFragmentAddMeasureEditText.text
                    when {
                        measurementName!!.isEmpty() -> {
                            binding.addAddressFragmentMeasurementNameEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.addAddressFragmentMeasurementNameEditTextLayout.errorIconDrawable =
                                null
                            return@setOnClickListener
                        }
                        measurement.toString().isEmpty() -> {
                            binding.addMeasurementFragmentAddMeasurementEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.addMeasurementFragmentAddMeasurementEditTextLayout.errorIconDrawable =
                                null
                            return@setOnClickListener
                        }
                        else -> {
                            val bundle = DressMeasurementModel(
                                measurementName.toString(),
                                measurement.toString().toBigDecimal()
                            )
                            setFragmentResult(
                                ADD_MEASUREMENT_REQUEST_KEY,
                                bundleOf(ADD_MEASUREMENT_BUNDLE_KEY to bundle)
                            )
                            dismiss()
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun createClientDialogFragment(layoutId: Int): ClientManagementDialogFragments {

            return ClientManagementDialogFragments(layoutId)
        }
    }
}
