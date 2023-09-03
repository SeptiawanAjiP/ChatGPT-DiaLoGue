package com.dewakoding.dialogue.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
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
interface VocabularyCountByDateDao {

    @Query("SELECT strftime('%Y-%m-%d', datetime(created_at / 1000, 'unixepoch')) as date, COUNT(*) as count FROM vocabularies GROUP BY date ORDER BY date DESC")
    fun getVocabularyCountByDate(): LiveData<List<VocabularyCountByDate>>


}