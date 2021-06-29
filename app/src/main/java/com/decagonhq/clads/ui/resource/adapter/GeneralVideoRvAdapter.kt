package com.decagonhq.clads.ui.resource.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.resource.ResourceGeneralVideoModel
import com.decagonhq.clads.databinding.ResourceGeneralVideoItemBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class GeneralVideoRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ResourceGeneralVideoModel>() {

        override fun areItemsTheSame(
            oldItem: ResourceGeneralVideoModel,
            newItem: ResourceGeneralVideoModel
        ): Boolean {
            return oldItem.videoUrl == newItem.videoUrl
        }

        override fun areContentsTheSame(
            oldItem: ResourceGeneralVideoModel,
            newItem: ResourceGeneralVideoModel
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ResourceGeneralVideoItemBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ViewHolder(
            binding,
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ResourceGeneralVideoModel>) {
        differ.submitList(list)
    }

    class ViewHolder
    constructor(
        private var binding: ResourceGeneralVideoItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        var simplePlayer = SimpleExoPlayer.Builder(binding.root.context).build()

        fun bind(item: ResourceGeneralVideoModel) = with(itemView) {
            itemView.setOnClickListener {
                val navigationCurrentDestination = findNavController().currentDestination?.id
                if (navigationCurrentDestination == R.id.resourceGeneralFragment) {
                    it.findNavController().navigate(R.id.action_resourceGeneralFragment_to_individualVideoScreenFragment)
                }
//                interaction?.onItemSelected(adapterPosition, item)
            }

            val mediaItem = MediaItem.fromUri(item.videoUrl)
            simplePlayer.addMediaItem(mediaItem)
            binding.resourceGeneralVideoItemTitleTextView.text = item.videoTitle
            binding.resourceGeneralVideoItemCardImageView.setImageResource(item.videoThumbnail)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ResourceGeneralVideoModel)
    }
}
