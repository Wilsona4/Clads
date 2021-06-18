package com.decagonhq.clads.ui.profile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagonhq.clads.databinding.MediaFragmentPhotoRecyclerViewItemBinding
import com.decagonhq.clads.model.PhotoGalleryModel
import com.decagonhq.clads.ui.profile.bottomnav.MediaFragmentDirections
import com.decagonhq.clads.ui.profile.bottomnav.MediaFragmentRecyclerViewItemClicked
import com.decagonhq.clads.util.photosProviders

class PhotoGalleryRecyclerAdapter(
    var photoArrayList: ArrayList<PhotoGalleryModel>,
) : RecyclerView.Adapter<PhotoGalleryRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: MediaFragmentPhotoRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var uploadedImage = binding.mediaFragmentPhotoRecyclerViewItemUploadedPhotoImageView
        var imageTitle = binding.mediaFragmentPhotoRecyclerViewItemImageTitleTextView

        // interface to handle the onclick listener
        private var itemClickListener: OnItemClickListener? = null

        fun setItemClickListener(iItemClickListener: OnItemClickListener) {
            this.itemClickListener = iItemClickListener
        }

        init {
            itemView.setOnClickListener { view ->
                itemClickListener?.onItemClick(
                    view,
                    adapterPosition
                )
            }
        }
//        override fun onClick(v: View?) {
//            val position: Int = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                onClickListener.onItemClick(position)
//            }
//        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MediaFragmentPhotoRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photoArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.binding.root.context)
            .load(photoArrayList[position].image)
            .into(holder.uploadedImage)

        holder.itemView.apply {
            with(holder) {
                with(photoArrayList[position]) {
                    // uploadedImage.setImageDrawable()
                    imageTitle.text = imageName
                }
            }
        }

        // listen for user click events
        holder.setItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val imageUri = photoArrayList[position].image.toString()
                // use actions to pass data from one fragment to the other
                val action =
                    MediaFragmentDirections.actionNavMediaToMediaFragmentRecyclerViewItemClicked2(
                        imageUri
                    )
                view.findNavController().navigate(action)
            }
        })
    }

    // enables navigation to the MediaFragmentRecyclerViewItemClicked2
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}