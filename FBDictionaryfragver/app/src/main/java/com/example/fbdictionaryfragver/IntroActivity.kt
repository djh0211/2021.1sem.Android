package com.example.fbdictionaryfragver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        init()
    }
    private fun init() {
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        supportFragmentManager.addOnBackStackChangedListener {
            var fg = supportFragmentManager.findFragmentById(R.id.frameLayout)
            var fg_str = fg.toString().split("{")[0]
            if (fg_str == "MainFragment"){
                bottomNavBar.menu.getItem(0).isChecked = true
            } else if (fg_str == "AddFragment"){
                bottomNavBar.menu.getItem(1).isChecked = true
            }
            else if (fg_str == "BookMarkFragment"){
                bottomNavBar.menu.getItem(2).isChecked = true
            }
            else if (fg_str == "QuizFragment"){
                bottomNavBar.menu.getItem(3).isChecked = true
            }
            else if (fg_str == "InternetFragment"){
                bottomNavBar.menu.getItem(4).isChecked = true
            }

        }

        replaceFragment(HomeFragment())

        bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {

                R.id.voca_page -> {
                    replaceFragment(VocaFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_page -> {
                    replaceFragment(AddFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.bookmark_page -> {
                    replaceFragment(BookMarkFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.quiz_page -> {
                    replaceFragment(QuizFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.naver_page -> {
                    replaceFragment(InternetFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
    }
}