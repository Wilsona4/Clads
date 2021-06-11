package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.databinding.SpecialtyDeliveryTimeDialogFragmentBinding

class SpecialtyDeliveryTimeDialogFragment : DialogFragment() {
    private var _binding: SpecialtyDeliveryTimeDialogFragmentBinding? = null
    private val binding get() = _binding!!

    var deliveryValueInNumber = MutableLiveData<String>()
    var deliveryDuration = MutableLiveData<String>()

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
        _binding = SpecialtyDeliveryTimeDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.specialtyDeliveryTimeDialogFragmentCancelButton.setOnClickListener {
            dismiss()
        }
        binding.specialtyDeliveryTimeDialogFragmentOkButton.setOnClickListener {
            val inputNumber =
                binding.specialtyFirstNameDialogFragmentFirstNameEditTextView.text.toString()
            val inputDurationInteger =
                binding.specialtyDeliveryTimeDialogFragmentRadioGroup.checkedRadioButtonId
            val durationValue = view.findViewById<RadioButton>(inputDurationInteger)
            deliveryValueInNumber.value = inputNumber
            deliveryDuration.value = durationValue.toString()
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
