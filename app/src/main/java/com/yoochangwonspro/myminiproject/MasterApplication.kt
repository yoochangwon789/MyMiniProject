package com.yoochangwonspro.myminiproject

import android.app.Application
import android.content.Context
import okhttp3.Interceptor

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