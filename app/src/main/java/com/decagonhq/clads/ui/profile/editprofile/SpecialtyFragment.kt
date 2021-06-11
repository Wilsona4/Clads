package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagonhq.clads.databinding.SpecialtyFragmentBinding
import com.decagonhq.clads.util.Specialty

class SpecialtyFragment : Fragment() {
    private var _binding: SpecialtyFragmentBinding? = null

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapterSetUp()
        changeIfObiomaIsTrainedDialog()
        addNewSpecialtyDialog()
        addSpeialdelivaryTime()
    }

    fun recyclerViewAdapterSetUp() {
        val recyclerView = binding.specialtyFragmentRecyclerView
        recyclerViewAdapter.populateList(list)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    // Gender Dialog
    fun changeIfObiomaIsTrainedDialog() {
        // when isTrained value is clicked
        binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.setOnClickListener {
            val obiomaTrainedFragment = SpecialtyObiomaTrainedDialogFragment()
            obiomaTrainedFragment.show(
                requireActivity().supportFragmentManager,
                "Obioma Trained fragment"
            )
            val obiomaTrainedFragmentInput = obiomaTrainedFragment.obiomaTrainedInputData
            // collect input values from dialog fragment and update the gender value of user
            obiomaTrainedFragmentInput.observe(
                viewLifecycleOwner,
                {
                    binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.text =
                        it.toString()
                }
            )
        }
    }

    fun addNewSpecialtyDialog() {
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
                    list.add(Specialty(it.toString(), false))
                }
            )
        }
    }

    fun addSpeialdelivaryTime() {
        binding.specialtyFragmentDeliveryLeadTimeValueTextView.setOnClickListener {
            val specialtyDeliveryTime = SpecialtyDeliveryTimeDialogFragment()
            specialtyDeliveryTime.show(
                requireActivity().supportFragmentManager,
                "add new obioma specialty"
            )
            val specialtyAddFragmentInput = specialtyDeliveryTime
        }
    }

    var list = arrayListOf(
        Specialty("Yoruba Attire", false),
        Specialty("Hausa Attires", false),
        Specialty("Senator", false),
        Specialty("Embroidery", false),
        Specialty("Africa Fashion", false),
        Specialty("School Uniform", false),
        Specialty("Militery and Paramiltery Uniforms", false),
        Specialty("Igbo Attire", false),
        Specialty("South-south attire", false),
        Specialty("Kaftans", false),
        Specialty("Contemporary", false),
        Specialty("Western Fashion", false),
        Specialty("Caps", false)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
