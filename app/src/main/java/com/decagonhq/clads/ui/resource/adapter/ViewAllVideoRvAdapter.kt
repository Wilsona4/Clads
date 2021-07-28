package com.decagonhq.clads.ui.resource.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.resource.ResourceDetailVideoModel
import com.decagonhq.clads.databinding.ResourceGeneralVideoViewAllItemBinding

class ViewAllVideoRvAdapter(private val interaction: Interaction) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ResourceDetailVideoModel>() {

        override fun areItemsTheSame(
            oldItem: ResourceDetailVideoModel,
            newItem: ResourceDetailVideoModel
        ): Boolean {
            return oldItem.resourceTitle == newItem.resourceTitle
        }

        override fun areContentsTheSame(
            oldItem: ResourceDetailVideoModel,
            newItem: ResourceDetailVideoModel
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ResourceGeneralVideoViewAllItemBinding.inflate(
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

    fun submitList(list: List<ResourceDetailVideoModel>) {
        differ.submitList(list)
    }

    class ViewHolder
    constructor(
        private val binding: ResourceGeneralVideoViewAllItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResourceDetailVideoModel) = with(itemView) {
            itemView.setOnClickListener {
                val currentID = findNavController().currentDestination?.id
                if (currentID == R.id.resourceVideosFragment) {
                    findNavController().navigate(R.id.action_resourceVideosFragment_to_individualVideoScreenFragment)
                    interaction?.onItemSelected(adapterPosition, item)
                } else {
                    interaction?.onItemSelected(adapterPosition, item)
                }
            }
            binding.resourceGeneralVideoViewAllItemCardImageView.setImageResource(item.videoThumbnail)
            binding.resourceGeneralVideoViewAllItemTitleTextView.text = item.resourceTitle
            binding.resourceGeneralVideoViewAllItemTimeTextView.text = item.resourceTime
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ResourceDetailVideoModel)
    }
}
