package com.decagonhq.clads.ui.profile.editprofile

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentResolverCompat.query
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.AccountFragmentBinding
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments.Companion.createProfileDialogFragment
import com.decagonhq.clads.viewmodels.AuthenticationViewModel
import com.decagonhq.clads.viewmodels.ImageUploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private var _binding: AccountFragmentBinding? = null
    private var selectedImage: Uri? = null

    val authenticationViewModel: AuthenticationViewModel by viewModels()

    val imageUploadViewModel: ImageUploadViewModel by viewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
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
        accountlegalStatusdialog()

        /*Select profile image*/
        binding.accountFragmentChangePictureTextView.setOnClickListener {
            Manifest.permission.READ_EXTERNAL_STORAGE.checkForPermission(NAME, READ_IMAGE_STORAGE)
        }
    }

    private fun String.checkForPermission(name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), this) == PackageManager.PERMISSION_GRANTED -> {
                    // call read contact function
                    openImageChooser()
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "$name permission refused", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_SHORT)
                    .show()
                openImageChooser()
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
            setMessage("Permission to access your $name is required to use this app")
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

    /*Select Image*/
    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    // function to attach the selected image to the image view
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICKER) {
            selectedImage = data?.data!!
            binding.accountFragmentEditProfileIconImageView.setImageURI(selectedImage)
            /*Upload image*/
            // Getting the file name
//            val file = File(FileUtil.getPath(selectedImage!!, requireContext()))
//            // Getting the file part
//            val requestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
//            val multiPartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
//
//            viewModel.userProfileImage(multiPartBody)
//            viewModel.loginUser.observe(
//                viewLifecycleOwner,
//                Observer {
//                    when (it) {
//                        is Resource.Success -> {
//                            binding.accountFragmentEditProfileIconImageView.errorSnack(
//                                "Image Uploaded successively",
//                                Snackbar.LENGTH_LONG
//                            )
//                            Log.d("RecourceReport", "onActivityResult:$it ")
//                        }
//                        is Resource.Error -> {
//                            binding.accountFragmentEditProfileIconImageView.errorSnack(
//                                "Image Uploaded successively",
//                                Snackbar.LENGTH_LONG
//                            )
//                        }
//                        is Resource.Loading -> {
//                            binding.accountFragmentEditProfileIconImageView.errorSnack(
//                                "Image upload in progress",
//                                Snackbar.LENGTH_LONG
//                            )
//                        }
//                    }
//                }
//            )
        }
    }

    fun File.asRequestBodyWithProgress(
        contentType: MediaType? = null,
        progressCallback: ((progress: Float) -> Unit)?
    ): RequestBody {
        return object : RequestBody() {
            override fun contentType() = contentType

            override fun contentLength() = length()

            override fun writeTo(sink: BufferedSink) {
                val fileLength = contentLength()
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                val inSt = FileInputStream(this@asRequestBodyWithProgress)
                var uploaded = 0L
                inSt.use {
                    var read: Int = inSt.read(buffer)
                    val handler = Handler(Looper.getMainLooper())
                    while (read != -1) {
                        progressCallback?.let {
                            uploaded += read
                            val progress = (uploaded.toDouble() / fileLength.toDouble()).toFloat()
                            handler.post { it(progress) }

                            sink.write(buffer, 0, read)
                        }
                        read = inSt.read(buffer)
                    }
                }
            }
        }
    }

    // function to get the name of the file
    private fun getFileName(uri: Uri, contentResolver: ContentResolver): String {
        var name = "TO BE REMOVED STRING"
        val cursor = query(contentResolver, uri, null, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }


    fun uploadImage(uri: Uri){

        //get the data under the Uri and open it in read format
        val parcelFileDescriptor = requireActivity().contentResolver
            .openFileDescriptor(uri, "r", null)?: return

        //use the contentResolver to get the actual file by uri
        val file = File(requireActivity().cacheDir, getFileName(uri, requireActivity().contentResolver))
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)


        //create RequestBody instance from file
        val body = file.asRequestBody("image/*".toMediaTypeOrNull())

        //get the information of the image to upload
        //MultiPartBody.Part is used to send the actual file name
        val imageUpload = MultipartBody.Part.createFormData("file", file.name, body)

        //imageUploadViewModel.mediaImageUpload(imageUpload)

    }



    private fun accountlegalStatusdialog() {
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
            val currentLegalStatus = binding.accountFragmentLegalStatusValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_LEGAL_STATUS_BUNDLE_KEY to currentLegalStatus)
            createProfileDialogFragment(R.layout.account_legal_status_dialog_fragment, bundle).show(
                childFragmentManager, AccountFragment::class.java.simpleName
            )
        }
    }

    // Firstname Dialog
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

        // when employee number name value is clicked
        binding.accountFragmentFirstNameValueTextView.setOnClickListener {
            val currentFirstName = binding.accountFragmentFirstNameValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_FIRST_NAME_BUNDLE_KEY to currentFirstName)
            createProfileDialogFragment(R.layout.account_first_name_dialog_fragment, bundle).show(
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
            createProfileDialogFragment(R.layout.account_last_name_dialog_fragment, bundle).show(
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
            binding.accountFragmentOtherNameValueTextView.text = otherName
        }

        // when last Name name value is clicked
        binding.accountFragmentOtherNameValueTextView.setOnClickListener {
            val currentOtherName = binding.accountFragmentOtherNameValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_OTHER_NAME_BUNDLE_KEY to currentOtherName)
            createProfileDialogFragment(R.layout.account_other_name_dialog_fragment, bundle).show(
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
            val currentState = binding.accountFragmentWorkshopAddressStateValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY to currentState)
            createProfileDialogFragment(R.layout.account_workshop_state_dialog_fragment, bundle).show(
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
            val currentCity = binding.accountFragmentWorkshopAddressCityValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY to currentCity)
            createProfileDialogFragment(R.layout.account_workshop_city_dialog_fragment, bundle).show(
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
            val currentStreet = binding.accountFragmentWorkshopAddressStreetValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY to currentStreet)
            createProfileDialogFragment(R.layout.account_workshop_street_dialog_fragment, bundle).show(
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
            val currentShowroomAddress = binding.accountFragmentShowroomAddressValueTextView.text.toString()
            val bundle = bundleOf(CURRENT_ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY to currentShowroomAddress)
            createProfileDialogFragment(R.layout.account_showroom_address_dialog_fragment, bundle).show(
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
            // collect input values from dialog fragment and update the union name text of user
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
        const val CURRENT_ACCOUNT_WORKSHOP_STATE_BUNDLE_KEY = "CURRENT ACCOUNT WORKSHOP STATE BUNDLE KEY"

        const val ACCOUNT_WORKSHOP_CITY_REQUEST_KEY = "ACCOUNT WORKSHOP CITY REQUEST KEY"
        const val ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY = "ACCOUNT WORKSHOP CITY BUNDLE KEY"
        const val CURRENT_ACCOUNT_WORKSHOP_CITY_BUNDLE_KEY = "CURRENT ACCOUNT WORKSHOP CITY BUNDLE KEY"

        const val ACCOUNT_WORKSHOP_STREET_REQUEST_KEY = "ACCOUNT WORKSHOP STREET REQUEST KEY"
        const val ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY = "ACCOUNT WORKSHOP STREET BUNDLE KEY"
        const val CURRENT_ACCOUNT_WORKSHOP_STREET_BUNDLE_KEY = "CURRENT ACCOUNT WORKSHOP STREET BUNDLE KEY"

        const val ACCOUNT_SHOWROOM_ADDRESS_REQUEST_KEY = "ACCOUNT SHOWROOM ADDRESS REQUEST KEY"
        const val ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY = "ACCOUNT SHOWROOM ADDRESS BUNDLE KEY"
        const val CURRENT_ACCOUNT_SHOWROOM_ADDRESS_BUNDLE_KEY = "CURRENT ACCOUNT SHOWROOM ADDRESS BUNDLE KEY"

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
        const val CURRENT_ACCOUNT_LEGAL_STATUS_BUNDLE_KEY = "CURRENT ACCOUNT LEGAL STATUS BUNDLE KEY"

        const val READ_IMAGE_STORAGE = 102
        const val NAME = "CLads"
        const val REQUEST_CODE_IMAGE_PICKER = 100
    }
}
