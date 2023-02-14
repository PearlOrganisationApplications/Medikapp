package com.pearl.medicap.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.BaseClass
import com.razorpay.Checkout
import org.json.JSONException
import org.json.JSONObject

class CustomerBillDetailsActvity : BaseClass() {
    lateinit var payNow:TextView
    lateinit var payLater:TextView
    lateinit var back_btn:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_bill_details_actvity)
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
    }

    override fun setLayoutXml() {

    }

    override fun initializeViews() {
        payNow=findViewById(R.id.payNow_btn)
        payLater=findViewById(R.id.payLater_btn)
        back_btn=findViewById(R.id.back_btn)

    }

    override fun initializeClickListners() {
        payNow.setOnClickListener {
            var amt=200
            val amount=Math.round(amt.toFloat()*100).toInt()
            var checkout=Checkout()
            checkout.setKeyID("rzp_test_capDM1KlnUhj5f")
            checkout.setImage(R.mipmap.appicon)
            val obj = JSONObject()
            try {
                obj.put("name", "Medikapp")
                obj.put("description", "Payment")
                obj.put("theme.color", "#4BAD32")
                obj.put("send_sms_hash", true)
                obj.put("allow_rotation", true)
                obj.put("currency", "INR")
                obj.put("amount", amount)
                val preFill = JSONObject()
                preFill.put("email", "support@gmail.com")
                preFill.put("contact", "91" + "9876565455")
                obj.put("prefill", preFill)
                checkout.open(this, obj)
            } catch (e: JSONException) {
                Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_SHORT).show();
                e.printStackTrace()
            }
        }
        payLater.setOnClickListener {
            finish()
            startActivity(Intent(this,Customer_Dashboard::class.java))
        }
        back_btn.setOnClickListener {
            startActivity(Intent(this,Customer_Dashboard::class.java))
        }
    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }

    override fun changeStatusBarColor() {
       window.statusBarColor=resources.getColor(R.color.App_color)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun onPaymentSuccess(s: String?) {
        Toast.makeText(this, "payment successful", Toast.LENGTH_SHORT).show()


    }

     fun onPaymentError(i: Int, s: String?) {
        Toast.makeText(this, "Payment failed$i", Toast.LENGTH_SHORT).show()

    }

}