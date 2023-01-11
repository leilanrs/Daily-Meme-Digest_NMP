package com.ilham.meme_digest_uas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.json.JSONObject


class SettingsFragment : Fragment() {

//    var firstName = "";
//    var lastName = "";
//    var username = "";
//    var avatarLink = ""
//    var privacy = ""
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var sharedName= activity?.packageName
        var shared = this.requireActivity().getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        userId = shared.getString("ACTIVEUSERID", "").toString()

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/flutter/160719052/nmp/detail_user.php"
        var stringRequest = object:StringRequest(
            Method.POST, url,
            Response.Listener<String> {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "success") {
                    txtFirstLastName.text =  obj.getString("first_name") + " " + obj.getString("last_name")
                    txtActiveMonth.text = "Active since " + obj.getString("registration_date")
                    txtUsernameSettings.text= "@"+obj.getString("username")
                    txtFirstNameSettings.setText( obj.getString("first_name"))
                    txtLastNameSettings.setText(obj.getString("last_name"))
                    Picasso.get().load(obj.getString("avatar_link")).into(imgSetting)
                    checkBoxHideMyName.isChecked = obj.getString("privacy") == "1"

                } else {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                Log.e("apiresult", it.message.toString())
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = userId.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedName= activity?.packageName
        var shared = this.requireActivity().getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        userId = shared.getString("ACTIVEUSERID", "").toString()

        fabLogout.setOnClickListener {
            shared.edit().clear().apply()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            this.requireActivity().finish()
        }

        btnSaveChanges.setOnClickListener {
            var inputFirstName = txtFirstNameSettings.text
            var inputLastName = txtLastNameSettings.text
            var inputPrivacy = checkBoxHideMyName.isChecked
            Log.d("PRIVACY", inputPrivacy.toString())
            val q = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/flutter/160719052/nmp/updateProfile.php"
            var stringRequest = object:StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        Toast.makeText(activity, "Update Success", Toast.LENGTH_SHORT).show()
                        txtFirstLastName.setText("$inputFirstName $inputLastName")
                    }else{
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    Log.e("apiresult", it.message.toString())
                }
            ){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["first_name"] = inputFirstName.toString()
                    params["last_name"] = inputLastName.toString()
                    var savePrivacy: Int
                    if (inputPrivacy) {
                        savePrivacy = 1
                    } else savePrivacy = 0
                    params["privacy"] = savePrivacy.toString()
                    params["users_id"] = userId
                    return params
                }
            }
            q.add(stringRequest)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}