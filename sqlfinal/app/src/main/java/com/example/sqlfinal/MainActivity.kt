package com.example.sqlfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlfinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val fragmentTransaction
                = supportFragmentManager.beginTransaction()
        val fragment = fragment1()
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}