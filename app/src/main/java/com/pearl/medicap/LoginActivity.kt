package com.pearl.medicap

import com.pearl.medicap.pearlLib.BasePublic
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.pearl.medicap.pearlLib.Session

class LoginActivity : BasePublic() {
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var loginbtn: TextView? = null
    override var session: Session? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = Session(this@LoginActivity)
        if (session!!.hasSession!!) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        // CheckSession(getApplicationContext(),activityIn);
        setLayoutXml()
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
        }

        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = window
            // Making notification bar transparent
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //            window.setStatusBarColor(Color.TRANSPARENT);
            window.statusBarColor = resources.getColor(R.color.register_bk_color)
        }
    }

    fun onLoginClick(View: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_login)
    }

    override fun initializeViews() {
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextEmail = findViewById(R.id.editTextEmail)
        loginbtn = findViewById(R.id.loginbtn)
    }

    override fun initializeClickListners() {
        loginbtn!!.setOnClickListener { view: View? ->
            val email = editTextEmail!!.text.toString()
            val pass = editTextPassword!!.text.toString()
            // validatedata(email,pass);
            if (email == "rajat" && pass == "123") {
                session!!.hasSession = true
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else if (email == "") {
                setCustomError("Please Enter Email", editTextEmail!!)
            } else if (pass == "") {
                setCustomError("Please Enter Password", editTextPassword!!)
            } else {
                Toast.makeText(this, "Email Or Password Not Matched !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initializeInputs() {}
    override fun initializeLabels() {}
}