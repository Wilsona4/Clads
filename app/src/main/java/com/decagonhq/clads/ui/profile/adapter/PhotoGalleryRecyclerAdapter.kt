package com.decagonhq.clads.ui.profile.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.databinding.MediaFragmentPhotoRecyclerViewItemBinding
class PhotoGalleryRecyclerAdapter(
    var photoArrayList: MutableList<UserGalleryImage>,
    var token: String,
    var listener1: RecyclerClickListener,
    var listener2: RecyclerClickListener
) : RecyclerView.Adapter<PhotoGalleryRecyclerAdapter.ViewHolder>() {
    // inner class
    inner class ViewHolder(val binding: MediaFragmentPhotoRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //        Binding the data with the view
        fun bind(userGalleryImage: UserGalleryImage) {
            val glideUrl = GlideUrl(
                userGalleryImage.downloadUri,
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
            Glide.with(binding.root.context)
                .load(glideUrl)
                .into(binding.mediaFragmentPhotoRecyclerViewItemUploadedPhotoImageView)
            binding.mediaFragmentPhotoRecyclerViewItemImageTitleTextView.text =
                userGalleryImage.description
        }
    }
    // Creating view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MediaFragmentPhotoRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    // Binding the view and attaching the listener
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photoArrayList[position])
        holder.binding.mediaFragmentPhotoRecyclerViewItemUploadedPhotoImageView.setOnClickListener {
            listener1.onItemClickToEdit(holder.adapterPosition, photoArrayList)
        }
        holder.binding.mediaFragmentPhotoRecyclerViewItemImageTitleTextView.setOnClickListener {
            listener2.onItemClickToDelete(holder.adapterPosition, photoArrayList)
        }
    }
    // Getting the item count size
    override fun getItemCount(): Int {
        return photoArrayList.size
    }
}
interface RecyclerClickListener {
    fun onItemClickToEdit(position: Int, photoArrayList: MutableList<UserGalleryImage>)
    fun onItemClickToDelete(position: Int, photoArrayList: MutableList<UserGalleryImage>)
}
