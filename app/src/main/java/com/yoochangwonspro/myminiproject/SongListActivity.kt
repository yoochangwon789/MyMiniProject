package com.yoochangwonspro.myminiproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoochangwonspro.myminiproject.databinding.ActivitySongListBinding
import com.yoochangwonspro.myminiproject.databinding.MyTubeItemViewBinding
import com.yoochangwonspro.myminiproject.databinding.SongListItemViewBinding

class SongListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}

class SongListAdapter(
    private val dataSet: ArrayList<Song>,
    private val activity: Activity
) : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    class ViewHolder(val itemViewBinding: SongListItemViewBinding) :
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
    }

    override fun getItemCount() = dataSet.size
}