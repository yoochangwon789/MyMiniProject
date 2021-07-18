package com.yoochangwonspro.myminiproject

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramPostListBinding
import com.yoochangwonspro.myminiproject.databinding.PostListItemViewBinding

class OutStagramPostListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramPostListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramPostListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}

class PostListAdapter(
    private val dataSet: ArrayList<Post>,
    private val activity: Activity
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    class ViewHolder(val itemViewBinding: PostListItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {}

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.post_list_item_view, viewGroup, false)

        return ViewHolder(PostListItemViewBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemViewBinding.postListOwnerItem.text = dataSet[position].owner
        viewHolder.itemViewBinding.postListOwnerItem.text = dataSet[position].content
        Glide.with(activity)
            .load(dataSet[position].image)
            .into(viewHolder.itemViewBinding.postListImgItem)
    }

    override fun getItemCount() = dataSet.size
}