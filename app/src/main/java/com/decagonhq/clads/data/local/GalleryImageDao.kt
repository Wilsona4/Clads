package com.decagonhq.clads.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import kotlinx.coroutines.flow.Flow

@Dao
interface GalleryImageDao {
    /*Add Gallery Image to Database*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserGalleryImage(userGalleryImage: UserGalleryImage)

    /*Get User Gallery in the Database*/
    @Transaction
    @Query("SELECT * FROM gallery_image_table")
    fun readUserGalleryImage(): Flow<List<UserGalleryImage>>

    @Update
    suspend fun updateUserGalleryImage(userGalleryImage: UserGalleryImage)

    /*Delete userGallery Image in the Database*/
    @Query("DELETE FROM gallery_image_table WHERE fileId = :fileId")
    suspend fun deleteUserGalleryImage(fileId: String)
}
