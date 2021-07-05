package com.example.mydictionaryver4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.dictionary_page -> {
                    val intent = Intent(this, DictionaryActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

//                R.id.dictionary_page -> {
//                    replaceFragment(DictionaryFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }

                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }



}