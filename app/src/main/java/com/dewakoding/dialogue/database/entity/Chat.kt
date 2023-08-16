package com.dewakoding.dialogue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class Chat(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,
    @ColumnInfo(name = "content")
    var content : String,
    @ColumnInfo(name = "session_id")
    var sessionId: Int,
    @ColumnInfo(name = "is_from_user")
    var isFromUser : Boolean,
    @ColumnInfo(name = "created_at")
    val createdAtInMilis: Long,
)