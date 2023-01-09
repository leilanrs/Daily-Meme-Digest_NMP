package com.ilham.meme_digest_uas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/flutter/160719052/nmp/detailmeme.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("memeDetail", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "success") {
//                    val data = obj.getJSONArray("data")
//                    Log.d("meme","32")
//                    for (i in 0 until data.length()) {
//                        Log.d("meme","34")
//                        val detailObj = data.getJSONObject(i)
//                        val commentData = data.getJSONObject(i).getJSONObject("comments")
//                        val detailMeme = Meme(
//                            detailObj.getInt("id"),
//                            detailObj.getString("url"),
//                            detailObj.getString("top_text"),
//                            detailObj.getString("bottom_text"),
//                            detailObj.getString("date"),
//                            detailObj.getInt("like_count"),
//                            detailObj.getInt("users_id"),
//                            false,
//                        )
//                        memeArray.add(detailMeme)
//                        Log.d("meme","48")
//                        for (i in 0 until commentData.length()) {
//                            val comment = Comment(
//                                commentData.getInt("memes_id"),
//                                commentData.getInt("users_id"),
//                                commentData.getString("comment"),
//                                commentData.getString("date"),
//                                commentData.getString("username"),
//                            )
//                            commentArray.add(comment)
//                        }
//                    }
                }
            },
            {
                Log.e("memeDetail", it.message.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = id.toString()
                return params
            }
        }
        q.add(stringRequest)
        Log.d("tesisiaray", memeArray.toString())

    }
}