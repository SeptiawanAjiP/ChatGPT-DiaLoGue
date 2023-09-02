package com.dewakoding.dialogue.ui.vocabulary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewakoding.dialogue.data.entity.Vocabulary
import com.dewakoding.dialogue.data.entity.VocabularyCountByDate
import com.dewakoding.dialogue.listener.NetResponseListener
import com.dewakoding.dialogue.net.response.ChatGptResponse
import com.dewakoding.dialogue.repository.VocabularyRepository
import com.dewakoding.dialogue.util.CommonCons
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@HiltViewModel
class VocabularyViewModel @Inject constructor(val vocabularyRepository: VocabularyRepository) : ViewModel() {
    val allVocab: LiveData<List<Vocabulary>>
    val vocabByDate: LiveData<List<VocabularyCountByDate>>
    init {
        allVocab = vocabularyRepository.allVocab
        vocabByDate = vocabularyRepository.vocabByDate
    }

    fun insert(vocabulary: Vocabulary) = viewModelScope.launch(Dispatchers.IO) {
        vocabularyRepository.insert(vocabulary)
    }

    fun addExample(id: Int, example: String) = viewModelScope.launch ( Dispatchers.IO ) {
        vocabularyRepository.addExample(id, example)
    }

    fun delete(vocabulary: Vocabulary) = viewModelScope.launch (Dispatchers.IO) {
        vocabularyRepository.delete(vocabulary)
    }

    fun getVocabExample(vocabulary: Vocabulary) = viewModelScope.launch {
        val jsonArray = JsonArray()
        val lastObject = JsonObject().apply {
            addProperty("role", "user")
            addProperty("content", CommonCons.GET_PROMPT_VOCAB_EXAMPLE(vocabulary.english))
        }
        jsonArray.add(lastObject)

        val request = JsonObject().apply {
            addProperty("model", "gpt-3.5-turbo")
            add("messages", jsonArray)
        }

        try {
            vocabularyRepository.postToGptApi(request, object: NetResponseListener {
                override fun onSuccess(successResponse: Any) {
                    val response = successResponse as Response<ChatGptResponse>
                    val code = response.code().toString()
                    if (code.equals("200")) {
                        response.body()?.choices?.get(0)?.message?.content?.let { addExample(vocabulary.id!!, it) }
                    }
                }

                override fun onFailed(errorMessage: String) {

                }
            })
        } catch (e: Exception) {
            Log.d("AJI", e.toString())
        }
    }

    fun getVocabTranslate(english: String, netResponseListener: NetResponseListener) = viewModelScope.launch {
        val jsonArray = JsonArray()
        val lastObject = JsonObject().apply {
            addProperty("role", "user")
            addProperty("content", CommonCons.GET_PROMPT_VOCAB_TRANSLATE(english))
        }
        jsonArray.add(lastObject)

        val request = JsonObject().apply {
            addProperty("model", "gpt-3.5-turbo")
            add("messages", jsonArray)
        }

        try {
            vocabularyRepository.postToGptApi(request, object: NetResponseListener {
                override fun onSuccess(successResponse: Any) {
                    val response = successResponse as Response<ChatGptResponse>
                    val code = response.code().toString()
                    if (code.equals("200")) {

                        response.body()?.choices?.get(0)?.message?.content?.let { netResponseListener.onSuccess(it)}
                    }
                }

                override fun onFailed(errorMessage: String) {

                }
            })
        } catch (e: Exception) {
            Log.d("AJI", e.toString())
        }
    }

}