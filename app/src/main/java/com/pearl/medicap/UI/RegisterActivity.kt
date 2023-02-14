package com.pearl.medicap.UI


import android.os.Bundle
import com.pearl.medicap.R
import android.content.Intent
import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.pearl.medicap.pearlLib.BaseClass

class RegisterActivity : BaseClass() {
    lateinit var choose_user_spinner:Spinner

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

    override fun initializeViews() {
        choose_user_spinner=findViewById(R.id.choose_user_spinner)
    }
    override fun initializeClickListners() {}
    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun initializeInputs() {
        var adapter=ArrayAdapter.createFromResource(this,R.array.user_type,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        choose_user_spinner.adapter=adapter
    }
    override fun initializeLabels() {}
    @SuppressLint("ObsoleteSdkInt")
    override fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            // Making notification bar transparent
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //            window.setStatusBarColor(Color.TRANSPARENT);
            window.statusBarColor = resources.getColor(R.color.App_color)
        }
    }
}