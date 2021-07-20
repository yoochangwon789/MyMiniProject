package com.yoochangwonspro.myminiproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramMyPostBinding
import com.yoochangwonspro.myminiproject.databinding.PostListItemViewBinding

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

class MyPostListAdapter(
    private val dataSet: ArrayList<Post>,
    private val activity: Activity
) : RecyclerView.Adapter<MyPostListAdapter.ViewHolder>() {

    class ViewHolder(val itemViewBinding: PostListItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {}

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.post_list_item_view, viewGroup, false)

        return ViewHolder(PostListItemViewBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemViewBinding.postListOwnerItem.text = dataSet[position].owner
        viewHolder.itemViewBinding.postListContentItem.text = dataSet[position].content
        Glide.with(activity)
            .load(dataSet[position].image)
            .into(viewHolder.itemViewBinding.postListImgItem)
    }

    override fun getItemCount() = dataSet.size
}