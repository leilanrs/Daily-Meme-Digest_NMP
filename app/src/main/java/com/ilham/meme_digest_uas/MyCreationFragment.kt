package com.ilham.meme_digest_uas

import android.content.Context
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
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [MyCreationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyCreationFragment : Fragment() {

    var memes:ArrayList<Meme> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var sharedName = context?.packageName
        var shared = context?.getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        var userId = shared?.getString("ACTIVEUSERID", null)

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/flutter/160719052/nmp/mymemes.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("mymemes", "$it, userid: $userId")
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
                            memeObj.getInt("totalComments"),
                        )
                        memes.add(meme)
                    }
                    updateList()
                }
                Log.d("isiarray", memes.toString())
            },
            {
                Response.ErrorListener { Log.e("mymemes", it.message.toString()) }
            }
        )
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["users_id"] = userId.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        val rv = view?.findViewById<RecyclerView>(R.id.myCreationsView)
        rv?.layoutManager = lm
        rv?.setHasFixedSize(true)
        rv?.adapter = MemeAdapter(memes)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_creation, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyCreationFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}