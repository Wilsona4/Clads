package com.decagonhq.clads.util

import com.decagonhq.clads.ui.client.model.DressMeasurementModel

interface RecyclerClickListener {
//    fun onItemClicked1(dressMeasurementModel: DressMeasurementModel)
//    fun onItemClicked2(dressMeasurementModel: DressMeasurementModel)
    fun onItemClickToEdit(position: Int, currentList: MutableList<DressMeasurementModel>)
    fun onItemClickToDelete(position: Int, currentList: MutableList<DressMeasurementModel>)
}
