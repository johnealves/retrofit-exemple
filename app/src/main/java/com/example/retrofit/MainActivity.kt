package com.example.retrofit

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.ui.adapters.CommentsAdapter
import com.example.retrofit.data.api.ApiService
import com.example.retrofit.data.api.ApiServiceClient
import com.example.retrofit.data.models.Comments
import com.example.retrofit.data.models.Post
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiService = ApiServiceClient.instance
    val commentListRecycle: RecyclerView by lazy { findViewById(R.id.main_recycle_view) }
    val mTitleSelector: AutoCompleteTextView by lazy { findViewById(R.id.main_menu_selection) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mTitleSelector.setOnItemClickListener { parent, view, position, id ->
            val postTitle = parent.getItemAtPosition(position) as String
            val postId: Int = postTitle.split("-")
                .first()
                .trim()
                .toInt()

            val callGetCommentsByPostId = apiService.getCommentsByPostId(postId)

            callGetCommentsByPostId.enqueue(object : Callback<List<Comments>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<Comments>>,
                    response: Response<List<Comments>>
                ) {
                    if (response.isSuccessful) {
                        val comments = response.body().orEmpty()
                        val commentsAdapter = CommentsAdapter(comments)
                        commentListRecycle.adapter = commentsAdapter
                        commentListRecycle.layoutManager = LinearLayoutManager(baseContext)

                        commentsAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun onStart() {
        super.onStart()

        val callGetPosts = apiService.getPosts()
        callGetPosts.enqueue(object: Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    val postsTitle = posts?.map { "${it.id} - ${it.title}" }?.toList().orEmpty()
                    val adapterList = ArrayAdapter(baseContext, android.R.layout.simple_list_item_1, postsTitle)
                    mTitleSelector.setAdapter(adapterList)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}