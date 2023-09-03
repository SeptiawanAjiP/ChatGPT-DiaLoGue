package com.dewakoding.dialogue.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dewakoding.dialogue.data.dao.ChatDao
import com.dewakoding.dialogue.data.dao.SessionDao
import com.dewakoding.dialogue.data.dao.VocabularyCountByDateDao
import com.dewakoding.dialogue.data.dao.VocabularyDao
import com.dewakoding.dialogue.data.entity.Chat
import com.dewakoding.dialogue.data.entity.Session
import com.dewakoding.dialogue.data.entity.Vocabulary

@Database(entities = arrayOf(Chat::class, Session::class, Vocabulary::class), version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun sessionDao(): SessionDao

    abstract fun vocabDao(): VocabularyDao

    abstract fun vocabCountByDateDao(): VocabularyCountByDateDao

}