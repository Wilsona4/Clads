package com.decagonhq.clads.ui.profile.bottomnav

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.decagonhq.clads.data.domain.PhotoGalleryModel
import com.decagonhq.clads.databinding.MediaFragmentBinding
import com.decagonhq.clads.ui.profile.adapter.PhotoGalleryRecyclerAdapter
import com.decagonhq.clads.util.DataListener
import com.decagonhq.clads.util.GRID_SIZE
import com.decagonhq.clads.util.IMAGE_DATA_BUNDLE_KEY
import com.decagonhq.clads.util.IMAGE_KEY
import com.decagonhq.clads.util.IMAGE_NAME_BUNDLE_KEY
import com.decagonhq.clads.util.PERMISSION_DENIED
import com.decagonhq.clads.util.REQUEST_CODE
import com.decagonhq.clads.util.hideView
import com.decagonhq.clads.util.photosProvidersList
import com.decagonhq.clads.util.showView
import com.decagonhq.clads.viewmodels.ImageUploadViewModel

class MediaFragment : Fragment() {

    private var _binding: MediaFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var photoGalleryRecyclerAdapter: PhotoGalleryRecyclerAdapter
    private lateinit var noPhotoImageView: ImageView
    private lateinit var noPhotoTextView: TextView
    private lateinit var photoGalleryModel: PhotoGalleryModel
    private lateinit var imageUri: Uri
    private val imageUploadViewModel: ImageUploadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MediaFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noPhotoImageView = binding.mediaFragmentPhotoIconImageView
        noPhotoTextView = binding.mediaFragmentYouHaveNoPhotoInGalleryTextView
       val imageUploadViewModel = ViewModelProvider(requireActivity()).get(ImageUploadViewModel::class.java)

//        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>(IMAGE_KEY)
//            ?.observe(viewLifecycleOwner) {
//                val imageName = it.getString(IMAGE_NAME_BUNDLE_KEY)
//                val imageData = it.getString(IMAGE_DATA_BUNDLE_KEY)
//                val imageDataUri = imageData?.toUri()
//
//                photoGalleryModel =
//                    PhotoGalleryModel(
//                        imageDataUri,
//                        imageName
//                    )
//
//                if (DataListener.imageListener.value == true) {
//
//                   // photosProvidersList.add(photoGalleryModel)
//                    photoGalleryRecyclerAdapter.notifyDataSetChanged()
//                }
//
//                binding.apply {
//                    noPhotoImageView.visibility = View.INVISIBLE
//                    noPhotoTextView.visibility = View.INVISIBLE
//                    mediaFragmentPhotoRecyclerView.visibility = View.VISIBLE
//                }
//            }
    imageUploadViewModel.userProfileImage.observe(viewLifecycleOwner, Observer {
    Toast.makeText(requireContext(), "YESSSSS", Toast.LENGTH_SHORT).show()
        Log.d("CHECK_VIEWMODEL", "onViewCreated: ${imageUploadViewModel.userProfileImage}")
})
        binding.apply {

            mediaFragmentPhotoRecyclerView.apply {
                photoGalleryRecyclerAdapter =
                    PhotoGalleryRecyclerAdapter(photosProvidersList)
                adapter = photoGalleryRecyclerAdapter
                layoutManager = GridLayoutManager(requireContext(), GRID_SIZE)
                photoGalleryRecyclerAdapter.notifyDataSetChanged()
            }

            if (photosProvidersList.isEmpty()) {
                noPhotoImageView.visibility = View.VISIBLE
                noPhotoTextView.visibility = View.VISIBLE
                mediaFragmentPhotoRecyclerView.visibility = View.GONE
            } else {
                noPhotoImageView.visibility = View.INVISIBLE
                noPhotoTextView.visibility = View.INVISIBLE
                mediaFragmentPhotoRecyclerView.visibility = View.VISIBLE
            }
        }

        /*add onclick listener to the fab to ask for permission and open gallery intent*/
        binding.mediaFragmentAddPhotoFab.setOnClickListener {
            if (checkPermission()) {
                uploadImageFromGallery()
            } else {
                requestPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    "Read External Storage",
                    REQUEST_CODE
                )
            }
        }
    }

    /* Check for user permission to read external storage*/
    private fun checkPermission(): Boolean {
        return (
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            )
    }

    /* requestPermission for user permission to read external storage*/
    private fun requestPermission(permission: String, name: String, requestCode: Int) {
        when {
            shouldShowRequestPermissionRationale(permission) -> showDialog(
                permission,
                name,
                requestCode
            )
            else ->

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQUEST_CODE
                )
        }
    }

    /*intent to get image from gallery*/
    private fun uploadImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    // function to attach the selected image to the image view
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            /*get image from gallery and send to a fragment for naming*/
            imageUri = data?.data!!
            val imageData = imageUri.toString()
            val action = MediaFragmentDirections.actionNavMediaToMediaFragmentPhotoName(imageData)
            findNavController().navigate(action)

            if (photosProvidersList.isEmpty()) {
                noPhotoImageView.showView()
                noPhotoTextView.showView()
                binding.mediaFragmentPhotoRecyclerView.visibility = View.GONE
            } else {
                noPhotoImageView.hideView()
                noPhotoTextView.hideView()
                binding.mediaFragmentPhotoRecyclerView.showView()
                photoGalleryRecyclerAdapter.notifyDataSetChanged()
            }
        }
    }

    /* On request permission result grant user permission or show a permission denied message */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    uploadImageFromGallery()
                } else {
                    Toast.makeText(requireContext(), PERMISSION_DENIED, Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
            }
        }
    }

    /* build the permission and show the permission dialog*/
    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(requireActivity())

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
