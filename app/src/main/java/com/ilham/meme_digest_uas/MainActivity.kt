package com.ilham.meme_digest_uas

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    val fragments : ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        var sharedName = packageName
        var shared = getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        var userinfo = shared.getString("USERINFO", "")

        Log.d("ISIINTENT", intent.getStringExtra("USERINFO").toString())
        Log.d("ISISP", userinfo.toString())

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
                R.id.itemHome -> viewPager.currentItem = 0
                R.id.itemMyCreation -> viewPager.currentItem = 1
                R.id.itemLeaderboard -> viewPager.currentItem = 2
                R.id.itemSetting -> viewPager.currentItem = 3
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }
}