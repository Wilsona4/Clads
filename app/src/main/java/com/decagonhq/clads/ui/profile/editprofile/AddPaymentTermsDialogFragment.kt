package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.decagonhq.clads.databinding.AddPaymentTermsDialogFragmentBinding

class AddPaymentTermsDialogFragment : DialogFragment() {

    // declaring my variables using view binding
    private var _binding: AddPaymentTermsDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var paymentTerms: TextView
    private lateinit var addPaymentTermsButton: Button

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
        _binding = AddPaymentTermsDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        paymentTerms = binding.addPaymentFragmentEditText
//        addPaymentTermsButton = binding.addPaymentFragmentAddButton
//
//        addPaymentTermsButton.setOnClickListener {
//            val newPaymentTerm = paymentTerms.text.toString()
//            /*Get New Payment Term and Send to Recycler View*/
//        }
    }

    companion object {
        fun newInstance() = AddPaymentTermsDialogFragment()
    }
}
