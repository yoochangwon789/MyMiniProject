package com.yoochangwonspro.myminiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutStagramLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.outStagramHomeView.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        binding.outStagramSignupBtn.setOnClickListener {
            startActivity(
                Intent(this, OutStagramSignupActivity::class.java)
            )
        }

        binding.outStagramLoginBtn.setOnClickListener {
            (application as MasterApplication).service.login(
                binding.outStagramIdText.text.toString(),
                binding.outStagramPasswordTextOne.text.toString()
            ).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@OutStagramLoginActivity,
                            "로그인을 성공했습니다.",
                            Toast.LENGTH_LONG
                        ).show()
                        (application as MasterApplication).createRetrofit()
                        startActivity(
                            Intent(this@OutStagramLoginActivity, OutStagramPostListActivity::class.java)
                        )
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                }
            })
        }
    }

}