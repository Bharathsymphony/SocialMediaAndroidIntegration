package com.my_app.socialmediaandroidintegration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var googlebutton:Button
    lateinit var facebookbutton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googlebutton=findViewById(R.id.googleButton)
        facebookbutton=findViewById(R.id.facebookButton)

        googlebutton.setOnClickListener{
            val googleintent=Intent(this@MainActivity,GoogleActivity::class.java)
            startActivity(googleintent)
        }

        facebookbutton.setOnClickListener{
            val facebookintent=Intent(this@MainActivity,FacebookActivity::class.java)
            startActivity(facebookintent)
        }
    }
}