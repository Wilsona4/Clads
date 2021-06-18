package com.decagonhq.clads.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/*Add List of Entities*/
@Database(entities = [UserProfileEntity::class], version = 1, exportSchema = false)
abstract class CladsDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun userDao(): UserDao

    companion object {
        var DATABASE_NAME: String = "clads_db"
    }
}
