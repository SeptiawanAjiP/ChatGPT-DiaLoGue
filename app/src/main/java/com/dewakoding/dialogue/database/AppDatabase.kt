package com.dewakoding.dialogue.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dewakoding.dialogue.database.dao.ChatDao
import com.dewakoding.dialogue.database.entity.Chat

@Database(entities = arrayOf(Chat::class), version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun chatDao(): ChatDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "chat_dialogue").build()
                INSTANCE = instance
                instance
            }
        }
    }

}