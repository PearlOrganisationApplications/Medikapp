package com.pearl.medicap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.pearl.medicap.pearlLib.BaseClass

class Medical_Dashboard : BaseClass() {
    lateinit var drawer_button: ImageView
    lateinit var drawer: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setLayoutXml()
        changeStatusBarColor()
        val isConnected = isNetworkConnected(this.applicationContext)
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("Customer_Dashboard", "onCreate", "initConnected")
            initializeViews()
            initializeClickListners()
            initializeLabels()
            initializeInputs()
            printLogs("Customer_Dashboard", "onCreate", "exitConnected")
        }

    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_medical_dashboard)
    }
    override fun changeStatusBarColor() {
        window.statusBarColor=resources.getColor(R.color.App_color)
    }

    override fun initializeViews() {
        drawer_button=findViewById(R.id.sidebar)
        drawer=findViewById(R.id.drawer_layout)
    }

    override fun initializeClickListners() {
        drawer_button.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }
    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }


}