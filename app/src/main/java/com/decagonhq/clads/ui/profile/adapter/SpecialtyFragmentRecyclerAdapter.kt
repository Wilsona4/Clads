package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.databinding.SpecialtyFragmentRecyclerItemBinding

class SpecialtyFragmentRecyclerAdapter :
    RecyclerView.Adapter<SpecialtyFragmentRecyclerAdapter.SpecialtyViewHolder>() {

    private var _specialtyList = mutableListOf<String>()
    val specialtyList: List<String> get() = _specialtyList

    inner class SpecialtyViewHolder(var itemBinding: SpecialtyFragmentRecyclerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(specialty: String) = with(itemBinding) {
            specialtyFragmentYorubaAttiresCheckBox.text = specialty.trim()
            specialtyFragmentYorubaAttiresCheckBox.isChecked = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val viewBinding = SpecialtyFragmentRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialtyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty = _specialtyList[position]
        holder.bind(specialty)
    }

    override fun getItemCount(): Int {
        return _specialtyList.size
    }

    fun populateList(list: MutableList<String>) {
        _specialtyList = list
        notifyDataSetChanged()
    }

    fun addNewSpecialty(specialty: String) {
        _specialtyList.add(specialty)
        notifyDataSetChanged()
    }

    fun removeSpecialty(position: Int) {
        _specialtyList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun undoRemove(position: Int, specialty: String) {
        _specialtyList.add(position, specialty)
        notifyItemRangeChanged(position, _specialtyList.size - 1)
    }
}
