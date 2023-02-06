package com.pearl.medicap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.medicap.Adapter.CustomerDetailsAdapter
import com.pearl.medicap.model.CustomerMedicine
import com.pearl.medicap.pearlLib.BaseClass

class Medical_Dashboard : BaseClass() {
    lateinit var drawer_button: ImageView
    lateinit var drawer: DrawerLayout
    lateinit var customerDetailsAdapter: CustomerDetailsAdapter
    lateinit var customerDetailsRecylerview:RecyclerView
    var customer_list=ArrayList<CustomerMedicine>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
        customerDetailsRecylerview=findViewById(R.id.customer_medicine_details)
    }

    override fun initializeClickListners() {
        drawer_button.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
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