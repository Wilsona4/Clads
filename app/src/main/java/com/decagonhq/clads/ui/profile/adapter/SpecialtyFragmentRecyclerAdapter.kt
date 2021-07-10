package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.domain.SpecialtyModel
import com.decagonhq.clads.databinding.SpecialtyFragmentRecyclerItemBinding

class SpecialtyFragmentRecyclerAdapter :
    RecyclerView.Adapter<SpecialtyFragmentRecyclerAdapter.SpecialtyViewHolder>() {

    private var specialtyList = listOf<SpecialtyModel>()

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

        fun bind(specialtyModel: SpecialtyModel) = with(itemBinding) {
            specialtyFragmentYorubaAttiresCheckBox.text = specialtyModel.specialtyName
            specialtyFragmentYorubaAttiresCheckBox.isChecked = specialtyModel.checked
        }
    }

    fun populateList(list: List<SpecialtyModel>) {
        this.specialtyList = list
        notifyDataSetChanged()
    }
}
