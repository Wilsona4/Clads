package com.decagonhq.clads.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decagonhq.clads.util.StringToListConverters

/*Add List of Entities*/
@Database(entities = [UserProfileEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringToListConverters::class)
abstract class CladsDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        var DATABASE_NAME: String = "clads_db"
    }
}
