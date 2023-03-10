package com.pearl.medicap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.pearl.medicap.R
import com.pearl.medicap.api.Methods
import com.pearl.medicap.api.RetrofitClient
import com.pearl.medicap.model.ResponseModel
import com.pearl.medicap.pearlLib.BaseClass
import com.pearl.medicap.pearlLib.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : BaseClass() {

    lateinit var Emailid: EditText
    lateinit var Otp: EditText
    lateinit var Btn: Button
    lateinit var verify_otp_btn: Button
    lateinit var email_error: LinearLayout
    lateinit var otp_view: LinearLayout
    lateinit var otp_error: LinearLayout
    lateinit var timer: TextView

    var email: String = ""
    var otp: String = ""
    override fun setLayoutXml() {
        TODO("Not yet implemented")
    }

    override fun initializeViews() {
        TODO("Not yet implemented")
    }

    override fun initializeClickListners() {
        TODO("Not yet implemented")
    }

    override fun initializeInputs() {
        TODO("Not yet implemented")
    }

    override fun initializeLabels() {
        TODO("Not yet implemented")
    }

    override fun changeStatusBarColor() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        session = Session(this@ForgotPasswordActivity)

        Emailid = findViewById(R.id.forgot_pass_email)
        Otp = findViewById(R.id.forgot_pass_otp)
        Btn = findViewById(R.id.forgot_pass_btn)
        verify_otp_btn = findViewById(R.id.forgot_pass_verify_otp_btn)
        email_error = findViewById(R.id.email_error_holder)
        otp_error = findViewById(R.id.otp_error_holder)
        otp_view = findViewById(R.id.otp_holder)
        timer = findViewById(R.id.otp_timer)


        otp_view.visibility = View.GONE
        otp_error.visibility = View.GONE
        email_error.visibility = View.GONE

        Btn.setOnClickListener {
            email = Emailid.text.toString()
            if (email != null || email != "") {
                verifyEmail()
            } else {
                email_error.visibility = View.VISIBLE
            }
        }

        verify_otp_btn.setOnClickListener {
            verifyOtp()
        }

    }

    private fun verifyEmail() {
        val methods = RetrofitClient.retrofitInstance?.create(
            Methods::class.java
        )

        val call = methods?.forgotPass(session!!.token,email)

        call?.enqueue(object : Callback<ResponseModel>{
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if(response.isSuccessful){
                    var result = "user exist:: " + response.body()!!.message
                    if (response.body()!!.message == "no match found"){
                        email_error.visibility = View.VISIBLE
                        Emailid.text.clear()
                    }
                    else{
                        otp_view.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun verifyOtp(){
        otp = Otp.text.toString()

        val methods = RetrofitClient.retrofitInstance?.create(
            Methods::class.java
        )

        val call = methods?.verifyOtp(session!!.token,otp)

        call?.enqueue(object: Callback<ResponseModel>{
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful){
                    session!!.email = email
                    val intent = Intent(this@ForgotPasswordActivity, NewPasswordActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    otp_error.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@ForgotPasswordActivity,"Error Occurred", Toast.LENGTH_SHORT).show()
            }

        })
    }
}