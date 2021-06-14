package com.decagonhq.clads.util

import com.decagonhq.clads.model.DressMeasurementModel

interface RecyclerClickListener {
    fun onItemClickToEdit(position: Int, currentList: MutableList<DressMeasurementModel>)
    fun onItemClickToDelete(position: Int, currentList: MutableList<DressMeasurementModel>)
}
