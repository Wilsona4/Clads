package com.decagonhq.clads.ui.profile.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.databinding.SpecialtyFragmentRecyclerItemBinding

class SpecialtyFragmentRecyclerAdapter(private val listener: SpecialtyAdapterClickListener) :
    RecyclerView.Adapter<SpecialtyFragmentRecyclerAdapter.SpecialtyViewHolder>() {

    var specialtyList = arrayListOf<String>()

    inner class SpecialtyViewHolder(var itemBinding: SpecialtyFragmentRecyclerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(specialty: String) = with(itemBinding) {
            specialtyFragmentYorubaAttiresCheckBox.text = specialty
            specialtyFragmentYorubaAttiresCheckBox.isChecked = true

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val viewBinding = SpecialtyFragmentRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialtyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty = specialtyList[position]
        holder.bind(specialty)
        holder.itemBinding.specialtyFragmentYorubaAttiresCheckBox.setOnClickListener {
            val check = holder.itemBinding.specialtyFragmentYorubaAttiresCheckBox.isChecked
            val position = position
            listener.onItemChecked(check,position)
        }
    }

    override fun getItemCount(): Int {
        return specialtyList.size
    }

    fun populateList(specialty: String) {
        this.specialtyList.add(specialty)
        notifyDataSetChanged()
    }

}
