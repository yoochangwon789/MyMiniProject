package com.yoochangwonspro.myminiproject

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
    }
}