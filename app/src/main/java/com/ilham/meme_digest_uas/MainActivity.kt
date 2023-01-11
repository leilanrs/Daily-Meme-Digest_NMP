package com.ilham.meme_digest_uas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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

        var sharedName = packageName
        var shared = getSharedPreferences(sharedName, Context.MODE_PRIVATE)

        var username = shared.getString("USERNAME", "")
        txtUsernameDrawer.text=username.toString()

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        var drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.app_name,
                R.string.app_name)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.itemHome -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                R.id.itemMyCreation -> Toast.makeText(this, "My Creation", Toast.LENGTH_SHORT)
                    .show()
                R.id.itemLeaderboard -> Toast.makeText(this, "Leaderboard", Toast.LENGTH_SHORT)
                    .show()
                R.id.itemSetting -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

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