package com.decagonhq.clads.ui.profile.bottomnav

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.MediaFragmentRecyclerViewItemClickedBinding


class MediaFragmentRecyclerViewItemClicked : Fragment() {
    private var _binding: MediaFragmentRecyclerViewItemClickedBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    val args: MediaFragmentRecyclerViewItemClickedArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = MediaFragmentRecyclerViewItemClickedBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoImageView = binding.mediaFragmentRecyclerViewPhotoImageView

        val photoIV = args.imageUri

        Glide.with(this)
            .load(photoIV)
            .into(photoImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.photo_gallery_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.media_share -> sharePhoto()
            R.id.media_edit -> editPhoto()
            R.id.media_delete -> Toast.makeText(
                requireActivity(),
                "Delete clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }

    // method to share photo
    private fun sharePhoto() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Picture")
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

    //method to edit photo
    private fun editPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)

    }

    companion object {
        private const val REQUEST_CODE = 100
    }
}