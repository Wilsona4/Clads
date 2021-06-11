package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.databinding.AccountGenderDialogFragmentBinding

class AccountGenderDialogFragment : DialogFragment() {
    private var _binding: AccountGenderDialogFragmentBinding? = null
    private val binding get() = _binding!!

    var genderInputData = MutableLiveData<String>()

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
        _binding = AccountGenderDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accountGenderDialogFragmentCancelButton.setOnClickListener {
            dismiss()
        }
        binding.accountGenderDialogFragmentOkButton.setOnClickListener {
            val selectedID = binding.accountGenderDialogFragmentRadioGroup.checkedRadioButtonId
            val resultValue = view.findViewById<RadioButton>(selectedID).text.toString()
            genderInputData.value = resultValue
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
