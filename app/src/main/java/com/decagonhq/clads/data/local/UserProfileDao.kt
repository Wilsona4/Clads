package com.decagonhq.clads.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface UserProfileDao {
    /*Add User to Database*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserProfile(userProfile: UserProfileEntity)

    /*Get User in the Database*/
    @Query("SELECT * FROM user_profile_table")
    fun readUserProfile(): Flow<UserProfileEntity>

    @Update
    fun updateUserProfile(userProfile: UserProfileEntity)
}
