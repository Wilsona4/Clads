package com.decagonhq.clads.ui.profile.editprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.databinding.MediaFragmentPhotoNameBinding

class MediaFragmentPhotoName : Fragment() {
    private var _binding: MediaFragmentPhotoNameBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    val args: MediaFragmentPhotoNameArgs by navArgs()
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

        sendButton.setOnClickListener {
            val imageName = binding.mediaFragmentPhotoNameEditText.text.toString()
            val imagePhoto = args.imageData

            Log.d("IMAGENAME", "onViewCreated: $imageName")
            Log.d("IMAGEPHOTO", "onViewCreated: $imagePhoto")

            val action = MediaFragmentPhotoNameDirections.actionMediaFragmentPhotoNameToNavMedia(
                imagePhoto,
                imageName
            )

            findNavController().navigate(action)
        }


        val photoGalleryImage = binding.photoGalleryImage

        val photoIV = args.imageData
        Log.d("PHOTOGALLERYIMAGE", "onViewCreated: $photoIV")
        Glide.with(this)
            .load(photoIV)
            .into(photoGalleryImage)
    }
}