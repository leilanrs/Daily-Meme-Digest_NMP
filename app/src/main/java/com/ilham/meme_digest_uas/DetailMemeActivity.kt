package com.ilham.meme_digest_uas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class DetailMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_meme)

        var id = intent.getStringExtra(MemeAdapter.ID)
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.fun/flutter/160719052/nmp/detailmeme.php"

    }
}