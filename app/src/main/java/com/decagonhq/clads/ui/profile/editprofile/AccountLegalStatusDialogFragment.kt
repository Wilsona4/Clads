package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.databinding.AccountLegalStatusDialogFragmentBinding

class AccountLegalStatusDialogFragment : DialogFragment() {

    private var _binding: AccountLegalStatusDialogFragmentBinding? = null
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
        _binding = AccountLegalStatusDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    var legalStatusInput = MutableLiveData<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accountLegalStatusDialogFragmentCancelButton.setOnClickListener {
            dismiss()
        }
        binding.accountLegalStatusDialogFragmentOkButton.setOnClickListener {
            val selectedID = binding.accountLegalStatusDialogFragmentRadioGroup.checkedRadioButtonId
            val resultValue = view.findViewById<RadioButton>(selectedID).text.toString()

            if (resultValue.isNotEmpty()) {
                legalStatusInput.value = resultValue
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
