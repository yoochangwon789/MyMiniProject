package com.yoochangwonspro.myminiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramLoginBinding

class OutStagramLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_out_stagram_login)

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


    }

}