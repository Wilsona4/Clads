package com.decagonhq.clads.ui.media

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.databinding.MediaFragmentPhotoNameBinding
import com.decagonhq.clads.ui.BaseFragment
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
            val imageUri = imageData.toUri()
            if (imageName.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Image Name", Toast.LENGTH_SHORT).show()
            } else {
                uploadGalleryImage(imageUri, imageName)
            }
        }
    }

    private fun uploadGalleryImage(uri: Uri, description: String) {
        // create RequestBody instance from file
        val convertedImageUriToBitmap = uriToBitmap(uri)
        val bitmapToFile = saveBitmap(convertedImageUriToBitmap)
        val requestBody = bitmapToFile?.asRequestBody("image/jpg".toMediaTypeOrNull())
        val reqBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", bitmapToFile?.name, requestBody!!)
            .addFormDataPart("description", description)
            .build()
        imageUploadViewModel.uploadGallery(reqBody)
        imageUploadViewModel.uploadGallery.observe(
            viewLifecycleOwner,
            Observer {
                if (it is Resource.Loading<List<UserGalleryImage>>/* && it.data.isNullOrEmpty()*/) {
                    progressDialog.showDialogFragment("Uploading...")
                } else if (it is Resource.Error) {
                    progressDialog.hideProgressDialog()
                    handleApiError(it, imageRetrofit, requireView(), sessionManager, database)
                } else {
                    progressDialog.hideProgressDialog()
                    it.data?.let { imageUrl ->
                        Toast.makeText(requireContext(), "Upload Successful", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
