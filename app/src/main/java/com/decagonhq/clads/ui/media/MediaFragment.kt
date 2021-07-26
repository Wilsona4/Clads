package com.decagonhq.clads.ui.media

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.databinding.MediaFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.profile.adapter.PhotoGalleryRecyclerAdapter
import com.decagonhq.clads.ui.profile.adapter.RecyclerClickListener
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment
import com.decagonhq.clads.util.Constants.TOKEN
import com.decagonhq.clads.util.GRID_SIZE
import com.decagonhq.clads.util.PERMISSION_DENIED
import com.decagonhq.clads.util.REQUEST_CODE
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.util.hideView
import com.decagonhq.clads.util.photosProvidersList
import com.decagonhq.clads.util.showView
import com.decagonhq.clads.viewmodels.ImageUploadViewModel
import okhttp3.MultipartBody

class MediaFragment : BaseFragment(), RecyclerClickListener {

    private var _binding: MediaFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding
        get() = _binding!!

    private lateinit var photoGalleryRecyclerAdapter: PhotoGalleryRecyclerAdapter
    private lateinit var noPhotoImageView: ImageView
    private lateinit var noPhotoTextView: TextView
    private val imageUploadViewModel: ImageUploadViewModel by activityViewModels()

    private val pickImages =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { it ->

                val imageData = uri.toString()
                val action =
                    MediaFragmentDirections.actionNavMediaToMediaFragmentPhotoName(imageData)
                findNavController().navigate(action)

                if (photosProvidersList.isEmpty()) {
                    noPhotoImageView.showView()
                    noPhotoTextView.showView()
                    binding.mediaFragmentPhotoRecyclerView.hideView()
                } else {
                    noPhotoImageView.hideView()
                    noPhotoTextView.hideView()
                    binding.mediaFragmentPhotoRecyclerView.showView()
                    photoGalleryRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }

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

        imageUploadViewModel.getLocalDatabaseGalleryImages()

        imageUploadViewModel.uploadGallery.observe(
            requireActivity(),
            Observer {
                when (it) {
                    is Resource.Success -> {
                        val myList: MutableList<UserGalleryImage> =
                            it.data as MutableList<UserGalleryImage>
                        progressDialog.hideProgressDialog()

                        binding.apply {
                            mediaFragmentPhotoRecyclerView.apply {
                                photoGalleryRecyclerAdapter =
                                    PhotoGalleryRecyclerAdapter(
                                        myList,
                                        sessionManager.loadFromSharedPref(TOKEN),
                                        this@MediaFragment,
                                        this@MediaFragment
                                    )
                                adapter = photoGalleryRecyclerAdapter
                                photoGalleryRecyclerAdapter.notifyDataSetChanged()
                                layoutManager =
                                    GridLayoutManager(requireContext(), GRID_SIZE)
                            }

                            if (myList.isEmpty()) {
                                noPhotoImageView.showView()
                                noPhotoTextView.showView()
                                mediaFragmentPhotoRecyclerView.hideView()
                            } else {
                                noPhotoImageView.hideView()
                                noPhotoTextView.hideView()
                                mediaFragmentPhotoRecyclerView.showView()
                            }
                        }
                    }
                    is Resource.Error -> {
                        progressDialog.hideProgressDialog()
                        Toast.makeText(
                            requireContext(),
                            "${it.errorBody}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        handleApiError(it, imageRetrofit, requireView(), sessionManager, database)
                    }
                    is Resource.Loading -> {
                        it.message?.let { it1 ->
                            progressDialog.showDialogFragment(
                                it1
                            )
                        }
                    }
                }
            }
        )

        // implement swipe to refresh
        binding.mediaFragmentSwipeRefreshLayout.setOnRefreshListener {
            imageUploadViewModel.getRemoteGalleryImages()
            binding.mediaFragmentSwipeRefreshLayout.isRefreshing = false
        }

        /*add onclick listener to the fab to ask for permission and open gallery intent*/
        binding.mediaFragmentAddPhotoFab.setOnClickListener {
            if (checkPermission()) {
                pickImages.launch("image/*")
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
                    pickImages.launch("image/*")
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

    /*This is edit and not delete functions (Don't mind the name)*/
    override fun onItemClickToDelete(position: Int, photoArrayList: MutableList<UserGalleryImage>) {
        val currentDescription = photoArrayList[position].description
        val fileId = photoArrayList[position].fileId

        // use actions to pass data from one fragment to the other
        // when first name value is clicked
        childFragmentManager.setFragmentResultListener(
            AccountFragment.RENAME_DESCRIPTION_REQUEST_KEY,
            requireActivity()
        ) { key, bundle ->
            // collect input values from dialog fragment and update the firstname text of user
            val description = bundle.getString(AccountFragment.RENAME_DESCRIPTION_BUNDLE_KEY)
            val reqBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("description", description!!)
                .build()
            imageUploadViewModel.editGalleryImage(fileId, reqBody)
            imageUploadViewModel.uploadGallery.observe(
                viewLifecycleOwner,
                Observer {
                    imageUploadViewModel.uploadGallery.observe(
                        viewLifecycleOwner,
                        Observer {
                            if (it is Resource.Loading<List<UserGalleryImage>>/* && it.data.isNullOrEmpty()*/) {
                                progressDialog.showDialogFragment("Uploading...")
                            } else if (it is Resource.Error) {
                                progressDialog.hideProgressDialog()
                                handleApiError(
                                    it,
                                    imageRetrofit,
                                    requireView(),
                                    sessionManager,
                                    database
                                )
                            } else {
                                progressDialog.hideProgressDialog()
                                it.data?.let { imageUrl ->
                                    Toast.makeText(
                                        requireContext(),
                                        "Upload Successful",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                    )
                }
            )
        }

        val bundle =
            bundleOf(AccountFragment.CURRENT_ACCOUNT_RENAME_DESCRIPTION_BUNDLE_KEY to currentDescription)
        ProfileManagementDialogFragments.createProfileDialogFragment(
            R.layout.rename_gallery_image_dialog_fragment,
            bundle
        ).show(
            childFragmentManager, getString(R.string.rename_description_dialog_fragment)
        )
    }

    /*This is for delete and not edit  */
    override fun onItemClickToEdit(position: Int, photoArrayList: MutableList<UserGalleryImage>) {

        val imageUri = photoArrayList[position].downloadUri
        val description = photoArrayList[position].description
        val fileId = photoArrayList[position].fileId

        // use actions to pass data from one fragment to the other
        val action =
            MediaFragmentDirections.actionNavMediaToPhotoGalleryEditImageFragment(
                imageUri,
                description,
                fileId
            )
        findNavController().navigate(action)
    }

//        override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
