package com.decagonhq.clads.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface UserDao {
    /*Add Post to Database*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserProfile(userProfile: UserProfileEntity)

    /*Read all Posts in the Database*/
    @Query("SELECT * FROM user_profile_table")
    fun readUserProfile(): Flow<List<UserProfileEntity>>
}
