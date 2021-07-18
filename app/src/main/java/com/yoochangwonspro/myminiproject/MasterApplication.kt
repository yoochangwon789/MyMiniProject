package com.yoochangwonspro.myminiproject

import android.app.Application
import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun createRetrofit() {
        val header = Interceptor {
            val original = it.request()

            if (checkIsLogin()) {
                getUserToken()?.let { token ->
                    val request = original.newBuilder()
                        .header("Authorization", "token " + token)
                        .build()
                    it.proceed(request)
                }
            } else{
                it.proceed(original)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

    fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")

        return token != "null"
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")

        return if (token == "null") null
        else token
    }
}