package com.pearl.medicap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class Choose_User_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
        window.statusBarColor=resources.getColor(R.color.App_color)
        var customer_btn=findViewById<TextView>(R.id.customers_btn)
        var continue_button=findViewById<TextView>(R.id.continueeTV)
        var ll_medical=findViewById<LinearLayout>(R.id.ll_medical)
        var ll_customer=findViewById<LinearLayout>(R.id.ll_customer)
        var check_customer=findViewById<ImageView>(R.id.check_customer)
        var check_medical=findViewById<ImageView>(R.id.check_medical)
      /*  customer_btn.setOnClickListener {

        }*/

        ll_medical.setOnClickListener {
            check_medical.visibility= View.VISIBLE
            ll_medical.setBackgroundResource(R.drawable.background)
            startActivity(Intent(this,LoginActivity::class.java))
            //Toast.makeText(this,"Medical is in progress",Toast.LENGTH_SHORT).show()
        }
        ll_customer.setOnClickListener {
            check_customer.visibility= View.VISIBLE
            ll_customer.setBackgroundResource(R.drawable.background)
            startActivity(Intent(this,LoginActivity::class.java))
        }

     /*   continue_button.setOnClickListener {
            if (ll_customer.isSelected){
                Toast.makeText(this,"Medical is in progress",Toast.LENGTH_SHORT).show()
            }
            else if (ll_customer.isSelected){

            }
        }*/
    }
}