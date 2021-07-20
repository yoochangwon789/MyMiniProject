package com.yoochangwonspro.myminiproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramPostListBinding
import com.yoochangwonspro.myminiproject.databinding.PostListItemViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutStagramPostListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramPostListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramPostListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.postListHomeBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        binding.postUpLoadView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramUpLoadActivity::class.java)
            )
        }

        (application as MasterApplication).service.getAllPosts().enqueue(
            object : Callback<ArrayList<Post>> {
                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if (response.isSuccessful) {
                        val postList = response.body()

                        binding.postListRecyclerView.apply {
                            adapter = PostListAdapter(postList!!, this@OutStagramPostListActivity)
                            layoutManager = LinearLayoutManager(this@OutStagramPostListActivity)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                }
            }
        )
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
        viewHolder.itemViewBinding.postListContentItem.text = dataSet[position].content
        Glide.with(activity)
            .load(dataSet[position].image)
            .into(viewHolder.itemViewBinding.postListImgItem)
    }

    override fun getItemCount() = dataSet.size
}