package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.decagonhq.clads.databinding.SpecialtyDeliveryTimeDialogFragmentBinding
import com.decagonhq.clads.viewmodels.ProfileManagementViewModel

class SpecialtyDeliveryTimeDialogFragment : DialogFragment() {
    private var _binding: SpecialtyDeliveryTimeDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileManagementViewModel: ProfileManagementViewModel

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

        profileManagementViewModel =
            ViewModelProvider(requireActivity()).get(ProfileManagementViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.specialtyDeliveryTimeDialogFragmentCancelButton.setOnClickListener {
            dismiss()
        }
        binding.specialtyDeliveryTimeDialogFragmentOkButton.setOnClickListener {
            val inputNumber =
                binding.specialtyDeliveryTimeDialogFragmentDeliveryTimeEditTextView.text.toString()
            val inputDurationInteger =
                binding.specialtyDeliveryTimeDialogFragmentRadioGroup.checkedRadioButtonId
            val durationValue = view.findViewById<RadioButton>(inputDurationInteger).text.toString()

            if (inputNumber.isNotEmpty() && durationValue.isNotEmpty()) {
                profileManagementViewModel.deliveryValueInNumber.value = inputNumber
                profileManagementViewModel.deliveryDuration.value = durationValue
            }
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
