package com.yoochangwonspro.myminiproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramSignupBinding

class OutStagramSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramSignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    fun saveUserToken(token: String) {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.apply()
    }
}