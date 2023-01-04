package com.ilham.meme_digest_uas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_meme.view.*

class MemeAdapter( val memes: ArrayList<Meme>): RecyclerView.Adapter<MemeAdapter.MemeViewHolder>() {
    class MemeViewHolder(val v: View): RecyclerView.ViewHolder(v)

    companion object{
    }

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

        val posisi = position
        holder.v.btnLike.setOnClickListener {
            val q = Volley.newRequestQueue(it.context)
            val url = "https://ubaya.fun/flutter/160719052/nmp/likememe.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                {
                    Log.d("cekparams", it)
                    memes[posisi].likeCount++
                    var newlikes = memes[position].likeCount
                    holder.v.btnLike.text = "$newlikes LIKES"
                },
                {
                    Log.e("cekparams", it.message.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val map = HashMap<String, String>()
                    map.set("id", memes[posisi].id.toString())
                    return map
                }
            }
            q.add(stringRequest)
        }


        var id = memes[position].id
        holder.v.btnComment.setOnClickListener {
            Log.d("memeId", id.toString())
            val activity = holder.v.context as Activity
            val intent = Intent(activity, DetailMemeActivity::class.java)
            intent.putExtra("memeIDForDetail", id)
            holder.v.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return memes.size
    }
}