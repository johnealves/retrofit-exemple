package com.example.retrofit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofit.R
import com.example.retrofit.data.models.Comments
import com.google.android.material.textview.MaterialTextView

class CommentsAdapter(private val commentsList: List<Comments>): Adapter<CommentsAdapter.CommentsViewHolder>() {

    class CommentsViewHolder(view: View): ViewHolder(view) {
        val name: MaterialTextView
        val email: MaterialTextView
        val comment: MaterialTextView

        init {
            name = view.findViewById(R.id.item_comments_name)
            email = view.findViewById(R.id.item_comments_email)
            comment = view.findViewById(R.id.item_comments_comment)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comments, parent, false)
        return CommentsViewHolder(view)
    }

    override fun getItemCount(): Int = commentsList.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.name.text = commentsList[position].name
        holder.email.text = commentsList[position].email
        holder.comment.text = commentsList[position].body
    }

}