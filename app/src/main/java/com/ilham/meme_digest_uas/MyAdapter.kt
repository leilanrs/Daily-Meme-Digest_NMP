package com.ilham.meme_digest_uas

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(val activity: AppCompatActivity, val fragments:ArrayList<Fragment>) : FragmentStateAdapter(activity) {
        override fun getItemCount()=fragments.size

        override fun createFragment(position: Int) = fragments[position]
}