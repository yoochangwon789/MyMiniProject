package com.yoochangwonspro.myminiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramMyPostBinding

class OutStagramMyPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramMyPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.myPostListHomeBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        binding.myPostAllPostView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramPostListActivity::class.java)
            )
        }

        binding.myPostUpLoadView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramUpLoadActivity::class.java)
            )
        }

        binding.myPostUserInfoView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramUserInfoActivity::class.java)
            )
        }
    }
}