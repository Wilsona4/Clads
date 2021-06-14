package com.decagonhq.clads.ui.client.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AddMeasurementDialogFragmentBinding
import com.decagonhq.clads.databinding.EditMeasurementDialogFragmentBinding
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.ADD_MEASUREMENT_BUNDLE_KEY
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.ADD_MEASUREMENT_REQUEST_KEY
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.EDITED_MEASUREMENT_BUNDLE_KEY
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.EDITED_MEASUREMENT_REQUEST_KEY
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.EDIT_MEASUREMENT_BUNDLE_KEY
import com.decagonhq.clads.ui.client.MeasurementsFragment.Companion.EDIT_MEASUREMENT_BUNDLE_POSITION
import com.decagonhq.clads.model.DressMeasurementModel
import com.decagonhq.clads.util.ClientMeasurementData

class ClientManagementDialogFragments(
    private var dialogLayoutId: Int,
    private var bundle: Bundle? = null
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(dialogLayoutId, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Inflate Dialog Fragment Layouts based on Id*/
        when (dialogLayoutId) {
            /*Add Measurement Dialog Fragment*/
            R.layout.add_measurement_dialog_fragment -> {
                /*Initialise binding*/
                val binding = AddMeasurementDialogFragmentBinding.bind(view)
                /*Initializing Views*/
                val measurementNameEditText = binding.addAddressFragmentMeasurementNameEditText
                val measurementEditText = binding.addMeasurementFragmentAddMeasureEditText
                val addMeasurementButton = binding.addMeasurementFragmentAddMeasurementButton
                /*Add new measurement*/
                addMeasurementButton.setOnClickListener {
                    val measurementName = measurementNameEditText.text
                    val measurement = measurementEditText.text
                    when {
                        measurementName!!.trim().isEmpty() -> {
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
                            val bundle =
                                DressMeasurementModel(
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
                /*Validate Dialog Fields onTextChange*/
                measurementNameEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        measurementNameEditText.text!!.trim().isEmpty() -> {
                            binding.addAddressFragmentMeasurementNameEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.addAddressFragmentMeasurementNameEditTextLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.addAddressFragmentMeasurementNameEditTextLayout.error = null
                        }
                    }
                }
                measurementEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        measurementEditText.text!!.toString().trim().isEmpty() -> {
                            binding.addMeasurementFragmentAddMeasurementEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.addMeasurementFragmentAddMeasurementEditTextLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.addMeasurementFragmentAddMeasurementEditTextLayout.error = null
                        }
                    }
                }
            }
            /*Edit Measurement Dialog Fragment*/
            R.layout.edit_measurement_dialog_fragment -> {
                /*Initialise binding*/
                val binding = EditMeasurementDialogFragmentBinding.bind(view)
                /*Initializing Views*/
                val measurementNameEditText = binding.editAddressFragmentMeasurementNameEditText
                val measurementEditText = binding.editMeasurementFragmentAddMeasureEditText
                val editMeasurementButton = binding.editMeasurementFragmentSaveButton

                val itemPosition = bundle?.getInt(EDIT_MEASUREMENT_BUNDLE_POSITION)
                val retrievedMeasurement =
                    bundle?.getParcelable<DressMeasurementModel>(EDIT_MEASUREMENT_BUNDLE_KEY)

                /*Attaching the data*/
                measurementNameEditText.setText(retrievedMeasurement?.measurementName)
                measurementEditText.setText(retrievedMeasurement?.measurement.toString())

                /*Saving the changes for the measurement*/
                editMeasurementButton.setOnClickListener {
                    val measurementName = binding.editAddressFragmentMeasurementNameEditText.text
                    val measurement = binding.editMeasurementFragmentAddMeasureEditText.text

                    when {
                        measurementName!!.isEmpty() -> {
                            binding.editAddressFragmentMeasurementNameEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                        }
                        measurement.toString().isEmpty() -> {
                            binding.editMeasurementFragmentAddMeasurementEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.editMeasurementFragmentAddMeasurementEditTextLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            val editedDataModel =
                                DressMeasurementModel(
                                    measurementName.toString(),
                                    measurement.toString().toBigDecimal()
                                )
                            ClientMeasurementData.currentList[itemPosition!!] = editedDataModel
                            setFragmentResult(
                                EDITED_MEASUREMENT_REQUEST_KEY, bundleOf(
                                    EDITED_MEASUREMENT_BUNDLE_KEY to editedDataModel
                                )
                            )
                            dismiss()
                        }
                    }
                }

                /*Validate Dialog Fields onTextChange*/
                measurementNameEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        measurementNameEditText.text!!.trim().isEmpty() -> {
                            binding.editAddressFragmentMeasurementNameEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.editAddressFragmentMeasurementNameEditTextLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.editAddressFragmentMeasurementNameEditTextLayout.error = null
                        }
                    }
                }
                measurementEditText.doOnTextChanged { _, _, _, _ ->
                    when {
                        measurementEditText.text!!.toString().trim().isEmpty() -> {
                            binding.editMeasurementFragmentAddMeasurementEditTextLayout.error =
                                getString(
                                    R.string.required
                                )
                            binding.editMeasurementFragmentAddMeasurementEditTextLayout.errorIconDrawable =
                                null
                        }
                        else -> {
                            binding.editMeasurementFragmentAddMeasurementEditTextLayout.error = null
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun createClientDialogFragment(
            layoutId: Int,
            bundle: Bundle? = null
        ): ClientManagementDialogFragments {
            return ClientManagementDialogFragments(layoutId, bundle)
        }
    }
}
