package com.yoochangwonspro.myminiproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.yoochangwonspro.myminiproject.databinding.ActivityOutStagramUpLoadBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class OutStagramUpLoadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutStagramUpLoadBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutStagramUpLoadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.uploadHomeBtn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

        binding.uploadPictureSearchBtn.setOnClickListener {
            getPicture()
        }
        
        binding.uploadBtn.setOnClickListener {
            uploadPost()
        }

        binding.uploadPostAllPostView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramPostListActivity::class.java)
            )
        }

        binding.uploadMyPostView.setOnClickListener {
            startActivity(
                Intent(this, OutStagramMyPostActivity::class.java)
            )
        }
    }

    fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.type = "image/*"

        startForResult.launch(intent)
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val uri: Uri = intent!!.data!!
            filePath = getImageFilePath(uri)
            Log.d("pathh", "path : $filePath")
        }
    }

    fun getImageFilePath(contentUri: Uri): String {
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null, null, null)

        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        return cursor.getString(columnIndex)
    }

    fun uploadPost() {
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image", file.name, fileRequestBody)
        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())

        (application as MasterApplication).service.upLoadPost(part, content)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        val post = response.body()
                        Log.d("pathh", post!!.content!!)
                        finish()
                        startActivity(
                            Intent(this@OutStagramUpLoadActivity, OutStagramMyPostActivity::class.java)
                        )
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                }
            })
    }

    fun getContent(): String {
        return binding.uploadContentEditText.text.toString()
    }
}