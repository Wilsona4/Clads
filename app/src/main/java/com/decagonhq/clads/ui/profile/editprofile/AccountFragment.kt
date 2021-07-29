package com.decagonhq.clads.ui.profile.editprofile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.data.domain.profile.ShowroomAddress
import com.decagonhq.clads.data.domain.profile.Union
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.domain.profile.WorkshopAddress
import com.decagonhq.clads.data.local.UserProfileEntity
import com.decagonhq.clads.databinding.AccountFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments.Companion.createProfileDialogFragment
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.util.observeOnce
import com.decagonhq.clads.util.saveBitmap
import com.decagonhq.clads.util.uriToBitmap
import com.decagonhq.clads.viewmodels.ImageUploadViewModel
import com.decagonhq.clads.viewmodels.UserProfileViewModel
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber

@AndroidEntryPoint
class AccountFragment : BaseFragment() {
    private var _binding: AccountFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val imageUploadViewModel: ImageUploadViewModel by activityViewModels()
    private val userProfileViewModel: UserProfileViewModel by activityViewModels()
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*Dialog fragment functions*/
        accountFirstNameEditDialog()
        accountGenderSelectDialog()
        accountUnionStateDialogFragment()
        accountUnionLGADialogFragment()
        accountUnionWardDialogFragment()
        accountUnionNameDialogFragment()
        accountLastNameDialogFragment()
        accountEmployeeNumberDialogFragment()
        accountShowRoomAddressDialog()
        accountWorkshopStreetDialog()
        accountWorkshopCityDialog()
        accountWorkshopStateDialog()
        accountOtherNameEditDialog()
//        accountLegalStatusDialog()

