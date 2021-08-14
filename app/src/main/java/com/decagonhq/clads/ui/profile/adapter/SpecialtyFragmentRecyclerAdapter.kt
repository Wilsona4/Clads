package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.databinding.SpecialtyFragmentRecyclerItemBinding

class SpecialtyFragmentRecyclerAdapter :
    RecyclerView.Adapter<SpecialtyFragmentRecyclerAdapter.SpecialtyViewHolder>() {

    private var _specialtySet = mutableSetOf(
        "Yoruba Attires",
        "Hausa Attires",
        "Senator",
        "Embroidery",
        "Africa Fashion",
        "School Uniform",
        "Military and Para-Military Uniforms",
        "Igbo Attires",
        "South-South Attires",
        "Kaftans",
        "Contemporary",
        "Western Fashion",
        "Caps"
    ).toSortedSet()

    val specialtySet: Set<String> get() = _specialtySet
    val savedSpecialtySet = mutableSetOf<String>().toSortedSet()

    inner class SpecialtyViewHolder(
        var itemBinding: SpecialtyFragmentRecyclerItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(specialty: String) = with(itemBinding) {

            specialtyFragmentYorubaAttiresCheckBox.text = specialty
            specialtyFragmentYorubaAttiresCheckBox.isChecked = savedSpecialtySet.contains(specialty)

            specialtyFragmentYorubaAttiresCheckBox.setOnCheckedChangeListener(null)

            specialtyFragmentYorubaAttiresCheckBox.setOnCheckedChangeListener(
                CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                    if (buttonView.isPressed) {
                        if (isChecked) {
                            savedSpecialtySet.add(specialty)
                        } else {
                            savedSpecialtySet.remove(specialty)
                        }
                    }
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val viewBinding = SpecialtyFragmentRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialtyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
        val specialty = _specialtySet.elementAt(position)
        holder.bind(specialty)
    }

    override fun getItemCount(): Int {
        return _specialtySet.size
    }

    fun populateList(list: MutableList<String>) {
        val filteredList = list.filter {
            it.trim().isNotBlank()
        }
        savedSpecialtySet.addAll(filteredList)
        _specialtySet.addAll(filteredList)
        notifyDataSetChanged()
    }

    fun addNewSpecialty(specialty: String) {
        _specialtySet.add(specialty.trim())
        savedSpecialtySet.add(specialty.trim())
        notifyDataSetChanged()
    }

    fun removeSpecialty(element: String) {
        _specialtySet.remove(element)
        savedSpecialtySet.remove(element)
        notifyDataSetChanged()
    }
}
