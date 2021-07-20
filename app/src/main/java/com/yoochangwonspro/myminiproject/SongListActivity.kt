package com.yoochangwonspro.myminiproject

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoochangwonspro.myminiproject.databinding.ActivitySongListBinding
import com.yoochangwonspro.myminiproject.databinding.MyTubeItemViewBinding
import com.yoochangwonspro.myminiproject.databinding.SongListItemViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.songListHomeBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        (application as MasterApplication).service.getSongList()
            .enqueue(object : Callback<ArrayList<Song>> {
                override fun onResponse(
                    call: Call<ArrayList<Song>>,
                    response: Response<ArrayList<Song>>
                ) {
                    if (response.isSuccessful) {
                        val songList = response.body()

                        binding.songListRecyclerView.apply {
                            adapter = SongListAdapter(songList!!, this@SongListActivity)
                            layoutManager = LinearLayoutManager(this@SongListActivity)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Song>>, t: Throwable) {
                }
            })
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    inner class SongListAdapter(
        private val dataSet: ArrayList<Song>,
        private val activity: Activity
    ) : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

        inner class ViewHolder(val itemViewBinding: SongListItemViewBinding) :
            RecyclerView.ViewHolder(itemViewBinding.root) {}

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.song_list_item_view, viewGroup, false)

            return ViewHolder(SongListItemViewBinding.bind(view))
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemViewBinding.songTitle.text = dataSet[position].title
            Glide.with(activity)
                .load(dataSet[position].thumbnail)
                .into(viewHolder.itemViewBinding.songImg)

            viewHolder.itemViewBinding.songPlay.setOnClickListener {
                val path = dataSet[position].song

                try {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                    mediaPlayer = MediaPlayer.create(activity, Uri.parse(path))
                    mediaPlayer?.start()
                } catch (e: Exception) {
                    Log.d("eee" , "e : $e")
                }
            }
        }

        override fun getItemCount() = dataSet.size
    }
}