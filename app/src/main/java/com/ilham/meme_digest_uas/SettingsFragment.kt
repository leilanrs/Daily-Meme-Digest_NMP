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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.json.JSONObject


class SettingsFragment : Fragment() {

    private var firstName =""
    private var lastName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var sharedName= "com.ilham.meme_digest_uas"
        var shared = this.requireActivity().getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        var userId = shared?.getString("ACTIVEUSERID", null)
        Log.d("userid",userId.toString())


//        var username =""
//        var regist_date=""
//        var avatar = ""
//        var privacy = 0

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/flutter/160719052/nmp/detail_user.php"
        var stringRequest = object:StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "success") {
                    firstName=obj.getString("first_name")
                    Log.d("firstname",firstName)
                    txtFirstLastName.text =  obj.getString("first_name")+" "+ obj.getString("last_name")
                    txtActiveMonth.text = "Active since "+obj.getString("registration_date")
                    txtUsernameSettings.text="@"+obj.getString("username")
                    txtFirstNameSettings.setText( obj.getString("first_name"))
                    txtLastNameSettings.setText(obj.getString("last_name"))
                    if(obj.getString("privacy")=="1"){
                        checkBoxHideMyName.isChecked=true
                    }

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
                params["id"] = userId.toString()
                return params
            }
        }
        q.add(stringRequest)
//        txtActiveMonth.text="Active since "+ regist_date
//        txtUsernameSettings.text=username

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
        var sharedName= "com.ilham.meme_digest_uas"
        var shared = this.requireActivity().getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        var userId = shared?.getString("ACTIVEUSERID", null)
        var privacy=0;

        btnSaveChanges.setOnClickListener {
            firstName=txtFirstNameSettings.text.toString()
            lastName=txtLastNameSettings.text.toString()
            if(checkBoxHideMyName.isChecked==true){
                privacy=1;
            }
            val q = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/flutter/160719052/nmp/updateProfile.php"
            var stringRequest = object:StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        Toast.makeText(activity, "Update Succes", Toast.LENGTH_SHORT).show()
                        txtFirstLastName.setText(firstName+" "+lastName)
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
                    params["first_name"] = firstName
                    params["last_name"] = lastName
                    params["privacy"] = privacy.toString()
                    params["users_id"] = userId.toString()
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