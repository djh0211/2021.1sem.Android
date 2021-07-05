package com.example.fbdictionaryfragver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {

    //    val SPLASH_VIEW_TIME:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val iv_bg = findViewById<ImageView>(R.id.imageView)


        iv_bg.alpha = 0f
        iv_bg.animate().setDuration(2000).alpha(1f).withEndAction {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

//        Handler().postDelayed ({
//            startActivity(Intent(this,MainActivity::class.java))
//            finish()
//        },SPLASH_VIEW_TIME)
    }
}