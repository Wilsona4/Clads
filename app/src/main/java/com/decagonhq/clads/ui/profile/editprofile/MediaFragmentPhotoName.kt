package com.decagonhq.clads.ui.profile.editprofile

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentResolverCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.databinding.MediaFragmentPhotoNameBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.util.DataListener
import com.decagonhq.clads.util.IMAGE_DATA_BUNDLE_KEY
import com.decagonhq.clads.util.IMAGE_KEY
import com.decagonhq.clads.util.IMAGE_NAME_BUNDLE_KEY
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.util.saveBitmap
import com.decagonhq.clads.util.uriToBitmap
import com.decagonhq.clads.viewmodels.ImageUploadViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@AndroidEntryPoint
class MediaFragmentPhotoName : BaseFragment() {

    private var _binding: MediaFragmentPhotoNameBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val args: MediaFragmentPhotoNameArgs by navArgs()
    private lateinit var sendFab: FloatingActionButton
    private lateinit var imageNamed: EditText
    private lateinit var imageName: String
    private lateinit var imageData: String
    private lateinit var photoGalleryImage: ImageView
    private val imageUploadViewModel: ImageUploadViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MediaFragmentPhotoNameBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendFab = binding.mediaFragmentPhotoNameSendButton
        photoGalleryImage = binding.photoGalleryImage
        imageNamed = binding.mediaFragmentPhotoNameEditText
        val photoIV = args.imageData

        /*load the image sent from media fragment*/
        Glide.with(this)
            .load(photoIV)
            .into(photoGalleryImage)

        /*click the send fab to send photo and name of photo back to media fragment*/
        sendFab.setOnClickListener {
            imageName = imageNamed.text.toString()
            imageData = args.imageData

            DataListener.imageListener.value = true

            val bundle =
                bundleOf(IMAGE_NAME_BUNDLE_KEY to imageName, IMAGE_DATA_BUNDLE_KEY to imageData)
            if (imageName.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Image Name", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(IMAGE_KEY, bundle)
                findNavController().popBackStack()
            }
        }
    }
//
//    private fun uploadImageToServer(uri: Uri) {
//
//        val imageName = imageNamed.text.toString().trim()
//        //get the data under the Uri and open it in read format
//        val parcelFileDescriptor = requireActivity().contentResolver
//            .openFileDescriptor(uri, "r", null) ?: return
//
//        //use the contentResolver to get the actual file by uri
//        val file = File(requireActivity().cacheDir, getFileName(uri, requireActivity().contentResolver))
//        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//        val outputStream = FileOutputStream(file)
//        inputStream.copyTo(outputStream)
//
//
//        //create RequestBody instance from file
//        val convertedImageUriToBitmap = uriToBitmap(uri)
//        val bitmapToFile = saveBitmap(convertedImageUriToBitmap)
//
//        val imageBody = bitmapToFile?.asRequestBody("image/jpg".toMediaTypeOrNull())
//
//        //MultiPartBody.Part is used to send the actual file name
//        val image = MultipartBody.Part.createFormData("file", imageName, imageBody!!)
//
//
//
//            imageUploadViewModel.mediaImageUpload(image)
//
//
//        /*Handling the response from the retrofit*/
//        imageUploadViewModel.userProfileImage.observe(
//            viewLifecycleOwner,
//            Observer {
//                when (it) {
//                    is Resource.Success -> {
//                       progressDialog.hideProgressDialog()
//                        Toast.makeText(requireContext(), "Upload Successful", Toast.LENGTH_SHORT).show()
//                        val bundle =
//                            bundleOf(IMAGE_NAME_BUNDLE_KEY to imageName, IMAGE_DATA_BUNDLE_KEY to imageData)
//                        findNavController().previousBackStackEntry?.savedStateHandle?.set(IMAGE_KEY, bundle)
//                        findNavController().popBackStack()
//                    }
//                    is Resource.Error -> {
//                        progressDialog.hideProgressDialog()
//                        Toast.makeText(requireContext(), "${it.errorBody}", Toast.LENGTH_SHORT).show()
//                        handleApiError(it, imageRetrofit, requireView())
//                    }
//                    is Resource.Loading -> {
//                        progressDialog.showDialogFragment(it.message)
//                    }
//                }
//            }
//        )
//    }

    // function to get the name of the file
    private fun getFileName(uri: Uri, contentResolver: ContentResolver): String {
        var name = "TO BE REMOVED STRING"
        val cursor = ContentResolverCompat.query(contentResolver, uri, null, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }
}
