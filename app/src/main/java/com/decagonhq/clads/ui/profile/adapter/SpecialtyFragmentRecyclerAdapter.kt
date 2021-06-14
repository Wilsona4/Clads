package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.databinding.SpecialtyFragmentRecyclerItemBinding
import com.decagonhq.clads.util.Specialty

class SpecialtyFragmentRecyclerAdapter :
    RecyclerView.Adapter<SpecialtyFragmentRecyclerAdapter.SpecialtyViewHolder>() {

    private var specialtyList = arrayListOf<Specialty>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val viewBinding = SpecialtyFragmentRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialtyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty = specialtyList[position]
        holder.bind(specialty)
    }

    override fun getItemCount(): Int {
        return specialtyList.size
    }

    class SpecialtyViewHolder(private val itemBinding: SpecialtyFragmentRecyclerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(specialty: Specialty) = with(itemBinding) {
            specialtyFragmentYorubaAttiresCheckBox.text = specialty.specialtyName
            specialtyFragmentYorubaAttiresCheckBox.isChecked = specialty.checked
        }
    }

    fun populateList(list: ArrayList<Specialty>) {
        this.specialtyList = list
        notifyDataSetChanged()
    }
}
