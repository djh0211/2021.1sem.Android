package com.example.myfbtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {

    val storage = Firebase.storage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        init()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.intro -> {
                val fragment = supportFragmentManager.beginTransaction()
                //fragment.addToBackStack(null)
                fragment.replace(R.id.frame, ItemFragment2())
                fragment.commit()

            }

        }
        return super.onOptionsItemSelected(item)
    }


    var receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent!!.getStringExtra("type")
            Log.i("plz",type.toString())
            if(type!=null) {
                when(type) {
                    "수능" -> Log.i("plz2",type)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter("com.example.ItemFragment"))
    }

    private fun init() {

        val fragment = supportFragmentManager.beginTransaction()
        //fragment.addToBackStack(null)
        fragment.replace(R.id.frame, ItemFragment2())
        fragment.commit()


    }
}