package com.ilham.meme_digest_uas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    var memes:ArrayList<Meme> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/flutter/160719052/nmp/allmemes.php"
        var stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("allmemes", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "success") {
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val memeObj = data.getJSONObject(i)
                        val meme = Meme(
                            memeObj.getInt("id"),
                            memeObj.getString("url"),
                            memeObj.getString("top_text"),
                            memeObj.getString("bottom_text"),
                            memeObj.getString("date"),
                            memeObj.getInt("like_count"),
                            memeObj.getInt("users_id"),
                            false,
                        )
                        memes.add(meme)
                    }
                    updateList()
                }
                Log.d("isiarray", memes.toString())
            },
            {
                Response.ErrorListener { Log.e("allmemes", it.message.toString()) }
            }
        )
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabAdd.setOnClickListener {
            activity?.let{
                val intent = Intent (it, CreateMemeActivity::class.java)
                it.startActivity(intent)
            }

        }
    }

    fun updateList() {
        val lm:LinearLayoutManager = LinearLayoutManager(activity)
        val rv = view?.findViewById<RecyclerView>(R.id.allMemesView)
        rv?.layoutManager = lm
        rv?.setHasFixedSize(true)
        rv?.adapter = MemeAdapter(memes)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}