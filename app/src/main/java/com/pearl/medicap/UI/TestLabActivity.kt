package com.pearl.medicap.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.BaseClass
import org.w3c.dom.Text

class TestLabActivity : BaseClass(){
    lateinit var submit_btn:TextView
    lateinit var customer_name:EditText
    lateinit var customer_number:EditText
    lateinit var dob:EditText
    lateinit var appointment:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val isConnected = isNetworkConnected(this.applicationContext)
        setLayoutXml()
        changeStatusBarColor()
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("RegisterActivity", "onCreate", "initConnected")
            initializeViews()
            initializeClickListners()
            initializeLabels()
            initializeInputs()

            printLogs("RegisterActivity", "onCreate", "exitConnected")
        }
    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_test_lab)
    }

    override fun initializeViews() {
        submit_btn= findViewById(R.id.submitButton)
        customer_name=findViewById(R.id.customernameET)
        customer_number=findViewById(R.id.customer_numberET)
        dob=findViewById(R.id.dobET)
        appointment=findViewById(R.id.appointmentforET)


    }

    override fun initializeClickListners() {
        submit_btn.setOnClickListener {
            Toast.makeText(this,"your appointment is deliver to medical store please wait for response",Toast.LENGTH_SHORT).show()
            customer_name.text.clear()
            customer_number.text.clear()
            dob.text.clear()
            appointment.text.clear()
        }
    }

    override fun initializeInputs() {


    }

    override fun initializeLabels() {

    }

    override fun changeStatusBarColor() {
        window.statusBarColor=resources.getColor(R.color.App_color)
    }
}