package com.pearl.medicap.UI

import android.Manifest
import com.pearl.medicap.pearlLib.BasePublic
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.pearl.medicap.R
import com.pearl.medicap.api.Methods
import com.pearl.medicap.api.RetrofitClient
import com.pearl.medicap.model.ResponseModel
import com.pearl.medicap.pearlLib.PrefManager
import com.pearl.medicap.pearlLib.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BasePublic(), GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var loginbtn: TextView? = null
    lateinit var forgotBtn: TextView

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_LOCATION = 1
    private var mGoogleApiClient: GoogleApiClient? = null
    override var session: Session? = null
    lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = Session(this@LoginActivity)
        prefManager = PrefManager(this)

        /*   if (session!!.hasSession!!) {
               //startActivity(Intent(applicationContext, MainActivity::class.java))

               if (prefManager.isCustomerlogin){
                   startActivity(Intent(this, Customer_Dashboard::class.java))
               }
              else
               if(prefManager.isMedicallogin){
                   startActivity(Intent(this, Medical_Dashboard::class.java))
               }
               finish()
           }*/

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
        } else {
            Toast.makeText(this, "Please connnect with internet", Toast.LENGTH_SHORT).show()
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
        forgotBtn = findViewById(R.id.forgot_pass)
    }

    override fun initializeClickListners() {
        loginbtn?.setOnClickListener {
            loginUser()
        }
        forgotBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun initializeInputs() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), REQUEST_LOCATION
        )
        mGoogleApiClient =
            GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(this@LoginActivity)
                .addOnConnectionFailedListener(this@LoginActivity).build()
    }

    override fun initializeLabels() {}
    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }

    private fun loginUser() {

        val email = editTextEmail!!.text.toString()
        val pass = editTextPassword!!.text.toString()
        // validatedata(email,pass);


        if(email!=null||email!="" && pass!=null||pass!=""){
            sendUserLoginData(email, pass)
        }

//        if (email == "customer@gmail.com" && pass == "1234") {
//            //session!!.hasSession = true
//            // startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            //+-
//            // prefManager.isCustomerlogin=false
//            startActivity(Intent(this, Customer_Dashboard::class.java))
//            finish()
//        } else if (email == "medical@gmail.com" && pass == "1234") {
//            // prefManager.isMedicallogin=false
//            startActivity(Intent(this, Medical_Dashboard::class.java))
//            finish()
//        } else if (email == "") {
//            setCustomError("Please Enter Email", editTextEmail!!)
//        } else if (pass == "") {
//            setCustomError("Please Enter Password", editTextPassword!!)
//        } else {
//            Toast.makeText(this, "Email Or Password Not Matched !!", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun sendUserLoginData(email: String, password: String) {

        val methods = RetrofitClient.retrofitInstance?.create(
            Methods::class.java
        )

        val call = methods?.loginUser(email, password)

        call?.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

                if(response.isSuccessful){
                    var strOutput = ""
                    strOutput = "message: " + response.body()!!.message
                    strOutput = """
                        $strOutput
                        token: ${response.body()!!.token}
                        """.trimIndent()
                    strOutput = """
                        $strOutput
                        Status: ${response.body()!!.status}
                        """.trimIndent()
                    Log.d("Response: ", strOutput)
                    Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_SHORT).show()

                    if (response.body()!!.status == "true") {

                        if (response.body()!!.user_type == "user") {
                            session!!.hasSession = true
                            session!!.token = response.body()!!.token
                            prefManager.isCustomerlogin=true
                            prefManager.isMedicallogin=false
                            val intent = Intent(this@LoginActivity, Customer_Dashboard::class.java)
                            startActivity(intent)
                            finish()
                        } else if (response.body()!!.user_type == "medical") {
                            session!!.hasSession = true
                            session!!.token = response.body()!!.token
                            prefManager.isMedicallogin=true
                            prefManager.isCustomerlogin=false
                            val intent = Intent(this@LoginActivity, Medical_Dashboard::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }
                }
                else{
                    Toast.makeText(this@LoginActivity, "Error Occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error Occurred", Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }
}