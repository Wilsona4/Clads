package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.SpecialtyModel
import com.decagonhq.clads.databinding.SpecialtyFragmentBinding
import com.decagonhq.clads.ui.profile.adapter.SpecialtyFragmentRecyclerAdapter
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments.Companion.createProfileDialogFragment

class SpecialtyFragment : Fragment() {
    private var _binding: SpecialtyFragmentBinding? = null

    private val recyclerViewAdapter by lazy { SpecialtyFragmentRecyclerAdapter() }

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = SpecialtyFragmentBinding.inflate(inflater, container, false)

//        profileManagementViewModel =
//            ViewModelProvider(requireActivity()).get(ProfileManagementViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapterSetUp()
        changeIfObiomaIsTrainedDialog()
        addNewSpecialtyDialog()
        addSpecialDeliveryTime()
    }

    private fun recyclerViewAdapterSetUp() {
        val recyclerView = binding.specialtyFragmentRecyclerView
        recyclerViewAdapter.populateList(list)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    // Gender Dialog
    private fun changeIfObiomaIsTrainedDialog() {
        // when delivery time value is clicked
        childFragmentManager.setFragmentResultListener(
            SPECIAL_OBIOMA_TRAINED_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the union name text of user
            val obiomaTrainedValue = bundle.getString(SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY)
            binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.text =
                obiomaTrainedValue
        }

        // when delivery time value is clicked
        binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.setOnClickListener {
            val currentObiomaTrainedValue =
                binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.text.toString()
            val bundle =
                bundleOf(CURRENT_SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY to currentObiomaTrainedValue)
            createProfileDialogFragment(
                R.layout.specialty_obioma_trained_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                SpecialtyFragment::class.java.simpleName
            )
        }
    }

    private fun addNewSpecialtyDialog() {
        // when delivery time value is clicked
        childFragmentManager.setFragmentResultListener(
            ADD_SPECIALTY_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the text of user
            val newSpecialty = bundle.getString(ADD_SPECIALTY_BUNDLE_KEY)
            list.add(SpecialtyModel(newSpecialty, false))
        }

        // when delivery time value is clicked
        binding.specialtyFragmentAddNewSpecialtyIcon.setOnClickListener {
            createProfileDialogFragment(
                R.layout.specialty_add_specialty_dialog_fragment
            ).show(
                childFragmentManager,
                SpecialtyFragment::class.java.simpleName
            )
        }
    }

    private fun addSpecialDeliveryTime() {
        // when delivery time value is clicked
        childFragmentManager.setFragmentResultListener(
            SPECIAL_DELIVERY_TIME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the union name text of user
            val specialtyDeliveryTime = bundle.getString(SPECIAL_DELIVERY_TIME_BUNDLE_KEY)
            binding.specialtyFragmentDeliveryLeadTimeValueTextView.text = specialtyDeliveryTime
        }

        // when delivery time value is clicked
        binding.specialtyFragmentDeliveryLeadTimeValueTextView.setOnClickListener {
            val currentDeliveryTime =
                binding.specialtyFragmentDeliveryLeadTimeValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_SPECIAL_DELIVERY_TIME_BUNDLE_KEY to currentDeliveryTime)
            createProfileDialogFragment(
                R.layout.specialty_delivery_time_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                SpecialtyFragment::class.java.simpleName
            )
        }
    }

    var list = arrayListOf(
        SpecialtyModel("Yoruba Attires", false),
        SpecialtyModel("Hausa Attires", false),
        SpecialtyModel("Senator", false),
        SpecialtyModel("Embroidery", false),
        SpecialtyModel("Africa Fashion", false),
        SpecialtyModel("School Uniform", false),
        SpecialtyModel("Military and Paramilitary Uniforms", false),
        SpecialtyModel("Igbo Attire", false),
        SpecialtyModel("South-south attire", false),
        SpecialtyModel("Kaftans", false),
        SpecialtyModel("Contemporary", false),
        SpecialtyModel("Western Fashion", false),
        SpecialtyModel("Caps", false)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SPECIAL_DELIVERY_TIME_REQUEST_KEY = "SPECIAL DELIVERY TIME REQUEST KEY"
        const val SPECIAL_DELIVERY_TIME_BUNDLE_KEY = "SPECIAL DELIVERY TIME BUNDLE KEY"
        const val CURRENT_SPECIAL_DELIVERY_TIME_BUNDLE_KEY =
            "CURRENT SPECIAL DELIVERY TIME BUNDLE KEY"

        const val SPECIAL_OBIOMA_TRAINED_REQUEST_KEY = "SPECIAL OBIOMA TRAINED REQUEST KEY"
        const val SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY = "SPECIAL OBIOMA TRAINED BUNDLE KEY"
        const val CURRENT_SPECIAL_OBIOMA_TRAINED_BUNDLE_KEY =
            "CURRENT SPECIAL OBIOMA TRAINED BUNDLE KEY"

        const val ADD_SPECIALTY_REQUEST_KEY = "SPECIAL OBIOMA TRAINED REQUEST KEY"
        const val ADD_SPECIALTY_BUNDLE_KEY = "SPECIAL OBIOMA TRAINED BUNDLE KEY"
    }
}
