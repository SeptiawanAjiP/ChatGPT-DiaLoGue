package com.dewakoding.dialogue.di

import android.content.Context
import androidx.room.Room
import com.dewakoding.dialogue.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "chat_dialogue")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideSessionDao(db: AppDatabase) = db.sessionDao()

    @Provides
    @Singleton
    fun provideChatDao(db: AppDatabase) = db.chatDao()

    @Provides
    @Singleton
    fun provideVocabularyDao(db: AppDatabase) = db.vocabDao()

}