package com.example.retrofit.services

data class Comments(
    val postId: Int,
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
