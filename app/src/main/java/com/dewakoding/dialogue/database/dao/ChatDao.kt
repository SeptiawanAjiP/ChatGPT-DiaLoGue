package com.dewakoding.dialogue.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dewakoding.dialogue.database.entity.Chat

@Dao
interface ChatDao {

    @Query("SELECT * FROM chats")
    fun getChats() : LiveData<List<Chat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChat(content : Chat)

    @Query("DELETE FROM chats WHERE id = :id")
    fun deleteChat(id : Int)

}