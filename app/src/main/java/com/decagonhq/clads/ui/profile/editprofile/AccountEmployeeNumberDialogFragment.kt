package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.databinding.AccountEmployeeNumberDialogFragmentBinding

class AccountEmployeeNumberDialogFragment : DialogFragment() {

    private var _binding: AccountEmployeeNumberDialogFragmentBinding? = null
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
        _binding = AccountEmployeeNumberDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    var numberOfEmployeeInput = MutableLiveData<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // when the dialog cancel button isclicked
        binding.accountEmployeeNumberDialogFragmentCancelButton.setOnClickListener {
            dismiss()
        }
        // when the dialog ok button is clicked
        binding.accountEmployeeNumberDialogFragmentOkButton.setOnClickListener {
            val inputValue =
                binding.accountEmployeeNumberDialogFragmentEmployeeNumberEditTextView.text.toString()
            if (inputValue.isNotEmpty()) {
                numberOfEmployeeInput.value = inputValue
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
