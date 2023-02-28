package com.pearl.medicap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.BaseClass

class MedicalProfileActivity : BaseClass() {
    //lateinit var backIV:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setLayoutXml()
        changeStatusBarColor()
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
    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_medical_profile)
    }

    override fun initializeViews() {

    }

    override fun initializeClickListners() {
        var backIV = findViewById<ImageView>(R.id.iv_back)
        backIV.setOnClickListener {
            this.finish()
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