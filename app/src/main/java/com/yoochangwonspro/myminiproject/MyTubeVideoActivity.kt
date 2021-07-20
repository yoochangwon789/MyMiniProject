package com.yoochangwonspro.myminiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_my_tube_video.*

class MyTubeVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tube_video)

        val url = intent.getStringExtra("video_url")
        val mediaController = MediaController(this)
        my_tube_video_view.setVideoPath(url)
        my_tube_video_view.requestFocus()
        my_tube_video_view.start()
        mediaController.show()
    }
}