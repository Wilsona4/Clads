package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.databinding.MediaFragmentPhotoNameBinding
import com.decagonhq.clads.util.IMAGE_DATA_BUNDLE_KEY
import com.decagonhq.clads.util.IMAGE_KEY
import com.decagonhq.clads.util.IMAGE_NAME_BUNDLE_KEY

class MediaFragmentPhotoName : Fragment() {
    private var _binding: MediaFragmentPhotoNameBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val args: MediaFragmentPhotoNameArgs by navArgs()
    private lateinit var sendButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = MediaFragmentPhotoNameBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendButton = binding.mediaFragmentPhotoNameSendButton
        val photoGalleryImage = binding.photoGalleryImage
        val photoIV = args.imageData

        Glide.with(this)
            .load(photoIV)
            .into(photoGalleryImage)


        sendButton.setOnClickListener {
            val imageName = binding.mediaFragmentPhotoNameEditText.text.toString()
            val imageData = args.imageData

            val bundle =
                bundleOf(IMAGE_NAME_BUNDLE_KEY to imageName, IMAGE_DATA_BUNDLE_KEY to imageData)
            findNavController().previousBackStackEntry?.savedStateHandle?.set(IMAGE_KEY, bundle)
            findNavController().popBackStack()
        }

    }
}