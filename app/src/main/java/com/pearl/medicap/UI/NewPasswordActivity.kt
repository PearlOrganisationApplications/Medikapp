package com.pearl.medicap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

class NewPasswordActivity : BaseClass() {

    lateinit var emailText : TextView
    lateinit var newPass:TextView
    lateinit var re_newPass:TextView
    lateinit var changePassBtn: Button

    lateinit var pass : String
    lateinit var confPass:String
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)
        session = Session(this@NewPasswordActivity)
        token = session!!.token.toString()

        emailText = findViewById(R.id.reset_pass_email)
        newPass = findViewById(R.id.new_pass)
        re_newPass = findViewById(R.id.re_new_pass)
        changePassBtn = findViewById(R.id.reset_pass_btn)

        emailText.text = session!!.email

        changePassBtn.setOnClickListener {
            setNewPass()
        }


    }

    private fun setNewPass(){
        pass = newPass.text.toString()
        confPass = re_newPass.text.toString()

        if(pass!=null||pass!="" && confPass!=null||confPass!=""){
            if(pass === confPass){
                updatePassword()
            }
        }
    }

    private fun updatePassword(){
        val methods = RetrofitClient.retrofitInstance?.create(
            Methods::class.java
        )

        val call = methods?.changePassword(token, pass)

        call?.enqueue(object: Callback<ResponseModel>{
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if(response.isSuccessful){

                    Toast.makeText(this@NewPasswordActivity,"Password Updated Successfully!!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@NewPasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@NewPasswordActivity,"Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@NewPasswordActivity,"Error Occurred", Toast.LENGTH_SHORT).show()
            }

        })
    }


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


}