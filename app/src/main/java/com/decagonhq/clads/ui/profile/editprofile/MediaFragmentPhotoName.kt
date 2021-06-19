package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.databinding.MediaFragmentPhotoNameBinding
import com.decagonhq.clads.util.DataListener
import com.decagonhq.clads.util.IMAGE_DATA_BUNDLE_KEY
import com.decagonhq.clads.util.IMAGE_KEY
import com.decagonhq.clads.util.IMAGE_NAME_BUNDLE_KEY
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MediaFragmentPhotoName : Fragment() {
    private var _binding: MediaFragmentPhotoNameBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val args: MediaFragmentPhotoNameArgs by navArgs()
    private lateinit var sendFab: FloatingActionButton

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
        val photoGalleryImage = binding.photoGalleryImage
        val photoIV = args.imageData

        /*load the image sent from media fragment*/
        Glide.with(this)
            .load(photoIV)
            .into(photoGalleryImage)

        /*click the send fab to send photo and name of photo back to media fragment*/
        sendFab.setOnClickListener {
            val imageName = binding.mediaFragmentPhotoNameEditText.text.toString()
            val imageData = args.imageData

            DataListener.imageListener.value = true

            val bundle =
                bundleOf(IMAGE_NAME_BUNDLE_KEY to imageName, IMAGE_DATA_BUNDLE_KEY to imageData)
            if (imageName.isEmpty()){
                Toast.makeText(requireContext(), "Enter Image Name", Toast.LENGTH_SHORT).show()
            }else {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(IMAGE_KEY, bundle)
            findNavController().popBackStack()
            }
        }
    }
}
