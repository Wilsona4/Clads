package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.databinding.SpecialtyAddSpecialtyDialogFragmentBinding

class SpecialtyAddSpecialtyDialogFragment : DialogFragment() {
    private var _binding: SpecialtyAddSpecialtyDialogFragmentBinding? = null
    private val binding get() = _binding!!

    var specialtyInput = MutableLiveData<String>()

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
        _binding = SpecialtyAddSpecialtyDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // when the dialog "add" button is clicked
        binding.specialtyAddSpecialtyDialogFragmentAddButton.setOnClickListener {
            val inputValue =
                binding.specialtyAddSpecialtyDialogFragmentAddSpecialtyEditText.text.toString()
            specialtyInput.value = inputValue
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
