package com.decagonhq.clads.data.domain.profile

import androidx.room.ColumnInfo

data class Union(
    @ColumnInfo(name = "union_lga")
    val lga: String? = null,
    @ColumnInfo(name = "union_name")
    val name: String? = null,
    @ColumnInfo(name = "union_state")
    val state: String? = null,
    @ColumnInfo(name = "union_ward")
    val ward: String? = null
)
