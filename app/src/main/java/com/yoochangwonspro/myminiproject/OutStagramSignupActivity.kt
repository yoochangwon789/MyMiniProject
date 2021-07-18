package com.yoochangwonspro.myminiproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutStagramSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramSignupBinding

    private lateinit var idText: EditText
    private lateinit var passwordOne: EditText
    private lateinit var passwordTwo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramSignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()

        binding.outStagramSignupBtn.setOnClickListener {
            signUpRetrofit()
        }

        binding.outStagramHomeView.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

    }

    fun initView() {
        idText = binding.outStagramIdText
        passwordOne = binding.outStagramPasswordTextOne
        passwordTwo = binding.outStagramPasswordTextTwo
    }

    fun signUpRetrofit() {
        (application as MasterApplication).service.register(
            getIdText(), getPasswordOne(), getPasswordTwo()).enqueue(object : Callback<User> {
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

    fun getIdText(): String {
        return idText.text.toString()
    }

    fun getPasswordOne(): String {
        return passwordOne.text.toString()
    }

    fun getPasswordTwo(): String {
        return passwordTwo.text.toString()
    }

}