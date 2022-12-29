package com.ilham.meme_digest_uas

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_meme.view.*

class MemeAdapter(val memes: ArrayList<Meme>): RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {
    class MemeViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.card_meme, parent, false)
        return MemeViewHolder(v)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val url = memes[position].memeUrl
        Log.d("memeUrl", url)
        Picasso.get().load(url).into(holder.v.imgMeme)
        holder.v.txtTopText.text = memes[position].topText
        holder.v.txtBotText.text = memes[position].botText
        holder.v.btnLike.text = memes[position].likeCount.toString()
    }

    override fun getItemCount(): Int {
        return memes.size
    }
}