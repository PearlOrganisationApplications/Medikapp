package com.pearl.medicap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.BaseClass

class MedicalProfileActivity : BaseClass() {
    //lateinit var backIV:ImageView
    lateinit var edit_button: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutXml()
        changeStatusBarColor()
        initializeViews()
        initializeClickListners()
        val isConnected = isNetworkConnected(this.applicationContext)
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("LoginActivity", "onCreate", "initConnected")
            initializeViews()
            initializeClickListners()
            initializeLabels()
            initializeInputs()
            printLogs("LoginActivity", "onCreate", "exitConnected")
        } else {
            Toast.makeText(this, "Please connnect with internet", Toast.LENGTH_SHORT).show()
        }


        edit_button.visibility = View.VISIBLE


    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_medical_profile)
    }

    override fun initializeViews() {
        edit_button = findViewById(R.id.edit_btn)

    }

    override fun initializeClickListners() {
        var backIV = findViewById<ImageView>(R.id.iv_back)
        backIV.setOnClickListener {
            this.finish()
        }

        edit_button.setOnClickListener {
            val intent = Intent(this@MedicalProfileActivity, EditProfileActivity::class.java)
            intent.putExtra("Usertype", "Medical")
            startActivity(intent)
        }
    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }

    override fun changeStatusBarColor() {
        window.statusBarColor = resources.getColor(R.color.App_color)
    }
}