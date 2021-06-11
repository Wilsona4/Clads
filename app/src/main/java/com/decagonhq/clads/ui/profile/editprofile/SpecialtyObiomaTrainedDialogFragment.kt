package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.databinding.SpecialtyObiomaTrainedDialogFragmentBinding

class SpecialtyObiomaTrainedDialogFragment : DialogFragment() {
    private var _binding: SpecialtyObiomaTrainedDialogFragmentBinding? = null
    private val binding get() = _binding!!

    var obiomaTrainedInputData = MutableLiveData<String>()

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
        _binding = SpecialtyObiomaTrainedDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.specialtyObiomaTrainedDialogFragmentCancelButton
            .setOnClickListener {
                dismiss()
            }
        binding.specialtyObiomaTrainedDialogFragmentOkButton
            .setOnClickListener {
                val selectedID =
                    binding.specialtyObiomaTrainedDialogFragmentRadioGroup.checkedRadioButtonId
                val radio = view.findViewById<RadioButton>(selectedID)
                val radioResult = radio.text.toString()
                obiomaTrainedInputData.value = radioResult

                dismiss()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
