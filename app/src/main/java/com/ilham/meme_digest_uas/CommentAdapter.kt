package com.ilham.meme_digest_uas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter (val context: Context, val comments: ArrayList<Comment>):RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
    class CommentViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.card_comment, parent, false)
        return CommentViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return comments.size
    }

}