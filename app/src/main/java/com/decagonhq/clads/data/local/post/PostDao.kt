package com.decagonhq.clads.data.local.post

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface PostDao {
    /*Add Post to Database*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(post: PostEntity)

    /*Read all Posts in the Database*/
    @Query("SELECT * FROM post_table")
    fun readAllPost(): Flow<List<PostEntity>>
}
