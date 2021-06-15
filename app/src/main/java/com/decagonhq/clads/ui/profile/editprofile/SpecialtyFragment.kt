package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.SpecialtyFragmentBinding
import com.decagonhq.clads.model.SpecialtyModel
import com.decagonhq.clads.ui.profile.adapter.SpecialtyFragmentRecyclerAdapter
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments
import com.decagonhq.clads.viewmodels.ProfileManagementViewModel

class SpecialtyFragment : Fragment() {
    private var _binding: SpecialtyFragmentBinding? = null
    private lateinit var profileManagementViewModel: ProfileManagementViewModel

    private val recyclerViewAdapter by lazy { SpecialtyFragmentRecyclerAdapter() }

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = SpecialtyFragmentBinding.inflate(inflater, container, false)

        profileManagementViewModel =
            ViewModelProvider(requireActivity()).get(ProfileManagementViewModel::class.java)
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
        // when isTrained value is clicked
        binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.setOnClickListener {
            val obiomaTrainedFragment = SpecialtyObiomaTrainedDialogFragment()
            obiomaTrainedFragment.show(
                requireActivity().supportFragmentManager,
                "Obioma Trained fragment"
            )
            // collect input values from dialog fragment and update the gender value of user
            profileManagementViewModel.obiomaTrainedLiveData.observe(
                viewLifecycleOwner,
                {
                    binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.text =
                        it.toString()
                }
            )
        }
    }

    private fun addNewSpecialtyDialog() {
        binding.specialtyFragmentAddNewSpecialtyIcon.setOnClickListener {
            val specialtyAddFragment = SpecialtyAddSpecialtyDialogFragment()
            specialtyAddFragment.show(
                requireActivity().supportFragmentManager,
                "add new obioma specialty"
            )
            // add the input "new specialty" to the list for recycler view usage
            specialtyAddFragment.specialtyInput.observe(
                viewLifecycleOwner,
                {
                    list.add(SpecialtyModel(it.toString(), false))
                }
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
            ProfileManagementDialogFragments.createProfileDialogFragment(
                R.layout.specialty_delivery_time_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                AccountFragment::class.java.simpleName
            )
        }
    }

    var list = arrayListOf(
        SpecialtyModel("Yoruba Attire", false),
        SpecialtyModel("Hausa Attires", false),
        SpecialtyModel("Senator", false),
        SpecialtyModel("Embroidery", false),
        SpecialtyModel("Africa Fashion", false),
        SpecialtyModel("School Uniform", false),
        SpecialtyModel("Militery and Paramiltery Uniforms", false),
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
        const val CURRENT_SPECIAL_DELIVERY_TIME_BUNDLE_KEY = "CURRENT SPECIAL DELIVERY TIME BUNDLE KEY"
        const val CURRENT_SPECIAL_DELIVERY_RADIO_KEY = "CURRENT SPECIAL DELIVERY RADIO KEY"
    }
    }
