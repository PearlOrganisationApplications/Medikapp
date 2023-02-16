package com.pearl.medicap.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.BaseClass
import com.pearl.medicap.pearlLib.PrefManager

class Choose_User_Activity : BaseClass() {

   lateinit var customer_btn:TextView
  lateinit  var continue_button:TextView
   lateinit var ll_medical:LinearLayout
  lateinit  var ll_customer:LinearLayout
   lateinit var check_customer:ImageView
   lateinit var check_medical:ImageView
   lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            Toast.makeText(this,"Please connnect with internet", Toast.LENGTH_SHORT).show()
        }

    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_choose_user)
    }

    override fun changeStatusBarColor() {
        window.statusBarColor=resources.getColor(R.color.App_color)
    }



    override fun initializeViews() {
        customer_btn =findViewById<TextView>(R.id.customers_btn)
         continue_button=findViewById<TextView>(R.id.continueeTV)
         ll_medical=findViewById<LinearLayout>(R.id.ll_medical)
         ll_customer=findViewById<LinearLayout>(R.id.ll_customer)
         check_customer=findViewById<ImageView>(R.id.check_customer)
         check_medical=findViewById<ImageView>(R.id.check_medical)
    }

    override fun initializeClickListners() {
        ll_medical.setOnClickListener {
            check_medical.visibility= View.VISIBLE
            prefManager.isCustomerlogin = false
            ll_medical.setBackgroundResource(R.drawable.background)
            startActivity(Intent(this, LoginActivity::class.java))
            //Toast.makeText(this,"Medical is in progress",Toast.LENGTH_SHORT).show()
        }
        ll_customer.setOnClickListener {
            prefManager.isMedicallogin = false
            check_customer.visibility= View.VISIBLE
            ll_customer.setBackgroundResource(R.drawable.background)
            startActivity(Intent(this, LoginActivity::class.java))
        }

        /*   continue_button.setOnClickListener {
               if (ll_customer.isSelected){
                   Toast.makeText(this,"Medical is in progress",Toast.LENGTH_SHORT).show()
               }
               else if (ll_customer.isSelected){

               }
           }*/
    }

    override fun initializeInputs() {
        prefManager=PrefManager(this)
    }

    override fun initializeLabels() {}


}