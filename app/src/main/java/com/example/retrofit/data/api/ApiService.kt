package com.example.retrofit.data.api

import com.example.retrofit.data.models.Comments
import com.example.retrofit.data.models.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/posts")
    fun getPosts(): Call<List<Post>>

    @GET("/posts/{postId}/comments")
    fun getCommentsByPostId(@Path("postId") postId: Int): Call<List<Comments>>

}