        /*Initialize Image Cropper*/
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
            it?.let { uri ->
                binding.accountFragmentEditProfileIconImageView.imageAlpha = 140
                uploadImageToServer(uri)
            }
        }

        /*Select profile image*/
        binding.accountFragmentEditProfileIconImageView.setOnClickListener {
            Manifest.permission.READ_EXTERNAL_STORAGE.checkForPermission(NAME, READ_IMAGE_STORAGE)
        }

        /* Update User Profile */
        binding.accountFragmentSaveChangesButton.setOnClickListener {
            updateUserProfile()
        }

        /*Get users profile*/

        userProfileViewModel.getLocalDatabaseUserProfile()
        getUserProfile()
    }

    /*Get User Profile*/
    private fun getUserProfile() {
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
                    it.data?.let { userProfile ->
                        binding.apply {
                            accountFragmentFirstNameValueTextView.text = userProfile.firstName
                            accountFragmentLastNameValueTextView.text = userProfile.lastName
                            accountFragmentPhoneNumberValueTextView.text = userProfile.phoneNumber
                            accountFragmentGenderValueTextView.text = userProfile.gender
                            accountFragmentStateValueTextView.text =
                                userProfile.workshopAddress?.state ?: getString(R.string.lagos)
                            accountFragmentWorkshopAddressCityValueTextView.text =
                                userProfile.workshopAddress?.city
                                ?: getString(R.string.lagos)
                            accountFragmentWorkshopAddressStreetValueTextView.text =
                                userProfile.workshopAddress?.street
                                ?: getString(R.string.enter_address)
                            accountFragmentShowroomAddressValueTextView.text =
                                userProfile.showroomAddress?.state
                                ?: getString(R.string.enter_address)
                            accountFragmentNameOfUnionValueTextView.text = userProfile.union?.name
                                ?: getString(R.string.enter_union_name)
                            accountFragmentWardValueTextView.text = userProfile.union?.ward
                                ?: getString(R.string.enter_union_ward)
                            accountFragmentLocalGovtAreaValueTextView.text = userProfile.union?.lga
                                ?: getString(R.string.enter_union_resource)
                            accountFragmentStateValueTextView.text = userProfile.union?.state
                                ?: getString(R.string.enter_union_resource)
                            Glide.with(this@AccountFragment)
                                .load(userProfile.thumbnail)
                                .placeholder(R.drawable.nav_drawer_profile_avatar)
                                .into(binding.accountFragmentEditProfileIconImageView)
                        }
                    }
                }
            }
        )
    }

    /*Update User Profile*/
    private fun updateUserProfile() {
        userProfileViewModel.userProfile.observeOnce(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading && it.data?.firstName.isNullOrEmpty()) {
                    progressDialog.showDialogFragment("Updating..")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                } else {
                    progressDialog.hideProgressDialog()
                    showToast("Update Successfull")
                    it.data?.let { profile ->
                        val userProfile = UserProfile(
                            country = profile.country,
                            deliveryTime = profile.deliveryTime,
                            email = profile.email,
                            firstName = binding.accountFragmentFirstNameValueTextView.text.toString(),
                            gender = binding.accountFragmentGenderValueTextView.text.toString(),
                            genderFocus = profile.genderFocus,
                            lastName = binding.accountFragmentLastNameValueTextView.text.toString(),
                            measurementOption = profile.measurementOption,
                            phoneNumber = binding.accountFragmentPhoneNumberValueTextView.text.toString(),
                            role = profile.role,
                            workshopAddress = WorkshopAddress(
                                street = binding.accountFragmentWorkshopAddressStreetValueTextView.text.toString(),
                                state = binding.accountFragmentShowroomAddressValueTextView.text.toString(),
                                city = binding.accountFragmentWorkshopAddressCityValueTextView.text.toString(),
                            ),
                            showroomAddress = ShowroomAddress(
                                street = binding.accountFragmentWorkshopAddressCityValueTextView.text.toString(),
                                city = binding.accountFragmentWorkshopAddressCityValueTextView.text.toString(),
                                state = binding.accountFragmentShowroomAddressValueTextView.text.toString(),
                            ),
                            specialties = profile.specialties,
                            thumbnail = profile.thumbnail,
                            trained = profile.trained,
                            union = Union(
                                name = binding.accountFragmentNameOfUnionValueTextView.text.toString(),
                                ward = binding.accountFragmentWardValueTextView.text.toString(),
                                lga = binding.accountFragmentLocalGovtAreaValueTextView.text.toString(),
                                state = binding.accountFragmentStateValueTextView.text.toString(),
                            ),
                            paymentTerms = profile.paymentTerms,
                            paymentOptions = profile.paymentOptions
                        )

                        userProfileViewModel.updateUserProfile(userProfile)
                    }
                }
            }
        )
    }

    /*Update User Profile Picture*/
    private fun updateUserProfilePicture(downloadUri: String) {
        userProfileViewModel.userProfile.observeOnce(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading<UserProfileEntity>) {
                    progressDialog.showDialogFragment("Uploading...")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                } else {
//                    progressDialog.hideProgressDialog()
                    it.data?.let { profile ->
                        val userProfile = UserProfile(
                            country = profile.country,
                            deliveryTime = profile.deliveryTime,
                            email = profile.email,
                            firstName = profile.firstName,
                            gender = profile.gender,
                            genderFocus = profile.genderFocus,
                            lastName = profile.lastName,
                            measurementOption = profile.measurementOption,
                            phoneNumber = profile.phoneNumber,
                            role = profile.role,
                            workshopAddress = profile.workshopAddress,
                            showroomAddress = profile.showroomAddress,
                            specialties = profile.specialties,
                            thumbnail = downloadUri,
                            trained = profile.trained,
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

    /*Check for Gallery Permission*/
    private fun String.checkForPermission(name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    this
                ) == PackageManager.PERMISSION_GRANTED -> {

                    cropActivityResultLauncher.launch(null)
                }
                shouldShowRequestPermissionRationale(this) -> showDialog(this, name, requestCode)
                else -> ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(this),
                    requestCode
                )
            }
        }
    }

    // check for permission and make call
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showToast("$name permission refused")
            } else {
                showToast("$name permission granted")
                cropActivityResultLauncher.launch(null)
            }
        }
        when (requestCode) {
            READ_IMAGE_STORAGE -> innerCheck(NAME)
        }
    }

    // Show dialog for permission dialog
    private fun showDialog(permission: String, name: String, requestCode: Int) {
        // Alert dialog box
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            // setting alert properties
            setMessage(getString(R.string.permision_to_access) + name + getString(R.string.is_required_to_use_this_app))
            setTitle("Permission required")
            setPositiveButton("Ok") { _, _ ->
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    /*function to crop picture*/
    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setCropMenuCropButtonTitle("Done")
                .setAspectRatio(1, 1)
                .getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            var imageUri: Uri? = null
            try {
                imageUri = CropImage.getActivityResult(intent).uri
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
            return imageUri
        }
    }

    /*Upload Profile Picture*/
    private fun uploadImageToServer(uri: Uri) {

        // create RequestBody instance from file
        val convertedImageUriToBitmap = uriToBitmap(uri)
        val bitmapToFile = saveBitmap(convertedImageUriToBitmap)

        /*Compress Image then Upload Image*/
        lifecycleScope.launch {
            val compressedImage = Compressor.compress(requireContext(), bitmapToFile!!)
            val imageBody = compressedImage.asRequestBody("image/jpg".toMediaTypeOrNull())
            val image = MultipartBody.Part.createFormData("file", bitmapToFile?.name, imageBody!!)
            imageUploadViewModel.mediaImageUpload(image)
        }

        /*Handling the response from the retrofit*/
        imageUploadViewModel.userProfileImage.observe(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading<UserProfileImage>) {
                    progressDialog.showDialogFragment("Uploading...")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, mainRetrofit, requireView(), sessionManager, database)
                } else {
//                    progressDialog.hideProgressDialog()
                    showToast("Upload Successful")
                    it.data?.downloadUri?.let { imageUrl ->
                        updateUserProfilePicture(imageUrl)
                        Glide.with(this@AccountFragment)
                            .load(imageUrl)
                            .placeholder(R.drawable.nav_drawer_profile_avatar)
                            .into(binding.accountFragmentEditProfileIconImageView)
                    }
                }
            }
        )
    }

    private fun accountLegalStatusDialog() {
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_LEGAL_STATUS_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the firstname text of user
            val legalStatus = bundle.getString(ACCOUNT_LEGAL_STATUS_BUNDLE_KEY)
            binding.accountFragmentLegalStatusValueTextView.text = legalStatus
        }

        // when employee number name value is clicked
        binding.accountFragmentLegalStatusValueTextView.setOnClickListener {
            val currentLegalStatus =
                binding.accountFragmentLegalStatusValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_LEGAL_STATUS_BUNDLE_KEY to currentLegalStatus)
            createProfileDialogFragment(
                R.layout.account_legal_status_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    // firstName Dialog
    private fun accountFirstNameEditDialog() {
        // when first name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_FIRST_NAME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the firstname text of user
            val firstName = bundle.getString(ACCOUNT_FIRST_NAME_BUNDLE_KEY)
            binding.accountFragmentFirstNameValueTextView.text = firstName
        }

        // when first name value is clicked
        binding.accountFragmentFirstNameValueTextView.setOnClickListener {
            val currentFirstName = binding.accountFragmentFirstNameValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY to currentFirstName)
            createProfileDialogFragment(
                R.layout.account_first_name_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, getString(R.string.frstname_dialog_fragment)
            )
        }
    }

    private fun accountLastNameDialogFragment() {
        // when last name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_LAST_NAME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the firstname text of user
            val lastName = bundle.getString(ACCOUNT_LAST_NAME_BUNDLE_KEY)
            binding.accountFragmentLastNameValueTextView.text = lastName
        }

        // when last Name name value is clicked
        binding.accountFragmentLastNameValueTextView.setOnClickListener {
            val currentLastName = binding.accountFragmentLastNameValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_LAST_NAME_BUNDLE_KEY to currentLastName)
            createProfileDialogFragment(
                R.layout.account_last_name_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    // Other name Dialog
    private fun accountOtherNameEditDialog() {
        // when other name name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_OTHER_NAME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the otherName text of user
            val otherName = bundle.getString(ACCOUNT_OTHER_NAME_BUNDLE_KEY)
            binding.accountFragmentPhoneNumberValueTextView.text = otherName
        }

        // when last Name name value is clicked
        binding.accountFragmentPhoneNumberValueTextView.setOnClickListener {
            val currentOtherName =
                binding.accountFragmentPhoneNumberValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY to currentOtherName)
            createProfileDialogFragment(
                R.layout.account_phone_number_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    // Workshop state Dialog
    private fun accountWorkshopStateDialog() {
        // when account shop name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_WORKSHOP_STATE_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the state text of user
            val workshopState = bundle.getString(ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY)
            binding.accountFragmentWorkshopAddressStateValueTextView.text = workshopState
        }

        // when state value is clicked
        binding.accountFragmentWorkshopAddressStateValueTextView.setOnClickListener {
            val currentState =
                binding.accountFragmentWorkshopAddressStateValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY to currentState)
            createProfileDialogFragment(
                R.layout.account_workshop_state_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    // Workshop state Dialog
    private fun accountWorkshopCityDialog() {
        // when city value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_WORKSHOP_CITY_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the city text of user
            val workshopCity = bundle.getString(ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY)
            binding.accountFragmentWorkshopAddressCityValueTextView.text = workshopCity
        }

        // when city is clicked
        binding.accountFragmentWorkshopAddressCityValueTextView.setOnClickListener {
            val currentCity =
                binding.accountFragmentWorkshopAddressCityValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY to currentCity)
            createProfileDialogFragment(
                R.layout.account_workshop_city_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    // Workshop street Dialog
    private fun accountWorkshopStreetDialog() {
        // when street value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_WORKSHOP_STREET_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the street text of user
            val workshopStreet = bundle.getString(ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY)
            binding.accountFragmentWorkshopAddressStreetValueTextView.text = workshopStreet
        }

        // when street value is clicked
        binding.accountFragmentWorkshopAddressStreetValueTextView.setOnClickListener {
            val currentStreet =
                binding.accountFragmentWorkshopAddressStreetValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY to currentStreet)
            createProfileDialogFragment(
                R.layout.account_workshop_street_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    private fun accountShowRoomAddressDialog() {
        // when showroom name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_SHOWROOM_ADDRESS_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the showroom address text of user
            val showroomAddress = bundle.getString(ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY)
            binding.accountFragmentShowroomAddressValueTextView.text = showroomAddress
        }

        // when showroom address is clicked
        binding.accountFragmentShowroomAddressValueTextView.setOnClickListener {
            val currentShowroomAddress =
                binding.accountFragmentShowroomAddressValueTextView.text.toString()
            val bundle =
                bundleOf(CURRENT_ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY to currentShowroomAddress)
            createProfileDialogFragment(
                R.layout.account_showroom_address_dialog_fragment,
                bundle
            ).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    private fun accountEmployeeNumberDialogFragment() {
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_EMPLOYEE_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the employee number text of user
            val employeeNumber = bundle.getString(ACCOUNT_EMPLOYEE_BUNDLE_KEY)
            binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.text = employeeNumber
        }

        // when employee number name value is clicked
        binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.setOnClickListener {
            val currentEmployeeNumber =
                binding.accountFragmentNumberOfEmployeeApprenticeValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY to currentEmployeeNumber)
            createProfileDialogFragment(
                R.layout.account_employee_number_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                getString(R.string.tag_employee_number_dialog_fragment)
            )
        }
    }

    private fun accountUnionNameDialogFragment() {
        // when union name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_UNION_NAME_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the union name text of user
            val unionName = bundle.getString(ACCOUNT_UNION_NAME_BUNDLE_KEY)
            binding.accountFragmentNameOfUnionValueTextView.text = unionName
        }

        // when union name value is clicked
        binding.accountFragmentNameOfUnionValueTextView.setOnClickListener {
            val currentUnionName =
                binding.accountFragmentNameOfUnionValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_UNION_NAME_BUNDLE_KEY to currentUnionName)
            createProfileDialogFragment(
                R.layout.account_union_name_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                AccountFragment::class.java.simpleName
            )
        }
    }

    private fun accountUnionWardDialogFragment() {
        // when ward name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_UNION_WARD_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the union name text of user
            val unionWard = bundle.getString(ACCOUNT_UNION_WARD_BUNDLE_KEY)
            binding.accountFragmentWardValueTextView.text = unionWard
        }

        // when union ward value is clicked
        binding.accountFragmentWardValueTextView.setOnClickListener {
            val currentUnionWard =
                binding.accountFragmentWardValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_UNION_WARD_BUNDLE_KEY to currentUnionWard)
            createProfileDialogFragment(
                R.layout.account_union_ward_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                AccountFragment::class.java.simpleName
            )
        }
    }

    private fun accountUnionLGADialogFragment() {
        // when lga name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_UNION_LGA_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the union lga text of user
            val unionLga = bundle.getString(ACCOUNT_UNION_LGA_BUNDLE_KEY)
            binding.accountFragmentLocalGovtAreaValueTextView.text = unionLga
        }

        // when lga value is clicked
        binding.accountFragmentLocalGovtAreaValueTextView.setOnClickListener {
            val currentUnionState =
                binding.accountFragmentLocalGovtAreaValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_UNION_LGA_BUNDLE_KEY to currentUnionState)
            createProfileDialogFragment(
                R.layout.account_union_lga_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                AccountFragment::class.java.simpleName
            )
        }
    }

    private fun accountUnionStateDialogFragment() {
        // when state name value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_UNION_STATE_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the union state text of user
            val unionState = bundle.getString(ACCOUNT_UNION_STATE_BUNDLE_KEY)
            binding.accountFragmentStateValueTextView.text = unionState
        }

        // when state value is clicked
        binding.accountFragmentStateValueTextView.setOnClickListener {
            val currentUnionState =
                binding.accountFragmentStateValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_STATE_NAME_BUNDLE_KEY to currentUnionState)
            createProfileDialogFragment(
                R.layout.account_union_state_dialog_fragment,
                bundle
            ).show(
                childFragmentManager,
                AccountFragment::class.java.simpleName
            )
        }
    }

    // Gender Dialog
    private fun accountGenderSelectDialog() {
        // when gender value is clicked
        childFragmentManager.setFragmentResultListener(
            ACCOUNT_GENDER_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the text of user
            val gender = bundle.getString(ACCOUNT_GENDER_BUNDLE_KEY)
            binding.accountFragmentGenderValueTextView.text = gender
        }

        // when employee number name value is clicked
        binding.accountFragmentGenderValueTextView.setOnClickListener {
            val currentGender = binding.accountFragmentGenderValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_GENDER_BUNDLE_KEY to currentGender)
            createProfileDialogFragment(R.layout.account_gender_dialog_fragment, bundle).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ACCOUNT_EMPLOYEE_REQUEST_KEY = "ACCOUNT EMPLOYEE REQUEST KEY"
        const val ACCOUNT_EMPLOYEE_BUNDLE_KEY = "ACCOUNT EMPLOYEE BUNDLE KEY"
        const val CURRENT_ACCOUNT_EMPLOYEE_BUNDLE_KEY = "CURRENT ACCOUNT EMPLOYEE BUNDLE KEY"

        const val ACCOUNT_FIRST_NAME_REQUEST_KEY = "ACCOUNT FIRST NAME REQUEST KEY"
        const val ACCOUNT_FIRST_NAME_BUNDLE_KEY = "ACCOUNT FIRST NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY = "CURRENT ACCOUNT FIRST NAME BUNDLE KEY"

        const val ACCOUNT_LAST_NAME_REQUEST_KEY = "ACCOUNT LAST NAME REQUEST KEY"
        const val ACCOUNT_LAST_NAME_BUNDLE_KEY = "ACCOUNT LAST NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_LAST_NAME_BUNDLE_KEY = "CURRENT ACCOUNT LAST NAME BUNDLE KEY"

        const val ACCOUNT_OTHER_NAME_REQUEST_KEY = "ACCOUNT OTHER NAME REQUEST KEY"
        const val ACCOUNT_OTHER_NAME_BUNDLE_KEY = "ACCOUNT OTHER NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY = "CURRENT ACCOUNT OTHER NAME BUNDLE KEY"

        const val ACCOUNT_GENDER_REQUEST_KEY = "ACCOUNT GENDER REQUEST KEY"
        const val ACCOUNT_GENDER_BUNDLE_KEY = "ACCOUNT GENDER BUNDLE KEY"
        const val CURRENT_ACCOUNT_GENDER_BUNDLE_KEY = "CURRENT ACCOUNT GENDER BUNDLE KEY"

        const val ACCOUNT_WORKSHOP_STATE_REQUEST_KEY = "ACCOUNT WORKSHOP STATE REQUEST KEY"
        const val ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY = "ACCOUNT WORKSHOP STATE BUNDLE KEY"
        const val CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY =
            "CURRENT ACCOUNT WORKSHOP STATE BUNDLE KEY"

        const val ACCOUNT_WORKSHOP_CITY_REQUEST_KEY = "ACCOUNT WORKSHOP CITY REQUEST KEY"
        const val ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY = "ACCOUNT WORKSHOP CITY BUNDLE KEY"
        const val CURRENT_ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY =
            "CURRENT ACCOUNT WORKSHOP CITY BUNDLE KEY"

        const val ACCOUNT_WORKSHOP_STREET_REQUEST_KEY = "ACCOUNT WORKSHOP STREET REQUEST KEY"
        const val ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY = "ACCOUNT WORKSHOP STREET BUNDLE KEY"
        const val CURRENT_ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY =
            "CURRENT ACCOUNT WORKSHOP STREET BUNDLE KEY"

        const val ACCOUNT_SHOWROOM_ADDRESS_REQUEST_KEY = "ACCOUNT SHOWROOM ADDRESS REQUEST KEY"
        const val ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY = "ACCOUNT SHOWROOM ADDRESS BUNDLE KEY"
        const val CURRENT_ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY =
            "CURRENT ACCOUNT SHOWROOM ADDRESS BUNDLE KEY"

        const val ACCOUNT_UNION_NAME_REQUEST_KEY = "ACCOUNT UNION NAME REQUEST KEY"
        const val ACCOUNT_UNION_NAME_BUNDLE_KEY = "ACCOUNT UNION NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_UNION_NAME_BUNDLE_KEY = "CURRENT ACCOUNT UNION NAME BUNDLE KEY"

        const val ACCOUNT_UNION_STATE_REQUEST_KEY = "ACCOUNT UNION STATE REQUEST KEY"
        const val ACCOUNT_UNION_STATE_BUNDLE_KEY = "ACCOUNT UNION STATE BUNDLE KEY"
        const val CURRENT_ACCOUNT_STATE_NAME_BUNDLE_KEY = "CURRENT ACCOUNT UNION STATE BUNDLE KEY"

        const val ACCOUNT_UNION_WARD_REQUEST_KEY = "ACCOUNT UNION WARD REQUEST KEY"
        const val ACCOUNT_UNION_WARD_BUNDLE_KEY = "ACCOUNT UNION WARD BUNDLE KEY"
        const val CURRENT_ACCOUNT_UNION_WARD_BUNDLE_KEY = "CURRENT ACCOUNT UNION WARD BUNDLE KEY"

        const val ACCOUNT_UNION_LGA_REQUEST_KEY = "ACCOUNT UNION LGA REQUEST KEY"
        const val ACCOUNT_UNION_LGA_BUNDLE_KEY = "ACCOUNT UNION LGA BUNDLE KEY"
        const val CURRENT_ACCOUNT_UNION_LGA_BUNDLE_KEY = "CURRENT ACCOUNT UNION LGA BUNDLE KEY"

        const val ACCOUNT_LEGAL_STATUS_REQUEST_KEY = "ACCOUNT LEGAL STATUS REQUEST KEY"
        const val ACCOUNT_LEGAL_STATUS_BUNDLE_KEY = "ACCOUNT LEGAL STATUS BUNDLE KEY"
        const val CURRENT_ACCOUNT_LEGAL_STATUS_BUNDLE_KEY =
            "CURRENT ACCOUNT LEGAL STATUS BUNDLE KEY"

        const val RENAME_DESCRIPTION_REQUEST_KEY = "ACCOUNT FIRST NAME REQUEST KEY"
        const val RENAME_DESCRIPTION_BUNDLE_KEY = "ACCOUNT FIRST NAME BUNDLE KEY"
        const val CURRENT_ACCOUNT_RENAME_DESCRIPTION_BUNDLE_KEY =
            "CURRENT ACCOUNT FIRST NAME BUNDLE KEY"

        const val READ_IMAGE_STORAGE = 102
        const val NAME = "CLads"
    }
}
