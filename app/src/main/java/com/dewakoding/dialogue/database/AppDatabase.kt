package com.dewakoding.dialogue.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dewakoding.dialogue.database.dao.ChatDao
import com.dewakoding.dialogue.database.dao.SessionDao
import com.dewakoding.dialogue.database.dao.VocabularyDao
import com.dewakoding.dialogue.database.entity.Chat
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.database.entity.Vocabulary

@Database(entities = arrayOf(Chat::class, Session::class, Vocabulary::class), version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun sessionDao(): SessionDao

    abstract fun vocabDao(): VocabularyDao

}