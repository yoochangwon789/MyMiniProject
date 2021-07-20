package com.yoochangwonspro.myminiproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramUserInfoBinding

class OutStagramUserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramUserInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.userInfoHomeBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        binding.userInfoAllPostView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramPostListActivity::class.java)
            )
        }

        binding.userInfoUpLoadView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramUpLoadActivity::class.java)
            )
        }

        binding.userInfoUserPostView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramMyPostActivity::class.java)
            )
        }
    }

    fun getUserId(): String? {
        val sp = getSharedPreferences("userId", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "null")

        return userId
    }
}