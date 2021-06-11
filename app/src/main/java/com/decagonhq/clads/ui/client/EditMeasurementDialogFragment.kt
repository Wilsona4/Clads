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
import com.decagonhq.clads.databinding.EditMeasurementDialogFragmentBinding
import com.decagonhq.clads.ui.client.model.DressMeasurementModel
import com.google.android.material.textfield.TextInputEditText
import kotlin.properties.Delegates

class EditMeasurementDialogFragment(data: Bundle) : DialogFragment() {

    private var _binding: EditMeasurementDialogFragmentBinding? = null
    private lateinit var editMeasurementButton: Button
    private lateinit var measurementName: TextInputEditText
    private lateinit var measurementNumber: TextInputEditText
    val editData = data
    private var position by Delegates.notNull<Int>()
    private lateinit var editDataModel: DressMeasurementModel

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
        position = editData?.getInt(getString(R.string.key_position))
        editDataModel = editData?.getParcelable(getString(R.string.key_editedData))!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = EditMeasurementDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        measurementName = binding.editAddressFragmentMeasurementNameEditText
        measurementNumber = binding.editMeasurementFragmentAddMeasureEditText
        /*Attaching the data*/
//        val editDataModel = editData["editData"]
//        val position = editData["position"]
        measurementName.setText(editDataModel.measurementName)
        measurementNumber.setText(editDataModel.measurement.toString())

        // Saving the changes for the measurement
        editMeasurementButton = binding.editMeasurementFragmentSaveButton
        editMeasurementButton.setOnClickListener {
            val measurementName = binding.editAddressFragmentMeasurementNameEditText.text.toString()
            val measurement = binding.editMeasurementFragmentAddMeasureEditText.text.toString().toBigDecimal()
            val dataModel = DressMeasurementModel(measurementName, measurement)
            var bundle = bundleOf(
                getString(R.string.key_editedData) to dataModel,
                getString(R.string.key_position) to position
            )
            setFragmentResult(getString(R.string.request_key_keyClicked2), bundle)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
