package com.my_app.socialmediaandroidintegration


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class GoogleProfileActivity : AppCompatActivity() {

    lateinit var image:ImageView
    lateinit var name:TextView
    lateinit var mailId:TextView
    lateinit var signout:Button
    lateinit var backButton:Button

    lateinit var mGoogleSignInClient:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_profile)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        image=findViewById(R.id.profileimage)
        name=findViewById(R.id.profilename)
        mailId=findViewById(R.id.profilemail)
        signout=findViewById(R.id.profilesignout)
        backButton=findViewById(R.id.googleProfileBackButton)

        backButton.setOnClickListener {
            val backIntent= Intent(this@GoogleProfileActivity,GoogleActivity::class.java)
            startActivity(backIntent)
        }

        signout.setOnClickListener(View.OnClickListener {
            signOut()
        })

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl

            name.text = "Name: $personName"
            mailId.text = "MailId: $personEmail"

            Glide.with(this).load(personPhoto.toString()).into(image)

        }

    }
    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                mGoogleSignInClient.revokeAccess()
                    .addOnCompleteListener(this) {
                        // ...
                    }

                Toast.makeText(this@GoogleProfileActivity, "Signed Out Successfully", Toast.LENGTH_SHORT).show()
                val intent= Intent(this@GoogleProfileActivity,GoogleActivity::class.java)
                startActivity(intent)
            }
    }
}