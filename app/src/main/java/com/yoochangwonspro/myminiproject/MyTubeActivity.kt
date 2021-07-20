package com.yoochangwonspro.myminiproject

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoochangwonspro.myminiproject.databinding.ActivityMyTubeBinding
import com.yoochangwonspro.myminiproject.databinding.MyTubeItemViewBinding
import com.yoochangwonspro.myminiproject.databinding.PostListItemViewBinding

class MyTubeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTubeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}

class MyTubeListAdapter(
    private val dataSet: ArrayList<YouTube>,
    private val activity: Activity
) : RecyclerView.Adapter<MyTubeListAdapter.ViewHolder>() {

    class ViewHolder(val itemViewBinding: MyTubeItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {}

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.my_tube_item_view, viewGroup, false)

        return ViewHolder(MyTubeItemViewBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemViewBinding.myTubeItemTitle.text = dataSet[position].title
        viewHolder.itemViewBinding.myTubeItemContent.text = dataSet[position].content
        Glide.with(activity)
            .load(dataSet[position].thumbnail)
            .into(viewHolder.itemViewBinding.myTubeItemThumbnail)
    }

    override fun getItemCount() = dataSet.size
}