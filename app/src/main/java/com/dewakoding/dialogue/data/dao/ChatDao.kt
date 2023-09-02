package com.dewakoding.dialogue.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dewakoding.dialogue.data.entity.Chat

@Dao
interface ChatDao {

    @Query("SELECT * FROM chats WHERE session_id = :sessionId")
    fun getChats(sessionId: Int) : LiveData<List<Chat>>
    @Query("SELECT * FROM chats WHERE session_id = :sessionId AND is_from_user = 0 ORDER BY id DESC")
    fun getLastGptChat(sessionId: Int) : Chat?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChat(content : Chat)

    @Query("DELETE FROM chats WHERE id = :id")
    fun deleteChat(id : Int)

}