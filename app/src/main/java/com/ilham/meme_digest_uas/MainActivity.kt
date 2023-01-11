package com.ilham.meme_digest_uas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    val fragments : ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)


        fragments.add(HomeFragment())
        fragments.add(MyCreationFragment())
        fragments.add(LeaderboardFragment())
        fragments.add(SettingsFragment())

        viewPager.adapter = MyAdapter(this, fragments)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                bottomNav.selectedItemId = bottomNav.menu[position].itemId
            }
        })

        bottomNav.setOnItemSelectedListener {
            viewPager.currentItem = when(it.itemId) {
                R.id.ItemHome -> 0
                R.id.ItemMyCreation -> 1
                R.id.ItemLeaderboards -> 2
                R.id.ItemSettings -> 3
                else -> 0 // default to home
            }
            true
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        var drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name,
                R.string.app_name)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.itemHome -> viewPager.currentItem=0
                R.id.itemMyCreation -> viewPager.currentItem=1
                R.id.itemLeaderboard -> viewPager.currentItem=2
                R.id.itemSetting -> viewPager.currentItem=3
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

//        var sharedName = packageName
//        var shared = getSharedPreferences(sharedName, Context.MODE_PRIVATE)
//
//        var username = shared.getString("USERNAME", "")
//        val navigationView: NavigationView =  findViewById(R.id.navView)
//        val header: View = navigationView.getHeaderView(0)
//        val tv: TextView = header.findViewById(R.id.txtUsername)
//        val image: ImageView = header.findViewById(R.id.imgDrawer)
//        tv.text = "lala"
//        image.setImageResource(R.drawable.your_image)
//        fabLogoutDrawer.setOnClickListener {
//            var sharedName = packageName
//            var shared = getSharedPreferences(sharedName, Context.MODE_PRIVATE)
//            shared.edit().clear().apply()
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }


    }
}