package com.dewakoding.dialogue.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@Entity(tableName = "vocabularies")
class Vocabulary (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int?,
    @ColumnInfo(name = "english")
    var english : String,
    @ColumnInfo(name = "bahasa")
    var bahasa: String,
    @ColumnInfo(name = "example")
    var example: String?,
    @ColumnInfo(name = "created_at")
    val createdAtInMilis: Long,
)