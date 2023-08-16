package com.dewakoding.dialogue.database.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@Entity(tableName = "sessions")
data class Session (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int?,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "created_at")
    val createdAtInMilis: Long,
): Serializable