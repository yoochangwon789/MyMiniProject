package com.yoochangwonspro.myminiproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutStagramSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramSignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }



    fun signUpRetrofit() {
        (application as MasterApplication).service.register(
            idText,
            passwordOne,
            passwordTwo
        ).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    saveUserToken(user!!.token!!)
                    Toast.makeText(
                        this@OutStagramSignupActivity,
                        "가입이 되었습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@OutStagramSignupActivity,
                    "가입에 실패했습니다.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    fun saveUserToken(token: String) {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.apply()
    }
}