package com.ilham.meme_digest_uas

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import okio.ByteString
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest


class SettingsFragment : Fragment() {

    var userId = ""
    val REQ_IMAGE_CAPTURE = 1
    val REQ_IMAGE_PICK=2

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

    fun takePicture() {
        val i = Intent()
        i.action = MediaStore.ACTION_IMAGE_CAPTURE
        startActivityForResult(i, REQ_IMAGE_CAPTURE)
    }

    fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra("outputFormat",
            Bitmap.CompressFormat.JPEG.toString())
        startActivityForResult(intent, REQ_IMAGE_PICK)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQ_IMAGE_CAPTURE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture()
                    pickImageGallery()
                } else {
                    Toast.makeText(requireContext(), "You need to grant permissions to access the camera.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_IMAGE_CAPTURE ) {
                val extras = data!!.extras
                val imageBitmap: Bitmap = extras!!.get("data") as Bitmap
                imgSetting.setImageBitmap(imageBitmap)
                val stream = ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val bytes = stream.toByteArray()
                val sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                val q = Volley.newRequestQueue(activity)
                val url = "https://ubaya.fun/flutter/160719052/nmp/updateavatar.php"
                val stringRequest = object : StringRequest(
                    Method.POST, url,
                    {
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "success") {
                            Toast.makeText(activity, "Update Success", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        Log.e("updateAvatarResult", it.message.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["users_id"] = userId
                        params["image"] = sImage
                        return params
                    }
                }
                q.add(stringRequest)
            }
            if (requestCode == REQ_IMAGE_PICK ) {
                val uri = data?.data
                if (uri != null) {
                    imgSetting.setImageURI(uri)
                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,uri)
                    var imageBitmap: Bitmap? = null
                    imageBitmap = bitmap
                    val stream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val bytes = stream.toByteArray()
                    val sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                    Log.d("hasilimage", sImage)
                    val q = Volley.newRequestQueue(activity)
                    val url = "https://ubaya.fun/flutter/160719052/nmp/updateavatar.php"
                    val stringRequest = object : StringRequest(
                        Method.POST, url,
                        {
                            val obj = JSONObject(it)
                            if (obj.getString("result") == "success") {
                                Toast.makeText(activity, "Update Success", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                            }
                        },
                        {
                            Log.e("updateAvatarResult", it.message.toString())
                        }
                    ) {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            params["users_id"] = userId
                            params["image"] = sImage
                            return params
                        }
                    }
                    q.add(stringRequest)
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedName= activity?.packageName
        var shared = this.requireActivity().getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        userId = shared.getString("ACTIVEUSERID", "").toString()

        fabLogout.setOnClickListener {
            val alert = android.app.AlertDialog.Builder(activity)
            alert.setTitle("Daily Meme Digest")
            alert.setMessage("Are you sure you want to logout?")
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                shared.edit().clear().apply()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                this.requireActivity().finish()
            })
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
            })
            alert.create().show()
        }

        imgSetting.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), REQ_IMAGE_CAPTURE)
            } else {
                val takePictOptions = arrayOf<String>("Camera", "Gallery")
                AlertDialog.Builder(activity)
                    .setTitle("Ambil gambar melalui")
                    .setItems(takePictOptions) { _, which-> when (which) {
                        0 -> takePicture()
                        1 -> pickImageGallery()
                    } }
                    .create()
                    .show()
            }
        }


        btnSaveChanges.setOnClickListener {
            var inputFirstName = txtFirstNameSettings.text
            var inputLastName = txtLastNameSettings.text
            var inputPrivacy = checkBoxHideMyName.isChecked
            Log.d("PRIVACY", inputPrivacy.toString())
            val q = Volley.newRequestQueue(activity)
            val url = "https://ubaya.fun/flutter/160719052/nmp/updateProfile.php"
            var stringRequest = object:StringRequest(
                Method.POST, url,
                Response.Listener<String> {
                    Log.d("apiresult", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        Toast.makeText(activity, "Update Success", Toast.LENGTH_SHORT).show()
                        txtFirstLastName.setText("$inputFirstName $inputLastName")
                        var editor: SharedPreferences.Editor = shared.edit()
                        editor.putString("FIRSTNAME",inputFirstName.toString())
                        editor.putString("LASTNAME",inputLastName.toString())
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