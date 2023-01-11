package com.ilham.meme_digest_uas

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_meme.*
import org.json.JSONObject

class DetailMemeActivity : AppCompatActivity() {
    var memeArray:ArrayList<Meme> = ArrayList()
    var commentArray:ArrayList<Comment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meme)

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
                            detailObj.getInt("totalComments"),
                        )
                        memeArray.add(detailMeme)
                        Log.d("isiMemeArray", memeArray.toString())
                        for (i in 0 until memeArray.size) {
                            Picasso.get().load(memeArray[i].memeUrl).into(imgViewDetail)
                            txtTopDetail.text = memeArray[i].topText
                            txtBottomDetail.text = memeArray[i].botText
                            btnDetailLike.text = memeArray[i].likeCount.toString()+" Likes"
                        }
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
//        for(element in memeArray) {
//            Picasso.get().load(memeArray[i].memeUrl).into(imgViewDetail)
//        }

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
                        Log.d("isiCommentArray", commentArray.toString())
                    }
                    updateList()
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

        txtInputComment.addTextChangedListener {
            btnSend.isEnabled = !txtInputComment.text.isNullOrEmpty()
        }

        btnSend.setOnClickListener {
            var sharedName = packageName
            var shared = getSharedPreferences(sharedName, Context.MODE_PRIVATE)
            var userId = shared.getString("ACTIVEUSERID", null)

            var inputComment = txtInputComment.text
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/flutter/160719052/nmp/newcomment.php"
            var stringRequest = object:StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> {
                    Log.d("cekSendComment", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        Toast.makeText(this, "Comment added Successfully", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    Log.e("apiresult", it.message.toString())
                }
            ){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["memes_id"] = id.toString()
                    params["users_id"] = userId.toString()
                    params["comment"]=inputComment.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }

        btnBackDetail.setOnClickListener { finish() }
    }

    fun updateList() {
        val lm = LinearLayoutManager(this)
        val rv = findViewById<RecyclerView>(R.id.commentsView)
        rv.layoutManager = lm
        rv.setHasFixedSize(true)
        rv.adapter = CommentAdapter( commentArray)
    }

}