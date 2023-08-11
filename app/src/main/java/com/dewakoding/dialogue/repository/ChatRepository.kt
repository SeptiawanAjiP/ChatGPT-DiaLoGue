package com.dewakoding.dialogue.repository

import androidx.lifecycle.LiveData
import com.dewakoding.dialogue.App
import com.dewakoding.dialogue.database.AppDatabase
import com.dewakoding.dialogue.database.dao.ChatDao
import com.dewakoding.dialogue.database.entity.Chat
import com.dewakoding.dialogue.net.ChatGptResponse
import com.dewakoding.dialogue.net.NetCallBack
import com.dewakoding.dialogue.net.RetrofitClient
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
class ChatRepository @Inject constructor(val chatDao: ChatDao) {
    fun getAllChats(sessionId: Int): LiveData<List<Chat>> {
        return chatDao.getChats(sessionId)
    }

    fun getLastGptChat(sessionId: Int): Chat? {
        return chatDao.getLastGptChat(sessionId)
    }

    fun insertChat(content : String, sessionId: Int, isFromUser: Boolean) = chatDao.insertChat(Chat(0, content, sessionId, isFromUser))

    suspend fun postToGptApi(json: JsonObject, successResponse: NetCallBack) = RetrofitClient.instance.postRequest(json).enqueue(object:
        Callback<ChatGptResponse> {
        override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
            successResponse.onSuccess(response)
        }

        override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
            successResponse.onFailed(t.message.toString())
        }
    })

}