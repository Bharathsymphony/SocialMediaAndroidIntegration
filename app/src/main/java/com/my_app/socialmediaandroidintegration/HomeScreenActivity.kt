package com.my_app.socialmediaandroidintegration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        Handler().postDelayed({
            val startAct= Intent(this@HomeScreenActivity, MainActivity::class.java)
            startActivity(startAct)},2000)
    }
}