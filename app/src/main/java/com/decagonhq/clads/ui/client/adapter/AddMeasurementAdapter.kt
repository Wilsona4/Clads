package com.decagonhq.clads.ui.client.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.model.DressMeasurementModel
import com.decagonhq.clads.util.RecyclerClickListener

class AddMeasurementAdapter(
    private val currentList: MutableList<DressMeasurementModel>,
    private val listener1: RecyclerClickListener,
    private val listener2: RecyclerClickListener
) : RecyclerView.Adapter<AddMeasurementAdapter.CardViewHolder>() {

    // inner class
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val display: TextView = itemView.findViewById(R.id.measurement_recyclerview_item)
        val delete: ImageView = itemView.findViewById(R.id.measurementment_recyclerview_item_delete_button)
//        Binding the data with the view
        fun bind(dressMeasurementModel: DressMeasurementModel) {
            display.text = "${dressMeasurementModel.measurementName} ${dressMeasurementModel.measurement}"
        }
    }

    // Creating view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.measurement_fragment_recyclerview_items, parent, false)
        return CardViewHolder(view)
    }
    // Binding the view and attaching the listener
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(currentList[position])

        holder.display.setOnClickListener {
            listener1.onItemClickToEdit(holder.adapterPosition, currentList)
        }

        holder.delete.setOnClickListener {
            listener2.onItemClickToDelete(holder.adapterPosition, currentList)
        }
    }
    // Getting the item cout size
    override fun getItemCount(): Int {
        return currentList.size
    }
}
