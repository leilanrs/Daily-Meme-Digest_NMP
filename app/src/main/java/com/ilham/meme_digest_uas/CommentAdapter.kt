package com.ilham.meme_digest_uas

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_comment.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CommentAdapter (val comments: ArrayList<Comment>):RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
    class CommentViewHolder (val v:View):RecyclerView.ViewHolder(v)

    companion object {
//        val memeIDForDetail = "memeIDForDetail"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.card_comment, parent, false)
        return CommentViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.v.txtNameComment.text = comments[position].username
        holder.v.txtIsiComment.text = comments[position].comment
    }

    override fun getItemCount(): Int {
        return comments.size
    }

}