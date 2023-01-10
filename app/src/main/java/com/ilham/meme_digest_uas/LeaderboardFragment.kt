package com.ilham.meme_digest_uas

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
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    var leaderboards:ArrayList<Leaderboard> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/flutter/160719052/nmp/get_userranks.php"
        var stringRequest = StringRequest(
            Request.Method.POST,
            url,
            {
                Log.d("alluser", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "success") {
                    val data = obj.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val leaderObj = data.getJSONObject(i)
                        val leaderboard = Leaderboard(
                            leaderObj.getString("avatar_link"),
                            leaderObj.getString("first_name"),
                            leaderObj.getString("last_name"),
                            leaderObj.getString("privacy"),
                            leaderObj.getInt("total_like"),
                        )
                        leaderboards.add(leaderboard)
                    }
                    updateList()
                }
                Log.d("isiarray", leaderboards.toString())
            },
            {
                Response.ErrorListener { Log.e("alluser", it.message.toString()) }
            }
        )
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        val rv = view?.findViewById<RecyclerView>(R.id.leaderboardView)
        rv?.layoutManager = lm
        rv?.setHasFixedSize(true)
        rv?.adapter = LeaderboadAdapter(leaderboards)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}