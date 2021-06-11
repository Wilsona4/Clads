package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.databinding.AccountOtherNameDialogFragmentBinding

class AccountOtherNameDialogFragment : DialogFragment() {

    private var _binding: AccountOtherNameDialogFragmentBinding? = null
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
        _binding = AccountOtherNameDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    var otherNameInput = MutableLiveData<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // when the dialog cancel button isclicked
        binding.accountOtherNameDialogFragmentCancelButton.setOnClickListener {
            dismiss()
        }
        // when the dialog ok button is clicked
        binding.accountOtherNameDialogFragmentOkButton.setOnClickListener {
//            val selectedID = rootView.findViewById<RadioGroup>(R.id.account_gender_dialog_fragment_radio_group).checkedRadioButtonId
            val inputValue =
                binding.accountOtherNameDialogFragmentOtherNameEditTextView.text.toString()
            if (inputValue.isNotEmpty()) {
                otherNameInput.value = inputValue
            }
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
