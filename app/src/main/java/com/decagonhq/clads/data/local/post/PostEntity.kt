package com.decagonhq.clads.data.local.post

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "post_table")
data class PostEntity(
    val userId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val body: String,
) : Parcelable
