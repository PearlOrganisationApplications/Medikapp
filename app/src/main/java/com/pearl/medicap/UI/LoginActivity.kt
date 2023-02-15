package com.pearl.medicap.UI

import com.pearl.medicap.pearlLib.BasePublic
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.PrefManager
import com.pearl.medicap.pearlLib.Session

class LoginActivity : BasePublic() {
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var loginbtn: TextView? = null
    override var session: Session? = null
    lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = Session(this@LoginActivity)
        prefManager=PrefManager(this)
        if (session!!.hasSession!!) {
            //startActivity(Intent(applicationContext, MainActivity::class.java))

            if (prefManager.isCustomerlogin){

                startActivity(Intent(this, Customer_Dashboard::class.java))
            }
            else if(prefManager.isMedicallogin){
                startActivity(Intent(this, Medical_Dashboard::class.java))
            }
            finish()
        }

        setLayoutXml()
        changeStatusBarColor()
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
        else{
            Toast.makeText(this,"Please connnect with internet",Toast.LENGTH_SHORT).show()
        }
    }

    fun onLoginClick(View: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_login)
    }
    override fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = window
            // Making notification bar transparent
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //            window.setStatusBarColor(Color.TRANSPARENT);
            //  window.statusBarColor = resources.getColor(R.color.register_bk_color)
            window.statusBarColor = resources.getColor(R.color.App_color)
        }
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
            if (email == "customer@gmail.com" && pass == "1234") {
                session!!.hasSession = true
               // startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                startActivity(Intent(this, Customer_Dashboard::class.java))
                finish()
            }
            else if (email=="medical@gmail.com"&& pass=="1234"){
                startActivity(Intent(this, Medical_Dashboard::class.java))
                finish()
            }
            else if (email == "") {
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