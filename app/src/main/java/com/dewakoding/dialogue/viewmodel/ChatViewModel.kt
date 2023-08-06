package com.dewakoding.dialogue.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dewakoding.dialogue.net.NetCallBack
import com.dewakoding.dialogue.database.entity.Chat
import com.dewakoding.dialogue.net.ChatGptResponse
import com.dewakoding.dialogue.repository.ChatRepository
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class ChatViewModel(application: Application): AndroidViewModel(application) {

    private val chatRepository: ChatRepository

    val allChats: LiveData<List<Chat>>
    init {
        chatRepository= ChatRepository()
        allChats= chatRepository.allChats
    }

    fun insert(str: String, isFromUser: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        chatRepository.insertChat(str, isFromUser)
    }

    fun postToGPT(str : String) = viewModelScope.launch {
        val obj = JsonObject().apply {
            addProperty("role", "user")
            addProperty("content", str)
        }

        val jsonArray = JsonArray()
        jsonArray.add(obj)
        val request = JsonObject().apply {

            addProperty("model", "gpt-3.5-turbo")
            add("messages", jsonArray)
        }

        try {
            chatRepository.postToGptApi(request, object: NetCallBack {
                override fun onSuccess(successResponse: Any) {
                    val response = successResponse as Response<ChatGptResponse>
                    val code = response.code().toString()
                    if (code.equals("200")) {
                        response.body()?.choices?.get(0)?.message?.content?.let { insert(it, false) }
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