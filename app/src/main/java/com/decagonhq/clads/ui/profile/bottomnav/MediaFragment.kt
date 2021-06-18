package com.decagonhq.clads.ui.profile.bottomnav

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.MediaFragmentBinding
import com.decagonhq.clads.model.PhotoGalleryModel
import com.decagonhq.clads.ui.profile.adapter.PhotoGalleryRecyclerAdapter
import com.decagonhq.clads.util.photosProviders


class MediaFragment : Fragment(){


    private var _binding: MediaFragmentBinding? = null


    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var photoGalleryRecyclerAdapter: PhotoGalleryRecyclerAdapter
    var imageUri: Uri? = null

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

        imageUri?.let { initRecyclerView(it) }
//
//        val photoGalleryModel = PhotoGalleryModel(
//            "content://media/external/images/media/256827".toUri()
//            ,
//            "Ifeyinwa"
//        )
//
//        photoList = arrayListOf(
//            photoGalleryModel
//        )
//
//        photoGalleryRecyclerAdapter =
//            PhotoGalleryRecyclerAdapter(photoList, this@MediaFragment)
//        photoGalleryRecyclerView.layoutManager =
//            GridLayoutManager(requireContext(), 2)
//        photoGalleryRecyclerView.adapter = photoGalleryRecyclerAdapter
//        photoGalleryRecyclerAdapter.notifyDataSetChanged()


        binding.mediaFragmentAddPhotoFab.setOnClickListener {
            if (checkPermission()) {
                getPickImageIntent()
            } else {
                requestPermission()
            }
        }
    }

    /** Check for user permission to access phone camera **/
    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED )
    }

    /** requestPermission for user permission to access phone camera **/
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            REQUEST_CODE
        )
    }

    private fun uploadImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun getPickImageIntent() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, REQUEST_CODE)
    }


    //function to attach the selected image to the image view
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            imageUri = data?.data!!

          initRecyclerView(imageUri!!)
        }
    }

    private fun initRecyclerView(uri: Uri) {

        val photoGalleryModel = PhotoGalleryModel(
            uri,
            TEMP_LABEL
        )
        photosProviders.add(photoGalleryModel)

        binding.apply {

            mediaFragmentPhotoRecyclerView.apply {
                photoGalleryRecyclerAdapter = PhotoGalleryRecyclerAdapter(photosProviders /*this@MediaFragment*/)
                adapter = photoGalleryRecyclerAdapter
                layoutManager = GridLayoutManager(requireContext(), GRID_SIZE)
                photoGalleryRecyclerAdapter.notifyDataSetChanged()
            }
        }
    }


    /** On request permission result grant user permission or show a permission denied message **/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    getPickImageIntent()
                } else {
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {

            }
        }
    }

    // build the permission and show the permission dialog
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

//    override fun onItemClick(position: Int) {
//        Toast.makeText(requireContext(), "Item $position clicked", Toast.LENGTH_SHORT).show()
//        photoGalleryRecyclerAdapter.notifyDataSetChanged()
//
//        val intent = Intent(requireContext(), MediaFragmentRecyclerViewItemClicked::class.java )
//        intent.putExtra("key" , photosProviders[position].image )
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
////        val imageUri = photosProviders[position].image
////        val action = MediaFragmentDirections.actionNavMediaToMediaFragmentRecyclerViewItemClicked2()
////        findNavController().navigate(action)
//    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE = 100
        private const val GRID_SIZE = 2
        private const val TEMP_LABEL = "Ifeyinwa"
    }


}


