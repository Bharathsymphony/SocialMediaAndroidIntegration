package com.my_app.socialmediaandroidintegration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONObject


class FacebookActivity : AppCompatActivity() {

    lateinit var callbackManager:CallbackManager
    lateinit var loginButton: LoginButton
    lateinit var backButton:Button

    lateinit var facebookName:TextView
    lateinit var facebookMail:TextView
    lateinit var facebookGender:TextView
    lateinit var facebookBirthday:TextView
    lateinit var facebookImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)

        loginButton=findViewById(R.id.login_button)
        facebookName=findViewById(R.id.facebookname)
        facebookMail=findViewById(R.id.facebookemail)
        facebookImage=findViewById(R.id.facebookimage)
        facebookGender=findViewById(R.id.facebookgender)
        facebookBirthday=findViewById(R.id.facebookbirthday)
        backButton=findViewById(R.id.facebookBackButton)

        facebookName.visibility=View.GONE
        facebookMail.visibility=View.GONE
        facebookBirthday.visibility=View.GONE
        facebookGender.visibility=View.GONE

        backButton.setOnClickListener {
            val backIntent=Intent(this@FacebookActivity,MainActivity::class.java)
            startActivity(backIntent)
        }

        loginButton.setOnClickListener {
            loginButton.setReadPermissions(listOf("email","public_profile","user_gender","user_birthday"))
            callbackManager= CallbackManager.Factory.create()

            var accessToken = AccessToken.getCurrentAccessToken()
            var isLoggedIn = accessToken != null && !accessToken!!.isExpired
            if(accessToken!=null) {

                facebookName.visibility = View.GONE
                facebookMail.visibility = View.GONE
                facebookBirthday.visibility = View.GONE
                facebookGender.visibility = View.GONE
                facebookImage.visibility=View.GONE

            }
            else {

                LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult?> {
                        override fun onSuccess(loginResult: LoginResult?) {
                            val graphRequest =
                                GraphRequest.newMeRequest(loginResult?.accessToken) { obj, response ->
                                    getFacebookData(obj)
                                }

                            val param = Bundle()
                            param.putString("fields", "name,email,id,birthday,gender")
                            graphRequest.parameters = param
                            graphRequest.executeAsync()
                        }

                        override fun onCancel() {

                        }

                        override fun onError(exception: FacebookException) {
                            // App code
                        }
                    })
                facebookName.visibility = View.VISIBLE
                facebookMail.visibility = View.VISIBLE
                facebookBirthday.visibility = View.VISIBLE
                facebookGender.visibility = View.VISIBLE
                facebookImage.visibility=View.VISIBLE
            }
        }
    }

    private fun getFacebookData(obj:JSONObject) {



        val name=obj.getString("name")
        val birthday=obj.getString("birthday")
        val gender=obj.getString("gender")
        val email=obj.getString("email")
        val id=obj.getString("id")
        val image_url="https://graph.facebook.com/"+id+"/picture?type=normal"

        Glide.with(this).load(image_url).into(facebookImage)
        facebookName.setText("Name:$name")
        facebookMail.setText("Email:$email")
        facebookGender.setText("Gender:$gender")
        facebookBirthday.setText("BirthDay:$birthday")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


}