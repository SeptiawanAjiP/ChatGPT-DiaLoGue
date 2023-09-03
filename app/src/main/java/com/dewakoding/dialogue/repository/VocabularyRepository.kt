package com.dewakoding.dialogue.repository

import androidx.lifecycle.LiveData
import com.dewakoding.dialogue.data.dao.VocabularyDao
import com.dewakoding.dialogue.data.entity.Vocabulary
import com.dewakoding.dialogue.data.entity.VocabularyCountByDate
import com.dewakoding.dialogue.listener.NetResponseListener
import com.dewakoding.dialogue.net.RetrofitClient
import com.dewakoding.dialogue.net.response.ChatGptResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class VocabularyRepository @Inject constructor(val vocabularyDao: VocabularyDao) {
    val allVocab: LiveData<List<Vocabulary>> = vocabularyDao.getAllVocab()
    fun getVocabulariesByDate(str: String): LiveData<List<Vocabulary>> {
        return vocabularyDao.getVocabulariesByDate(str)
    }


    suspend fun insert(vocab: Vocabulary) = vocabularyDao.insert(vocab)

    suspend fun addExample(id: Int, example: String) = vocabularyDao.addExample(id, example)

    suspend fun delete(vocab: Vocabulary) = vocabularyDao.delete(vocab)

    suspend fun postToGptApi(json: JsonObject, successResponse: NetResponseListener) = RetrofitClient.instance.postRequest(json).enqueue(object:
        Callback<ChatGptResponse> {
        override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
            successResponse.onSuccess(response)
        }

        override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
            successResponse.onFailed(t.message.toString())
        }
    })
}