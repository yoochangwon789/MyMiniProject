package com.yoochangwonspro.myminiproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramMyPostBinding
import com.yoochangwonspro.myminiproject.databinding.PostListItemViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        (application as MasterApplication).service.getUserPostList().enqueue(
            object : Callback<ArrayList<Post>> {
                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if (response.isSuccessful) {
                        val userPost = response.body()

                        binding.myPostListRecyclerView.apply {
                            adapter = MyPostListAdapter(userPost!!, this@OutStagramMyPostActivity)
                            layoutManager = LinearLayoutManager(this@OutStagramMyPostActivity)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                }
            })
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