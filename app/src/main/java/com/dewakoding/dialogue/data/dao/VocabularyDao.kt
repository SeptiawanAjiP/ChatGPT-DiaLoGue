package com.dewakoding.dialogue.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dewakoding.dialogue.data.entity.Vocabulary
import com.dewakoding.dialogue.data.entity.VocabularyCountByDate


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

    @Query("SELECT * FROM vocabularies WHERE strftime('%Y-%m-%d', datetime(created_at/ 1000, 'unixepoch')) = :desiredDate")
    fun getVocabulariesByDate(desiredDate: String): LiveData<List<Vocabulary>>

}