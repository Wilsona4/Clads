package com.decagonhq.clads.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.decagonhq.clads.data.local.post.PostDao
import com.decagonhq.clads.data.local.post.PostEntity

/*Add List of Entities*/
@Database(entities = [UserProfileEntity::class, PostEntity::class], version = 1, exportSchema = false)
abstract class CladsDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        var DATABASE_NAME: String = "clads_db"
    }
}
