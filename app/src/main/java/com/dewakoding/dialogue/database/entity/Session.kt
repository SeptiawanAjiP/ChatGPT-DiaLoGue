package com.dewakoding.dialogue.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


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
): Serializable