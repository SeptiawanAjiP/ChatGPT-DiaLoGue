package com.dewakoding.dialogue.repository

import androidx.lifecycle.LiveData
import com.dewakoding.dialogue.data.dao.VocabularyCountByDateDao
import com.dewakoding.dialogue.data.entity.Vocabulary
import com.dewakoding.dialogue.data.entity.VocabularyCountByDate
import javax.inject.Inject


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class VocabularyCountByDateRepository @Inject constructor(val vocabularyCountByDateDao: VocabularyCountByDateDao){
    val allVocabCountByDate: LiveData<List<VocabularyCountByDate>> = vocabularyCountByDateDao.getVocabularyCountByDate()
}