package com.pearl.medicap


import android.os.Bundle
import com.pearl.medicap.R
import android.content.Intent
import com.pearl.medicap.LoginActivity
import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.pearl.medicap.pearlLib.BaseClass

class RegisterActivity : BaseClass() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutXml()
        val isConnected = isNetworkConnected(this.applicationContext)
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("RegisterActivity", "onCreate", "initConnected")
            initializeViews()
            initializeClickListners()
            initializeLabels()
            initializeInputs()
            changeStatusBarColor()
            printLogs("RegisterActivity", "onCreate", "exitConnected")
        }
    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_register)
    }

    override fun initializeViews() {}
    override fun initializeClickListners() {}
    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun initializeInputs() {}
    override fun initializeLabels() {}
    @SuppressLint("ObsoleteSdkInt")
    fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            // Making notification bar transparent
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //            window.setStatusBarColor(Color.TRANSPARENT);
            window.statusBarColor = resources.getColor(R.color.register_bk_color)
        }
    }
}