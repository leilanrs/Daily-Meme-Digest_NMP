package com.ilham.meme_digest_uas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detail_meme.*
import org.json.JSONObject

class DetailMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meme)

        var memeArray:ArrayList<Meme> = ArrayList()
        var commentArray:ArrayList<Comment> = ArrayList()

        var id = intent.getIntExtra(MemeAdapter.memeIDForDetail, 0)
        Log.d("memeId in detail", id.toString())
        val qMeme = Volley.newRequestQueue(this)
        val urlMeme = "https://ubaya.fun/flutter/160719052/nmp/detailmeme.php"
        val stringRequestMeme = object : StringRequest(
            Method.POST,
            urlMeme,
            {
                Log.d("memeDetail", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "success") {
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val detailObj = data.getJSONObject(i)
                        val detailMeme = Meme(
                            detailObj.getInt("id"),
                            detailObj.getString("url"),
                            detailObj.getString("top_text"),
                            detailObj.getString("bottom_text"),
                            detailObj.getString("date"),
                            detailObj.getInt("like_count"),
                            detailObj.getInt("users_id"),
                            false,
                        )
                        memeArray.add(detailMeme)
                    }
                }
            },
            {
                Log.e("memeDetailError", it.message.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = id.toString()
                return params
            }
        }
        qMeme.add(stringRequestMeme)
        Log.d("arrayMeme", memeArray.toString())

        val qComment = Volley.newRequestQueue(this)
        val urlComment = "https://ubaya.fun/flutter/160719052/nmp/getcomments.php"
        val stringRequestComment = object : StringRequest (
            Method.POST,
            urlComment,
            {
                Log.d("comments", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "success") {
                    val data = obj.getJSONArray("comments")
                    for (i in 0 until data.length()) {
                        val commentObj = data.getJSONObject(i)
                        val comment = Comment(
                            commentObj.getInt("memes_id"),
                            commentObj.getInt("users_id"),
                            commentObj.getString("comment"),
                            commentObj.getString("date"),
                            commentObj.getString("username"),
                        )
                        commentArray.add(comment)
                    }
                }
            },
            {
                Log.e("commentError", it.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id.toString()
                return params
            }
        }
        qComment.add(stringRequestComment)
        Log.d("arrayComment", commentArray.toString())

        btnBackDetail.setOnClickListener { finish() }
    }
}