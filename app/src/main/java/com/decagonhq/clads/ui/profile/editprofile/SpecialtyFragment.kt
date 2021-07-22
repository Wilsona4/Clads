package com.decagonhq.clads.ui.profile.editprofile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.profile.MeasurementOption
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.databinding.SpecialtyFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.profile.adapter.SpecialtyFragmentRecyclerAdapter
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments.Companion.createProfileDialogFragment
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.util.observeOnce
import com.decagonhq.clads.viewmodels.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialtyFragment : BaseFragment() {
    private var _binding: SpecialtyFragmentBinding? = null
    private val recyclerViewAdapter by lazy { SpecialtyFragmentRecyclerAdapter() }
    private val userProfileViewModel: UserProfileViewModel by activityViewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = SpecialtyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapterSetUp()
        changeIfObiomaIsTrainedDialog()
        addNewSpecialtyDialog()
        addSpecialDeliveryTime()
        savedInfoFromApi()
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
//        recyclerViewAdapter.specialtyList.addAll(specialtyList)
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
            ADD_NEW_SPECIALTY_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the text of user
            val newSpecialty = bundle.getString(ADD_NEW_SPECIALTY_BUNDLE_KEY)
            if (newSpecialty != null) {
                recyclerViewAdapter.addNewSpecialty(newSpecialty)
            }
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

    /**
     * the viewModel is observed for available user profile information.
     * since not all the user profile information is to be update in this fragment
     * only the required information is updated, the rest is obtained from the viewmodel
     * the already avialable information fromis used to
     */
    private fun savedInfoFromApi() {
        userProfileViewModel.userProfile.observe(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading && it.data?.firstName.isNullOrEmpty()) {
                    progressDialog.showDialogFragment("Updating..")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                } else {
                    progressDialog.hideProgressDialog()
                    it.data?.specialties?.toMutableList()?.let { list ->
                        recyclerViewAdapter.populateList(
                            list
                        )
                    }
                    it.data?.genderFocus?.let { genderFocusItem ->
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
                    }
                    binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.text =
                        getString(R.string.yes)
                    it.data?.measurementOption?.let { measurementOption ->
                        binding.specialtyFragmentVisitUsForYourMeasurementValueCheckBox.isChecked =
                            measurementOption.visitForMeasurement ?: false
                        binding.specialtyFragmentWillAcceptSelfMeasurementValueCheckBox.isChecked =
                            measurementOption.acceptSelfMeasurement ?: false
                    }
                    it.data?.deliveryTime?.let { deliveryTime ->
                        binding.specialtyFragmentDeliveryLeadTimeValueTextView.text = deliveryTime
                    }
                }
            }
        )
    }

    /**
     * Updates views in the specialty fragment based on the users already saved information
     * in the remote database which is obtained by observing retrieved users information in the viewModel
     */
    private fun updateUserProfile() {
        userProfileViewModel.userProfile.observeOnce(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading && it.data?.firstName.isNullOrEmpty()) {
                    progressDialog.showDialogFragment("Updating...")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                } else {
                    progressDialog.hideProgressDialog()
                    it.data?.let { profile ->
                        val userProfile = UserProfile(
                            country = profile.country,
                            deliveryTime = binding.specialtyFragmentDeliveryLeadTimeValueTextView.text.toString(),
                            email = profile.email,
                            firstName = profile.firstName,
                            gender = profile.gender,
                            genderFocus = getSelectedGenderFocusItem(),
                            lastName = profile.lastName,
                            measurementOption = MeasurementOption(
                                binding.specialtyFragmentVisitUsForYourMeasurementValueCheckBox.isChecked,
                                binding.specialtyFragmentWillAcceptSelfMeasurementValueCheckBox.isChecked
                            ),
                            phoneNumber = profile.phoneNumber,
                            role = profile.role,
                            workshopAddress = profile.workshopAddress,
                            showroomAddress = profile.showroomAddress,
                            specialties = recyclerViewAdapter.specialtyList,
                            thumbnail = profile.thumbnail,
                            trained = binding.specialtyFragmentObiomaTrainedAndCertifiedValueTextView.text.toString()
                                .toLowerCase() == "yes",
                            union = profile.union,
                            paymentTerms = profile.paymentTerms,
                            paymentOptions = profile.paymentOptions
                        )
                        userProfileViewModel.updateUserProfile(userProfile)
                    }
                }
            }
        )
    }

    /**
     * Checks which genderFocus option is selected and adds the gender to an arrayList
     * @return [ArrayList<String>] containing the gender selected
     */
    private fun getSelectedGenderFocusItem(): ArrayList<String> {
        var selectedGenderFocus = arrayListOf<String>()
        if (binding.specialtyFragmentMaleCheckBox.isChecked) {
            selectedGenderFocus.add("male")
        }
        if (binding.specialtyFragmentFemaleCheckBox.isChecked) {
            selectedGenderFocus.add("female")
        }
        if (binding.specialtyFragmentKidsCheckBox.isChecked) {
            selectedGenderFocus.add("kids")
        }
        if (binding.specialtyFragmentUnisexCheckBox.isChecked) {
            selectedGenderFocus.add("unisex")
        }
        return selectedGenderFocus
    }

    /**
     * simpleCallBack method for swiping the recycler view items so as to update the specialty info
     * drag direction is zero because we are not dragging
     * the swipe direction is only set to right, because we only want the user to
     * only perform one action on the recyclerview item i.e delete inputed item
     *
     *
     */
    private var simpleCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        /*specifying the position of the contact*/
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // adapterPosition
            val position = viewHolder.adapterPosition
            // getting the current contact swiped
            val currentSpecialty = recyclerViewAdapter.specialtyList[position]
            /*checking the direction swipped */
            if (direction == ItemTouchHelper.RIGHT) {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle("Are you sure you want to delete this specialty?")
                    it.setPositiveButton("Yes") { dialog, which ->
                        // below is to delete an item from the array list at a position
                        // the item was swiped.
                        recyclerViewAdapter.removeSpecialty(position)
                        // below line is to display our snackbar with action.
                    }
                    it.setNegativeButton(R.string.no) { dialog, which ->
                        dialog.cancel()
                    }
                }.create().show()
                binding.specialtyFragmentRecyclerView.adapter?.notifyDataSetChanged()
            }
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
        const val ADD_NEW_SPECIALTY_REQUEST_KEY = "NANANA REQUEST KEY"
        const val ADD_NEW_SPECIALTY_BUNDLE_KEY = "NANANA BUNDLE KEY"
    }
}
