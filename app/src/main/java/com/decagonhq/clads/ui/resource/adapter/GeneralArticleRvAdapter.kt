package com.decagonhq.clads.ui.resource.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.resource.ResourceGeneralArticleModel
import com.decagonhq.clads.databinding.ResourceGeneralArticleItemBinding

class GeneralArticleRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ResourceGeneralArticleModel>() {

        override fun areItemsTheSame(
            oldItem: ResourceGeneralArticleModel,
            newItem: ResourceGeneralArticleModel
        ): Boolean {
            return oldItem.articleTitle == newItem.articleTitle
        }

        override fun areContentsTheSame(
            oldItem: ResourceGeneralArticleModel,
            newItem: ResourceGeneralArticleModel
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ResourceGeneralArticleItemBinding.inflate(
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

    fun submitList(list: List<ResourceGeneralArticleModel>) {
        differ.submitList(list)
    }

    class ViewHolder
    constructor(
        private val binding: ResourceGeneralArticleItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResourceGeneralArticleModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            Glide.with(binding.root.context)
                .load(item.articleImages)
                .error(R.drawable.description)
                .into(binding.resourceGeneralArticleItemCardImageView)

            // binding.resourceGeneralArticleItemCardImageView.setImageResource(R.drawable.article_placeholder)
            binding.resourceGeneralArticleItemAuthorTextView.text = item.articleAuthor
            binding.resourceGeneralArticleItemTitleTextView.text = item.articleTitle
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ResourceGeneralArticleModel)
    }
}
