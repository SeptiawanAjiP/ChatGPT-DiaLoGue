package com.dewakoding.dialogue.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dewakoding.dialogue.data.entity.Session


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: Session)

    @Delete
    suspend fun delete(session: Session)

    @Query("SELECT * FROM sessions ORDER BY id DESC")
    fun getAllSession(): LiveData<List<Session>>

    @Query("UPDATE sessions SET title = :title, description = :description WHERE id = :id")
    suspend fun update(id: Int?, title: String?, description: String?)


}