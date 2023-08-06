package com.dewakoding.dialogue.net

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
at : 02/07/23 - 21.55

 **/
interface Api {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer "
    )
    @POST("chat/completions")
    fun postRequest(@Body jsonObject: JsonObject): Call<ChatGptResponse>
}