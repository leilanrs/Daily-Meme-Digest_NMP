package com.ilham.meme_digest_uas

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_meme.view.*
import kotlinx.android.synthetic.main.leaderboard_item.view.*

class LeaderboadAdapter ( val leaderboards: ArrayList<Leaderboard>): RecyclerView.Adapter<LeaderboadAdapter.LeaderboardViewHolder>() {
    class LeaderboardViewHolder(val v: View): RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.leaderboard_item, parent, false)
        return LeaderboardViewHolder(v)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val url = leaderboards[position].avatar
        Log.d("avaUrl", url)
        Picasso.get().load(url).into(holder.v.imgAvaLeader)
        if(leaderboards[position].privacy!=1){
        holder.v.txtNameLeaderboard.text = leaderboards[position].first_name +" "+ leaderboards[position].last_name
        }else{
            var name= (leaderboards[position].first_name+leaderboards[position].last_name).substring(0,3)+(leaderboards[position].first_name+leaderboards[position].last_name).substring(3).replace(Regex("[a-zA-Z]"),"*")
            holder.v.txtNameLeaderboard.text = name
        }
        holder.v.btnLikeLeaderboard.text = leaderboards[position].total_likes.toString()

    }


    override fun getItemCount(): Int {
        return leaderboards.size
    }
}