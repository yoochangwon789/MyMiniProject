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
import com.yoochangwonspro.myminiproject.databinding.ActivityMyTubeBinding
import com.yoochangwonspro.myminiproject.databinding.MyTubeItemViewBinding
import com.yoochangwonspro.myminiproject.databinding.PostListItemViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyTubeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTubeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTubeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.myTubeHomeBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        (application as MasterApplication).service.getYoutubeList().enqueue(
            object : Callback<ArrayList<YouTube>> {
                override fun onResponse(
                    call: Call<ArrayList<YouTube>>,
                    response: Response<ArrayList<YouTube>>
                ) {
                    if (response.isSuccessful) {
                        val myTube = response.body()

                        binding.myTubeRecyclerView.apply {
                            adapter = MyTubeListAdapter(myTube!!, this@MyTubeActivity)
                            layoutManager = LinearLayoutManager(this@MyTubeActivity)
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<YouTube>>, t: Throwable) {
                }
            }
        )
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

        viewHolder.itemViewBinding.myTubeItemThumbnail.setOnClickListener {
            val intent = Intent(activity, MyTubeVideoActivity::class.java)
            intent.putExtra("video_url", dataSet[position].video)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}