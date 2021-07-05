package com.example.myvoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myvoc.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val menuName = arrayListOf("단어장", "객관식", "검색")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
//        fragmentMain()
    }

    private fun init() {
        binding.pager.adapter = FragStateAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager){
                tab, position ->
            tab.text = menuName[position]
        }.attach()
    }
}