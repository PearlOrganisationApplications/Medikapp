package com.pearl.medicap.UI

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.pearl.medicap.Adapter.CustomerDetailsAdapter
import com.pearl.medicap.R
import com.pearl.medicap.model.CustomerMedicine
import com.pearl.medicap.pearlLib.BaseClass
import com.pearl.medicap.pearlLib.PrefManager
import com.pearl.medicap.pearlLib.Session

class Medical_Dashboard : BaseClass() {
    lateinit var drawer_button: ImageView
    lateinit var drawer: DrawerLayout
    lateinit var customerDetailsAdapter: CustomerDetailsAdapter
    lateinit var customerDetailsRecylerview:RecyclerView
    lateinit var draw_layout:NavigationView
    var customer_list=ArrayList<CustomerMedicine>()
    lateinit var prefManager:PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager= PrefManager(this)
        session = Session(this@Medical_Dashboard)
        setLayoutXml()
        changeStatusBarColor()
        val isConnected = isNetworkConnected(this.applicationContext)
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("Customer_Dashboard", "onCreate", "initConnected")
            initializeViews()
            initializeClickListners()
            initializeLabels()
            initializeInputs()
            printLogs("Customer_Dashboard", "onCreate", "exitConnected")
        }
        else{
            Toast.makeText(this,"Please connnect with internet", Toast.LENGTH_SHORT).show()
        }

    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_medical_dashboard)
    }
    override fun changeStatusBarColor() {
        window.statusBarColor=resources.getColor(R.color.App_color)
    }

    override fun initializeViews() {
        drawer_button=findViewById(R.id.sidebar)
        drawer=findViewById(R.id.drawer_layout)
        draw_layout=findViewById(R.id.draw_layout)
        customerDetailsRecylerview=findViewById(R.id.customer_medicine_details)
    }

    override fun initializeClickListners() {
        drawer_button.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }
        draw_layout.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            val alertDialog2 = AlertDialog.Builder(this)
            alertDialog2.setTitle("Alert...")
            alertDialog2.setMessage("Are you sure you want to exit ?")
            alertDialog2.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
                session!!.clearSession()
                prefManager!!.isMedicallogin=false
                session!!.hasSession=false
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            alertDialog2.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            alertDialog2.show()
            //  finish();
            true

        }

        draw_layout.menu.findItem(R.id.profile).setOnMenuItemClickListener {
            startActivity(Intent(this,MedicalProfileActivity::class.java))
            true
        }

        draw_layout.menu.findItem(R.id.Support).setOnMenuItemClickListener {
            startActivity(Intent(this,Support_Activity::class.java))
            true
        }

        draw_layout.menu.findItem(R.id.term_condition).setOnMenuItemClickListener {
            startActivity(Intent(this,Terms_Condition_Activity::class.java))
            true
        }

        draw_layout.menu.findItem(R.id.About).setOnMenuItemClickListener {
            startActivity(Intent(this,About_Activity::class.java))
            true
        }
    }

    override fun initializeInputs() {
        customerDetailsRecylerview.layoutManager=LinearLayoutManager(this)
        customer_list.add(CustomerMedicine(R.drawable.pill1,"Sagar Bisht","Acetaminophen,Cymbalta,jdjf...","20 pills"))
        customer_list.add(CustomerMedicine(R.drawable.pill2,"Vivek Yadav","Cyclobenzaprine,Cymbalta,jdjf...","120 pills"))
        customer_list.add(CustomerMedicine(R.drawable.pill3,"Anurag Chaudhary","Cymbalta,Cymbalta,jdjf...","20 pills"))
        customer_list.add(CustomerMedicine(R.drawable.pill4,"Jagparvesh","Cyclobenzaprine,Cymbalta,jdjf...","50 pills"))
        customer_list.add(CustomerMedicine(R.drawable.pill5,"Vikrant","Cyclobenzaprine,Cymbalta,jdjf...","90 pills"))
        customer_list.add(CustomerMedicine(R.drawable.pill1,"Neeraj","Cyclobenzaprine,Cymbalta,jdjf...","100 pills"))
        customer_list.add(CustomerMedicine(R.drawable.pill1,"Yashsvi","Cyclobenzaprine,Cymbalta,jdjf...","20 pills"))
        customer_list.add(CustomerMedicine(R.drawable.pill1,"Sagar Bisht","Cyclobenzaprine,Cymbalta,jdjf...","20 pills"))
        customerDetailsAdapter= CustomerDetailsAdapter(this,customer_list)
        customerDetailsRecylerview.adapter=customerDetailsAdapter
    }

    override fun initializeLabels() {

    }
}