package com.example.mybr

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mybr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var broadcastReceiver :BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPermission()
        init()
        if (intent.hasExtra("msg")){
            val msg = intent.getStringExtra("msg")
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent!=null){
            if (intent.hasExtra("msg")){
                val msg = intent.getStringExtra("msg")
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init(){
        broadcastReceiver = object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent != null){
                    if (intent.action.equals(Intent.ACTION_POWER_CONNECTED))
                        Toast.makeText(context,"배터리 충전 시작",Toast.LENGTH_SHORT).show()

                }
            }

        }
    }
    fun initPermission(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"문자 수신 동의함", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.RECEIVE_SMS),100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==100){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"문자 수신 동의함.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"문자 수신 동의 필요함.", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)

    }
}