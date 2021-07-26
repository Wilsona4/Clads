package com.decagonhq.clads.ui.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.databinding.ClientsRecyclerViewItemBinding
import java.util.Locale

class ClientListRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Client>() {

        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ClientsRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
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

    fun submitList(list: List<Client>) {
        differ.submitList(list)
    }

    class ViewHolder
    constructor(
        private val binding: ClientsRecyclerViewItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Client) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.clientsRecyclerViewItemClientNameTextView.text = item.fullName.capitalize(Locale.ROOT)
            binding.clientsRecyclerViewItemLocationTextView.text = item.deliveryAddresses?.get(0)?.city?.capitalize(Locale.ROOT)
            val clientInitials = item.fullName.split(" ")[0].substring(0, 1).capitalize(Locale.ROOT) +
                item.fullName.split(" ")[1].substring(0, 1).capitalize(Locale.ROOT)
            val generator: ColorGenerator = ColorGenerator.MATERIAL
            val color = generator.randomColor
            val drawable = TextDrawable.builder().beginConfig()
                .width(150)
                .height(150)
                .fontSize(55)
                .endConfig()
                .buildRound(clientInitials, color)

            binding.clientsRecyclerViewItemInitialCircleImageView.setImageDrawable(drawable)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Client)
    }
}
