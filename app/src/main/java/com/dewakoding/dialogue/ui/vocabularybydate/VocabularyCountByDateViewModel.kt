package com.dewakoding.dialogue.ui.vocabularybydate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dewakoding.dialogue.data.entity.VocabularyCountByDate
import com.dewakoding.dialogue.repository.VocabularyCountByDateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@HiltViewModel
class VocabularyCountByDateViewModel  @Inject constructor(val vocabularyCountByDateRepository: VocabularyCountByDateRepository) : ViewModel()  {
    val allVocabCountByDate: LiveData<List<VocabularyCountByDate>>
    init {
        allVocabCountByDate = vocabularyCountByDateRepository.allVocabCountByDate
    }
}