package com.decagonhq.clads.ui.profile.editprofile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.profile.MeasurementOption
import com.decagonhq.clads.data.domain.profile.ShowroomAddress
import com.decagonhq.clads.data.domain.profile.Union
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.domain.profile.WorkshopAddress
import com.decagonhq.clads.databinding.SpecialtyFragmentBinding
import com.decagonhq.clads.ui.profile.adapter.SpecialtyAdapterClickListener
import com.decagonhq.clads.ui.profile.adapter.SpecialtyFragmentRecyclerAdapter
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments.Companion.createProfileDialogFragment
import com.decagonhq.clads.viewmodels.UserProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialtyFragment : Fragment(), SpecialtyAdapterClickListener {
    private var _binding: SpecialtyFragmentBinding? = null

    private val recyclerViewAdapter by lazy { SpecialtyFragmentRecyclerAdapter(this) }

    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    var selectedGenderFocus = arrayListOf<String>()

    var specialtyList = arrayListOf(
        "Yoruba Attires",
        "Hausa Attires",
        "Senator",
        "Embroidery",
        "Africa Fashion"
    )

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = SpecialtyFragmentBinding.inflate(inflater, container, false)
        retrieveSavedInfoFromApi()
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
        getSelectedGenderFocusItem()
        /*simpleCallBAck object is passed as a parameter of the itemTouchHelper object*/
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        /*attaching the item touch helper to our recycler view*/
        itemTouchHelper.attachToRecyclerView(binding.specialtyFragmentRecyclerView)

        binding.specialtyFragmentSaveChangesMaterialButton.setOnClickListener {
            updateUserProfile()
        }
    }

    private fun recyclerViewAdapterSetUp() {
        val recyclerView = binding.specialtyFragmentRecyclerView
        recyclerViewAdapter.specialtyList.addAll(specialtyList)
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
            recyclerViewAdapter.specialtyList.add(newSpecialty!!)
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

    private fun retrieveSavedInfoFromApi() {
        userProfileViewModel.userProfile.observe(viewLifecycleOwner, Observer {
            var list = it.data?.specialties
            specialtyList.addAll(list!!)
//            recyclerViewAdapter.populateList(list!!.toList())
            var genderFocusItem = it.data?.genderFocus!!
            for (i in genderFocusItem) {
                when {
                    i.trim().toLowerCase() == "male" -> {
                        binding.specialtyFragmentMaleCheckBox.isChecked = true
                    }
                    i.trim().toLowerCase() == "female" -> {
                        binding.specialtyFragmentFemaleCheckBox.isChecked = true
                    }
                    i.trim().toLowerCase() == "kids" -> {
                        binding.specialtyFragmentKidsCheckBox.isChecked = true
                    }
                    i.trim().toLowerCase() == "unisex" -> {
                        binding.specialtyFragmentUnisexCheckBox.isChecked = true
                    }
                }
            }

            if (it.data!!.trained) {
                binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.text = "Yes"
            }

            binding.specialtyFragmentVisitUsForYourMeasurementValueCheckBox.isChecked =
                it.data.measurementOption.visitForMeasurement!!

            binding.specialtyFragmentWillAcceptSelfMeasurementValueCheckBox.isChecked =
                it.data.measurementOption.acceptSelfMeasurement!!

            binding.specialtyFragmentDeliveryLeadTimeValueTextView.text =
                it.data.deliveryTime ?: "2 Weeks"
        })
    }

    private fun updateUserProfile() {
        userProfileViewModel.userProfile.observe(
            viewLifecycleOwner,
            Observer {
                binding.specialtyFragmentUploadProgressbar.isVisible = true
                it.data?.let { profile ->
                    val userProfile = UserProfile(
                        country = profile.country,
                        deliveryTime = binding.specialtyFragmentDeliveryLeadTimeValueTextView.text.toString(),
                        email = profile.email,
                        firstName = profile.firstName,
                        gender = profile.gender,
                        genderFocus = selectedGenderFocus.toList(),
                        lastName = profile.lastName,
                        measurementOption = MeasurementOption(
                            binding.specialtyFragmentVisitUsForYourMeasurementValueCheckBox.isChecked,
                            binding.specialtyFragmentWillAcceptSelfMeasurementValueCheckBox.isChecked
                        ),
                        phoneNumber = profile.phoneNumber,
                        role = profile.role,
                        workshopAddress = WorkshopAddress(
                            street = profile.workshopAddress?.street,
                            state = profile.workshopAddress?.state,
                            city = profile.workshopAddress?.city,
                        ),
                        showroomAddress = ShowroomAddress(
                            street = profile.showroomAddress?.street,
                            city = profile.showroomAddress?.city,
                            state = profile.showroomAddress?.state,
                        ),
                        specialties = specialtyList,
                        thumbnail = profile.thumbnail,
                        trained = true,
                        union = Union(
                            name = profile.union?.name,
                            ward = profile.union?.ward,
                            lga = profile.union?.lga,
                            state = profile.union?.state,
                        ),
                        paymentTerms = profile.paymentTerms,
                        paymentOptions = profile.paymentOptions
                    )
                    userProfileViewModel.updateUserProfile(userProfile)
                    binding.specialtyFragmentUploadProgressbar.isVisible = false

                }
            }
        )
    }

    private fun getSelectedGenderFocusItem() {

        when {
            binding.specialtyFragmentMaleCheckBox.isChecked -> {
                selectedGenderFocus.add("male")
            }
            binding.specialtyFragmentFemaleCheckBox.isChecked -> {
                selectedGenderFocus.add("female")
            }
            binding.specialtyFragmentKidsCheckBox.isChecked -> {
                selectedGenderFocus.add("kids")
            }
            binding.specialtyFragmentUnisexCheckBox.isChecked -> {
                selectedGenderFocus.add("unisex")
            }
        }
    }

    override fun onItemChecked(status: Boolean, position: Int) {
        if (!status) {
            specialtyList.removeAt(position)
        }
    }

    /*simpleCallBack method for swipping the recycler view items so as to update the info
    * dragdirection is zero because we are not dragging*/
    private var simpleCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or (ItemTouchHelper.RIGHT)) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        /*specifing the position of the contact*/
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            // adapterPosition
            var currentSpecialty = recyclerViewAdapter.specialtyList[position]
            // getting the current contact swipped

            /*checking the direction swipped */

            if (direction == ItemTouchHelper.LEFT) {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle("Are you sure you want to delete this specialty?")
                    it.setPositiveButton("Yes") { dialog, which ->
                        recyclerViewAdapter.specialtyList.removeAt(position)
                        binding.specialtyFragmentRecyclerView.adapter?.notifyItemRemoved(position)
                        // below line is to display our snackbar with action.
                        Snackbar.make(
                            binding.root,
                            currentSpecialty,
                            Snackbar.LENGTH_LONG
                        ).setAction("Undo",) {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            recyclerViewAdapter.specialtyList.add(position, currentSpecialty);

                            // below line is to notify item is
                            // added to our adapter class.
                            recyclerViewAdapter.notifyItemInserted(position);

                        }
                    }.create().show()
                }
                binding.specialtyFragmentRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

    }
}
