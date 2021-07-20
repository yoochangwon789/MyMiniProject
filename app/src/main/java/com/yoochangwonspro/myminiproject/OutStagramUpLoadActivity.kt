package com.yoochangwonspro.myminiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramUpLoadBinding

class OutStagramUpLoadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramUpLoadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramUpLoadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}