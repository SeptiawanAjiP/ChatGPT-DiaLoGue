package com.dewakoding.dialogue.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewakoding.dialogue.App
import com.dewakoding.dialogue.listener.NetResponseListener
import com.dewakoding.dialogue.database.entity.Chat
import com.dewakoding.dialogue.net.response.ChatGptResponse
import com.dewakoding.dialogue.util.CommonCons
import com.dewakoding.dialogue.repository.ChatRepository
import com.dewakoding.dialogue.util.CommonCons.Companion.WRITING
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
class ChatViewModel @Inject constructor(val chatRepository: ChatRepository): ViewModel() {

    fun getAllChat(sessionId: Int): LiveData<List<Chat>> {
        return chatRepository.getAllChats(sessionId = sessionId)
    }

    fun getLastChat(sessionId: Int): Chat? {
        return chatRepository.getLastGptChat(sessionId)
    }

    fun insert(str: String, sessionId: Int, isFromUser: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        chatRepository.insertChat(str, sessionId, isFromUser)
    }

    fun postToGPT(listChat: List<Chat>?, str : String, sessionId: Int, isInit: Boolean) = viewModelScope.launch {
        val jsonArray = JsonArray()
        if (isInit) {
            val lastObject = JsonObject().apply {
                addProperty("role", "system")
                addProperty("content", CommonCons.PROMPT_CONVERSATION_INIT)
            }
            jsonArray.add(lastObject)
        } else {
            var reminder = " (reminder: ${CommonCons.PROMPT_CONVERSATION})"
            if (listChat != null) {
                listChat?.forEach { chat ->
                    val obj = JsonObject().apply {
                        if (chat.isFromUser) {
                            addProperty("role", "user")
                            addProperty("content", chat.content + reminder)
                        } else {
                            addProperty("role", "assistant")
                            addProperty("content", chat.content)
                        }
                                            }
                    jsonArray.add(obj)
                }
            }

            val obj = JsonObject().apply {
                addProperty("role", "user")
                addProperty("content", str + reminder)
            }
            jsonArray.add(obj)
        }

        val request = JsonObject().apply {
            addProperty("model", "gpt-3.5-turbo")
            add("messages", jsonArray)
        }

        try {
            chatRepository.postToGptApi(request, object: NetResponseListener {
                override fun onSuccess(successResponse: Any) {
                    val response = successResponse as Response<ChatGptResponse>
                    val code = response.code().toString()
                    if (code.equals("200")) {
                        response.body()?.choices?.get(0)?.message?.content?.let { insert(it, sessionId, false) }
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