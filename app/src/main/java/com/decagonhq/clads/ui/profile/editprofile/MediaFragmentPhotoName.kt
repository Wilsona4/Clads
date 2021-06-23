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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.databinding.MediaFragmentPhotoNameBinding
import com.decagonhq.clads.util.DataListener
import com.decagonhq.clads.util.IMAGE_DATA_BUNDLE_KEY
import com.decagonhq.clads.util.IMAGE_KEY
import com.decagonhq.clads.util.IMAGE_NAME_BUNDLE_KEY
import com.decagonhq.clads.viewmodels.ImageUploadViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@AndroidEntryPoint
class MediaFragmentPhotoName : Fragment() {

    private var _binding: MediaFragmentPhotoNameBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val args: MediaFragmentPhotoNameArgs by navArgs()
    private lateinit var sendFab: FloatingActionButton
    private lateinit var imageNamed: EditText
    lateinit var photoGalleryImage: ImageView
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
            val imageName = imageNamed.text.toString()
            val imageData = args.imageData

            DataListener.imageListener.value = true

            val bundle =
                bundleOf(IMAGE_NAME_BUNDLE_KEY to imageName, IMAGE_DATA_BUNDLE_KEY to imageData)
            if (imageName.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Image Name", Toast.LENGTH_SHORT).show()
            } else {
                uploadImageToServer(imageData.toUri())
//                findNavController().previousBackStackEntry?.savedStateHandle?.set(IMAGE_KEY, bundle)
//                findNavController().popBackStack()
            }
        }
    }

    private fun uploadImageToServer(uri: Uri) {

        val imageName = imageNamed.text.toString().trim()
        //get the data under the Uri and open it in read format
        val parcelFileDescriptor = requireActivity().contentResolver
            .openFileDescriptor(uri, "r", null) ?: return

        //use the contentResolver to get the actual file by uri
        val file = File(requireActivity().cacheDir, getFileName(uri, requireActivity().contentResolver))
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)


        //create RequestBody instance from file
        val reqBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        //get the information of the image to upload
        //MultiPartBody.Part is used to send the actual file name
        val imageUpload: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, reqBody)
            .build()
//        val imageUpload = MultipartBody.Part.createFormData("imageName", file.name, body)
        imageUploadViewModel.mediaImageUpload(imageUpload)

    }


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


//    private fun uploadMultipart() {
//        val imageData = args.imageData
//        val filePath: Uri = imageData.toUri()
//        //getting name for the image
//        val name: String = imageNamed.text.toString().trim()
//
//        //getting the actual path of the image
//        val path = getPath(filePath)
//
//        //Uploading code
//        try {
//            val uploadId: String = UUID.randomUUID().toString()
//
//            //Creating a multi part request
//            MultipartUploadRequest(requireActivity(), uploadId, Constants.IMAGE_BASE_URL)
//                .addFileToUpload(path, "image") //Adding file
//                .addParameter("name", name) //Adding text parameter to the request
//                .setNotificationConfig(UploadNotificationConfig())
//                .setMaxRetries(2)
//                .startUpload() //Starting the upload
//        } catch (exc: Exception) {
//            Toast.makeText(requireContext(), exc.message, Toast.LENGTH_SHORT).show()
//        }
//    }

    //method to get the file path from uri
//    private fun getPath(uri: Uri?): String? {
//        var cursor: Cursor? =
//            uri?.let { requireActivity().contentResolver.query(it, null, null, null, null) }
//        cursor?.moveToFirst()
//        var document_id = cursor?.getString(0)
//        if (document_id != null) {
//            document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
//        }
//        cursor?.close()
//        cursor = requireActivity().contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
//        )
//        cursor?.moveToFirst()
//        val path =
//            cursor?.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
//        cursor?.close()
//        return path
//    }



}
