package com.ilham.meme_digest_uas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_meme.*
import org.json.JSONObject

class CreateMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meme)

        var sharedName = this?.packageName
        var shared = this?.getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        var userId = shared?.getString("ACTIVEUSERID", null)

        var image_url = txtInputUrl.text
        txtInputUrl.addTextChangedListener {
            Picasso.get().load(image_url.toString()).into(imgPreview)
        }

        var top_text = txtInputTopText.text
        var bottom_text = txtInputBottomText.text
        txtInputTopText.addTextChangedListener {
            txtViewTopText.setText(top_text)
        }
        txtInputBottomText.addTextChangedListener {
            txtViewBottomText.setText(bottom_text)
        }



        btnSubmit.setOnClickListener {
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/flutter/160719052/nmp/newmemes.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                {
//                        sukses
                    Log.d("cekcreatememe", it.toString())
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        Toast.makeText(this, "New Meme Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                },
                {
//                        error
                    Log.e("cekcreatememe", it.message.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["url"] = image_url.toString()
                    params["top_text"]= top_text.toString()
                    params["bottom_text"]= bottom_text.toString()
                    params["users_id"]= userId.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }

//        btnback.setOnClickListener {
//            finish()
//        }

    }
}