package com.example.mydictionary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydictionary.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val textarr = arrayListOf<String>("단어장", "즐겨찾는 단어", "네이버검색")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        binding.viewPager.adapter = MyFragStateAdapter(this)
        TabLayoutMediator(binding.tab, binding.viewPager){
                tab, position ->
            tab.text = textarr[position]
        }.attach()
    }



}

