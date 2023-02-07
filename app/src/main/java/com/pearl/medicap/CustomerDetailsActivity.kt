package com.pearl.medicap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pearl.medicap.Adapter.MedicineDetailsAdapter
import com.pearl.medicap.databinding.ActivityCustomerDetailsBinding
import com.pearl.medicap.model.MedicineDetails
import com.pearl.medicap.pearlLib.BaseClass

class CustomerDetailsActivity : BaseClass() {
    lateinit var binding: ActivityCustomerDetailsBinding
    lateinit var medicineDetailsAdapter: MedicineDetailsAdapter
    var medicine_list=ArrayList<MedicineDetails>()


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
    }
    override fun setLayoutXml() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_customer_details)
    }

    override fun initializeViews() {
       binding.medicineList.layoutManager=LinearLayoutManager(this)
    }

    override fun initializeClickListners() {
        binding.rejectBtn.setOnClickListener {
            finish()
        }
        binding.acceptBtn.setOnClickListener {
            startActivity(Intent(this,PrescriptionActivity::class.java))
        }

    }

    override fun initializeInputs() {
       medicine_list.add(MedicineDetails("Paracetamol","1 pill","50mg","1 capsule",R.drawable.pill1))
       medicine_list.add(MedicineDetails("aspirin","5 pill","100mg","10 capsule",R.drawable.pill2))
       medicine_list.add(MedicineDetails("Paracetamol","2 pill","80mg","1 capsule",R.drawable.pill3))
       medicine_list.add(MedicineDetails("aspirin","6 pill","450mg","6 capsule",R.drawable.pill4))
       medicine_list.add(MedicineDetails("Paracetamol","10 pill","250mg","2 capsule",R.drawable.pill5))
       medicine_list.add(MedicineDetails("aspirin","3 pill","150mg","1 capsule",R.drawable.pill1))

        medicineDetailsAdapter= MedicineDetailsAdapter(this,medicine_list)
        binding.medicineList.adapter=medicineDetailsAdapter
    }

    override fun initializeLabels() {

    }

    override fun changeStatusBarColor() {
        window.statusBarColor=resources.getColor(R.color.App_color)
    }
}