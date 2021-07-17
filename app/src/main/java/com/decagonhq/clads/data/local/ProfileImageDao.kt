package com.decagonhq.clads.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.decagonhq.clads.data.domain.images.UserProfileImage
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileImageDao {
    /*Add User to Database*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserProfileImage(userProfileImage: UserProfileImage)

    /*Get User in the Database*/
    @Transaction
    @Query("SELECT * FROM profile_image_table")
    fun readUserProfileImage(): Flow<UserProfileImage>

    @Update
    fun updateUserProfileImage(userProfile: UserProfileImage)

    /*Delete userProfile in the Database*/
    @Query("DELETE FROM profile_image_table")
    suspend fun deleteUserProfileImage()
}
