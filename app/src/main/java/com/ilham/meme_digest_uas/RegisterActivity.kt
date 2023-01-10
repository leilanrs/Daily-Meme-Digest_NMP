package com.ilham.meme_digest_uas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener {
            if (txtRegisterFirst.text.toString().trim().isEmpty() ||
                txtRegisterLast.text.toString().trim().isEmpty() ||
                txtRegisterUsername.text.toString().trim().isEmpty() ||
                txtRegisterPassword.text.toString().trim().isEmpty() ||
                txtRegisterRepeatPassword.text.toString().trim().isEmpty())
            {
                Toast.makeText(this, "Harap perbaiki input", Toast.LENGTH_SHORT).show()
            } else if (txtRegisterPassword.text.toString() != txtRegisterRepeatPassword.text.toString()) {
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
            } else {
                var regFirst = txtRegisterFirst.text.toString()
                var regLast = txtRegisterLast.text.toString()
                var regUsername = txtRegisterUsername.text.toString()
                var regPassword = txtRegisterPassword.text.toString()

                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.fun/flutter/160719052/nmp/register.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST,
                    url,
                    {
//                        sukses
                        Log.d("cekregister", it.toString())
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "success") {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    },
                    {
//                        error
                        Log.e("cekregister", it.message.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["username"] = regUsername
                        params["first_name"] = regFirst
                        params["last_name"] = regLast
                        params["password"] = regPassword
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }

        btnBackRegister.setOnClickListener {
            finish()
        }
    }
}