package com.decagonhq.clads.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    /*Add User to Database*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserProfile(userProfile: UserProfileEntity)

    /*Get User in the Database*/
    @Transaction
    @Query("SELECT * FROM user_profile_table")
    fun readUserProfile(): Flow<UserProfileEntity>

    @Update
    suspend fun updateUserProfile(userProfile: UserProfileEntity)

    /*Delete userProfile in the Database*/
    @Query("DELETE FROM user_profile_table")
    suspend fun deleteUserProfile()
}
