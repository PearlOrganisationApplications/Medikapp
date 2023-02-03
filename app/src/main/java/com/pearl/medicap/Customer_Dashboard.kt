package com.pearl.medicap

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pearl.medicap.Adapter.MedicineAdapter
import com.pearl.medicap.Adapter.MultipleImageAdapter
import com.pearl.medicap.databinding.ActivityCustomerDashboardBinding
import com.pearl.medicap.model.MultipleImage
import com.pearl.medicap.pearlLib.BaseClass


class Customer_Dashboard :  BaseClass() {
    lateinit var binding: ActivityCustomerDashboardBinding
    override var STORAGE_PERMISSION_CODE=1
    lateinit var multipleImageAdapter: MultipleImageAdapter

    var imagelist=ArrayList<MultipleImage>()
    var medicineList=ArrayList<String>()
    lateinit var input_medicine:EditText
    lateinit var more_button:ImageView
    lateinit var done_button:ImageView
    lateinit var submit_button:Button
    lateinit var drawer_button:ImageView
    lateinit var drawer: DrawerLayout
    lateinit var medicineAdapter: MedicineAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLayoutXml()
        val isConnected = isNetworkConnected(this.applicationContext)
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("Customer_Dashboard", "onCreate", "initConnected")
            initializeViews()
            initializeClickListners()
            initializeLabels()
            initializeInputs()
            changeStatusBarColor()
            printLogs("Customer_Dashboard", "onCreate", "exitConnected")
        }
    }

    override fun changeStatusBarColor() {
        window.statusBarColor= resources.getColor(R.color.App_color)
    }

    override fun setLayoutXml() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_dashboard)
    }

    override fun initializeViews() {
        input_medicine=findViewById(R.id.input_medicineET)
       // more_button=findViewById(R.id.next_btnTV)
        done_button=findViewById(R.id.done_btnTV)
        submit_button=findViewById(R.id.submit_bt)
        drawer_button=findViewById(R.id.sidebar)
        drawer=findViewById(R.id.drawerr)
        binding.medicinelist.layoutManager=LinearLayoutManager(this)
    }

    override fun initializeClickListners() {
      /*  drawer.addDrawerListener(action_bar_toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/

        binding.imageUploadLL.setOnClickListener {
            //Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
            requestPermission()
        }
        done_button.setOnClickListener{
            if (validateEmail(input_medicine)){
            var data=input_medicine.text.toString()
            medicineList.add(data)
            medicineAdapter= MedicineAdapter(this,medicineList)
            binding.medicinelist.adapter=medicineAdapter
            hideSoftKeyboard(this,it )
            input_medicine.clearFocus()
            input_medicine.text.clear()
         //   input_medicine.setHint("")
        }
        }
        submit_button.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.waiting_dialog_layout)
            var ok_button=dialog.findViewById<Button>(R.id.ok_btn)
            ok_button.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

            Toast.makeText(this,"Your medicine Details successfully submitted",Toast.LENGTH_SHORT).show()
        }

        drawer_button.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }
    }
    fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0)
    }

    override fun initializeInputs() {

    }

    override fun initializeLabels() {

    }

    override fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openFileExplorer()
            return;
        }
        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
    }
    override fun openFileExplorer() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), STORAGE_PERMISSION_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == STORAGE_PERMISSION_CODE) {


                if (resultCode == RESULT_OK) {
                    if (requestCode == STORAGE_PERMISSION_CODE) {
                        imagelist.clear();
                        if (data!!.getClipData() != null) {
                            var count = data.getClipData()!!.getItemCount()
                            for (i in 0..count-1 ) {
                              var  imageUri: Uri = data.getClipData()!!.getItemAt(i).getUri()
                                imagelist.add(MultipleImage(imageUri))

                            }
                            multipleImageAdapter= MultipleImageAdapter(this,imagelist)
                            binding.imagelist.layoutManager=GridLayoutManager(this,4)
                            binding.imagelist.adapter=multipleImageAdapter
                        }
                    }
                }
            }
        }
    }
}



