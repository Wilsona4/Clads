package com.decagonhq.clads.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.util.StringToListConverters

/*Add List of Entities*/
@Database(entities = [UserProfileEntity::class, UserProfileImage::class], version = 1, exportSchema = false)
@TypeConverters(StringToListConverters::class)
abstract class CladsDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun profileImageDao(): ProfileImageDao

    companion object {
        var DATABASE_NAME: String = "clads_db"
    }
}
