package com.dewakoding.dialogue.net

import com.dewakoding.dialogue.App
import com.dewakoding.dialogue.util.CommonCons
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://api.openai.com/v1/"
    private var okHttpClient = OkHttpClient
        .Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val token = App.getSession().getSessionString(CommonCons.API_KEY)
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${token}")
                .build()
            chain.proceed(request)
        })
        .build()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        retrofit.create(Api::class.java)

    }
}