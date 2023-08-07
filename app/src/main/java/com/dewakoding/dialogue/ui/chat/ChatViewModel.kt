package com.dewakoding.dialogue.ui.chat

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
import kotlinx.coroutines.CoroutineScope
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

    init {
        chatRepository= ChatRepository()
    }

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
        val allChatsLiveData = getAllChat(sessionId)
        if (isInit) {
            val lastObject = JsonObject().apply {
                addProperty("role", "system")
                addProperty("content", "I am trying to learn English. You are an experienced teacher of English language.Your name is DiaLoGue. Your goal is to keep me engaged in a conversation so I can practice my reading and writing. You must answer my questions. You must ask me open-ended questions and provide answers with an easy to understand text. Always answer in English. Don’t repeat yourself. Be creative. You must always continue conversation and always ask me only one question. Never ask me more than 1 question. You must never let a conversation die. Answer with maximum of 5 sentences in total. Always answer in English.. The answer must be short and max 200 words.")
            }
            jsonArray.add(lastObject)
        } else {
            if (listChat != null) {
                listChat?.forEach { chat ->
                    val obj = JsonObject().apply {
                        if (chat.isFromUser) {
                            addProperty("role", "user")
                            addProperty("content", chat.content + " (reminder: I am trying to learn English. You are an experienced teacher of English language.Your name is DiaLoGue. Your goal is to keep me engaged in a conversation so I can practice my reading and writing. You must answer my questions. You must ask me open-ended questions and provide answers with an easy to understand text. Always answer in English. Don’t repeat yourself. Be creative. You must always continue conversation and always ask me only one question. Never ask me more than 1 question. You must never let a conversation die. Answer with maximum of 5 sentences in total. Always answer in English.. The answer must be short and max 200 words.)")
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
                addProperty("content", str + " (reminder: I am trying to learn English. You are an experienced teacher of English language.Your name is DiaLoGue. Your goal is to keep me engaged in a conversation so I can practice my reading and writing. You must answer my questions. You must ask me open-ended questions and provide answers with an easy to understand text. Always answer in English. Don’t repeat yourself. Be creative. You must always continue conversation and always ask me only one question. Never ask me more than 1 question. You must never let a conversation die. Answer with maximum of 5 sentences in total. Always answer in English.. The answer must be short and max 200 words.)")
            }
            jsonArray.add(obj)
        }

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