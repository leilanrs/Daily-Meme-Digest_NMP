package com.ilham.meme_digest_uas

import android.content.Context
import android.content.ContextParams
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var sharedName = packageName
        var shared = getSharedPreferences(sharedName, Context.MODE_PRIVATE)

        var activeInfo = shared.getString("USERINFO", "")
        if (activeInfo != "") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnOpenRegistration.setOnClickListener {
            val intentRegistration = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegistration)
        }

        btnLogin.setOnClickListener {
            var editor:SharedPreferences.Editor = shared.edit()
            var inputUsername = txtUsername.text.toString()
            var inputPassword = txtPassword.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/flutter/160719052/nmp/login.php"
            var stringRequest = object : StringRequest(
                Method.POST,
                url,
                {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        var userId = obj.getString("id")
                        var userFirstName = obj.getString("first_name")
                        var userLastName = obj.getString("last_name")
                        var userUsername = obj.getString("username")
                        var userAvatar = obj.getString("avatar_link")
                        var userPrivacy = obj.getString("privacy")
                        Log.d("userinfo", "$userId, $userFirstName, $userLastName, $userUsername, $userAvatar, $userPrivacy")
                        intent.putExtra("USERINFO", "$userId||$userFirstName||$userLastName||$userUsername||$userAvatar||$userPrivacy")
                        editor.putString("USERINFO", "$userId||$userFirstName||$userLastName||$userUsername||$userAvatar||$userPrivacy")
                        editor.putString("ACTIVEUSERID", userId)
                        editor.apply()
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Username atau password tidak sesuai", Toast.LENGTH_SHORT).show()
                    }
                },
                {
//                        error
                    Log.e("apiresult", it.message.toString())
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = inputUsername
                    params["password"] = inputPassword
                    return params
                }
            }
            q.add(stringRequest)
        }
    }
}