package com.dewakoding.dialogue.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.database.entity.Vocabulary


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@Dao
interface VocabularyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vocabulary: Vocabulary)

    @Delete
    suspend fun delete(vocabulary: Vocabulary)

    @Query("SELECT * FROM vocabularies ORDER BY id DESC")
    fun getAllVocab(): LiveData<List<Vocabulary>>

    @Query("UPDATE vocabularies SET example= :example WHERE id = :id")
    suspend fun addExample(id: Int?, example: String?)

}