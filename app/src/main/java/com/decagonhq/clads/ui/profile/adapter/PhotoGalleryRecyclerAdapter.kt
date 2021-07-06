package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.PhotoGalleryModel
import com.decagonhq.clads.databinding.MediaFragmentPhotoRecyclerViewItemBinding
import com.decagonhq.clads.ui.profile.bottomnav.MediaFragmentDirections

//class PhotoGalleryRecyclerAdapter(
//    var photoArrayList: ArrayList<PhotoGalleryModel>
//) : RecyclerView.Adapter<PhotoGalleryRecyclerAdapter.ViewHolder>() {
//
//    inner class ViewHolder(val binding: MediaFragmentPhotoRecyclerViewItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        var uploadedImage = binding.mediaFragmentPhotoRecyclerViewItemUploadedPhotoImageView
//        var imageTitle = binding.mediaFragmentPhotoRecyclerViewItemImageTitleTextView
//
//        // interface to handle the onclick listener
//        private var itemClickListener: OnItemClickListener? = null
//        fun setItemClickListener(iItemClickListener: OnItemClickListener) {
//            this.itemClickListener = iItemClickListener
//        }
//
//        init {
//            itemView.setOnClickListener { view ->
//                itemClickListener?.onItemClick(
//                    view,
//                    adapterPosition
//                )
//            }
//        }
//    }
//
//    /*inflate the view that contains the recycler view item*/
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = MediaFragmentPhotoRecyclerViewItemBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return ViewHolder(binding)
//    }
//
//    /*get the side of the array list*/
//    override fun getItemCount(): Int {
//        return photoArrayList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        Glide.with(holder.binding.root.context)
//            .load(photoArrayList[position].image)
//            .into(holder.uploadedImage)
//
//        holder.itemView.apply {
//            with(holder) {
//                with(photoArrayList[position]) {
//                    // uploadedImage.setImageDrawable()
//                    imageTitle.text = imageName
//                }
//            }
//        }
//
//        // listen for user click events
//        holder.setItemClickListener(object : OnItemClickListener {
//            override fun onItemClick(view: View, position: Int) {
//                val imageUri = photoArrayList[position].image.toString()
//                val imageName = photoArrayList[position].imageName.toString()
//
//                // use actions to pass data from one fragment to the other
//                val action =
//                    MediaFragmentDirections.actionNavMediaToPhotoGalleryEditImageFragment(imageUri, imageName)
//                view.findNavController().navigate(action)
//            }
//        })
//        holder.setIsRecyclable(false)
//    }
//
//    // enables navigation to the MediaFragmentRecyclerViewItemClicked2
//    interface OnItemClickListener {
//        fun onItemClick(view: View, position: Int)
//    }
//}













class PhotoGalleryRecyclerAdapter(
    var photoArrayList: ArrayList<PhotoGalleryModel>,
    private val listener1: RecyclerClickListener,
    private val listener2: RecyclerClickListener
) : RecyclerView.Adapter<PhotoGalleryRecyclerAdapter.CardViewHolder>() {

    // inner class
    inner class CardViewHolder(val binding: MediaFragmentPhotoRecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

//        val display: TextView = itemView.findViewById(R.id.measurement_recyclerview_item)
//        val delete: ImageView = itemView.findViewById(R.id.measurementment_recyclerview_item_delete_button)
        //        Binding the data with the view
        fun bind(photoGalleryModel: PhotoGalleryModel) {
                Glide.with(binding.root.context)
                    .load(photoGalleryModel.image)
                    .into(binding.mediaFragmentPhotoRecyclerViewItemUploadedPhotoImageView)
            binding.mediaFragmentPhotoRecyclerViewItemImageTitleTextView.text = photoGalleryModel.imageName
        }
    }

    // Creating view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = MediaFragmentPhotoRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }
    // Binding the view and attaching the listener
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(photoArrayList[position])

        holder.binding.mediaFragmentPhotoRecyclerViewItemUploadedPhotoImageView.setOnClickListener {
            listener1.onItemClickToEdit(holder.adapterPosition, photoArrayList)
        }

        holder.binding.mediaFragmentPhotoRecyclerViewItemImageTitleTextView.setOnClickListener {
            listener2.onItemClickToDelete(holder.adapterPosition, photoArrayList)
        }
    }
    // Getting the item cout size
    override fun getItemCount(): Int {
        return photoArrayList.size
    }
}

interface RecyclerClickListener {
    fun onItemClickToEdit(position: Int, photoArrayList: ArrayList<PhotoGalleryModel>)
    fun onItemClickToDelete(position: Int, photoArrayList: ArrayList<PhotoGalleryModel>)
}