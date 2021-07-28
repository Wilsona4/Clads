package com.decagonhq.clads.ui.media

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.databinding.PhotoGalleryEditImageFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.profile.DashboardActivity
import com.decagonhq.clads.ui.profile.dialogfragment.ProfileManagementDialogFragments
import com.decagonhq.clads.ui.profile.editprofile.AccountFragment
import com.decagonhq.clads.util.DataListener
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.handleApiError
import com.decagonhq.clads.viewmodels.ImageUploadViewModel
import okhttp3.MultipartBody

class PhotoGalleryEditImageFragment : BaseFragment() {
    private var _binding: PhotoGalleryEditImageFragmentBinding? = null

    private val imageUploadViewModel: ImageUploadViewModel by activityViewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var photoIV: Uri
    private lateinit var imageName: String
    private lateinit var fileId: String
    private val args: PhotoGalleryEditImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PhotoGalleryEditImageFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        (activity as DashboardActivity).setCustomActionBarTitle(args.imageName)
//        activity?.title = args.imageName

        photoIV = args.imageUri.toUri()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoImageView = binding.mediaFragmentRecyclerViewPhotoImageView

        photoIV = args.imageUri.toUri()
        imageName = args.imageName
        fileId = args.fileId

        DataListener.imageListener.value = false

        Glide.with(this)
            .load(photoIV)
            .into(photoImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.photo_gallery_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @ExperimentalStdlibApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.media_share -> sharePhoto()
            R.id.media_edit -> editPhoto()
            R.id.media_delete -> {
                imageDeleteConfirmationRequest()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // method to share photo
    private fun sharePhoto() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, photoIV)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

//    // delete photo
//    private fun deletePhoto() {
//        val photoGalleryModel = PhotoGalleryModel(photoIV, imageName)
//        photosProvidersList.remove(photoGalleryModel)
//        findNavController().popBackStack()
//    }

    // method to edit photo
    private fun editPhoto() {
        childFragmentManager.setFragmentResultListener(
            AccountFragment.RENAME_DESCRIPTION_REQUEST_KEY,
            requireActivity()
        ) { _, bundle ->
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
                        }
                        findNavController().popBackStack(R.id.nav_media, false)
                    }
                }
            )
        }

        val bundle =
            bundleOf(AccountFragment.CURRENT_ACCOUNT_RENAME_DESCRIPTION_BUNDLE_KEY to imageName)
        ProfileManagementDialogFragments.createProfileDialogFragment(
            R.layout.rename_gallery_image_dialog_fragment,
            bundle
        ).show(
            childFragmentManager, getString(R.string.rename_description_dialog_fragment)
        )
    }

    private fun imageDeleteConfirmationRequest() {
        val confirmation = AlertDialog.Builder(requireContext())
        confirmation.setMessage(getString(R.string.delete_image_confirmation))
        confirmation.setNegativeButton(getString(R.string.cancel)) {
            _: DialogInterface, _: Int ->
        }
        confirmation.setPositiveButton(getString(R.string.logout_confirmation_yes)) {
            _: DialogInterface, _: Int ->
            imageUploadViewModel.deleteGalleryImage(fileId)
            imageUploadViewModel.imageResponseCallback.observe(
                viewLifecycleOwner,
                Observer {
                    Toast.makeText(requireActivity(), "$it", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            )
        }
        confirmation.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE = 100
    }
}
