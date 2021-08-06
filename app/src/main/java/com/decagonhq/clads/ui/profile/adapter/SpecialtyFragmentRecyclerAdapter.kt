package com.decagonhq.clads.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.domain.profile.Specialty
import com.decagonhq.clads.databinding.SpecialtyFragmentRecyclerItemBinding

class SpecialtyFragmentRecyclerAdapter :
    RecyclerView.Adapter<SpecialtyFragmentRecyclerAdapter.SpecialtyViewHolder>() {

    private var _specialtySet = mutableSetOf(
        Specialty("Yoruba Attires", false),
        Specialty("Hausa Attires", false),
        Specialty("Senator", false),
        Specialty("Embroidery", false),
        Specialty("Africa Fashion", false),
        Specialty("School Uniform", false),
        Specialty("Military and Para-Military Uniforms", false),
        Specialty("Igbo Attires", false),
        Specialty("South-South attire", false),
        Specialty("Kaftans", false),
        Specialty("Contemporary", false),
        Specialty("Western Fashion", false),
        Specialty("Caps", false)
    )

//    private var _specialtySet = mutableSetOf(
//        "Yoruba Attires",
//        "Hausa Attires",
//        "Senator",
//        "Embroidery",
//        "Africa Fashion",
//        "School Uniform",
//        "Military and Para-Military Uniforms",
//        "Igbo Attires",
//        "South-South Attires",
//        "Kaftans",
//        "Contemporary",
//        "Western Fashion",
//        "Caps"
//    )
    val specialtySet: Set<Specialty> get() = _specialtySet

//    private var _specialtyList = mutableListOf<String>()
//    val specialtyList: List<String> get() = _specialtyList

    inner class SpecialtyViewHolder(var itemBinding: SpecialtyFragmentRecyclerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(specialty: Specialty) = with(itemBinding) {
            specialtyFragmentYorubaAttiresCheckBox.text = specialty.specialtyName
            specialtyFragmentYorubaAttiresCheckBox.isChecked = specialty.checked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialtyViewHolder {
        val viewBinding = SpecialtyFragmentRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialtyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SpecialtyViewHolder, position: Int) {
//        val specialty = _specialtyList[position]
        val specialty = _specialtySet.elementAt(position)
        holder.bind(specialty)
    }

    override fun getItemCount(): Int {
//        return _specialtyList.size
        return _specialtySet.size
    }

    fun populateList(list: MutableList<String>) {
        for (item in list) {
//            _specialtySet.add(item)
            _specialtySet.add(Specialty(item, true))
        }
//        _specialtyList = list
        notifyDataSetChanged()
    }

    fun addNewSpecialty(specialty: String) {
        _specialtySet.add(Specialty(specialty, true))
//        _specialtySet.add(Specialty(specialtyName = specialty, true))
        notifyDataSetChanged()
    }

    fun removeSpecialty(element: Specialty) {
//        _specialtyList.removeAt(position)
        _specialtySet.remove(element)
//        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun getList(): List<String> {
        return specialtySet.filter {
            it.checked
        }.map {
            it.specialtyName
        }
    }

//    fun undoRemove(position: Int, specialty: String) {
//        _specialtyList.add(position, specialty)
//        notifyItemRangeChanged(position, _specialtyList.size - 1)
//    }
}
