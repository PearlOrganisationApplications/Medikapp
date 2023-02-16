package com.pearl.medicap.UI


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.google.android.gms.identity.intents.Address
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.BaseClass
import com.pearl.medicap.pearlLib.PrefManager
import java.util.*


class RegisterActivity : BaseClass() {
    lateinit var choose_user_spinner:Spinner
    lateinit var medicalform:LinearLayout
    lateinit var ll_userform:LinearLayout
    lateinit var tvgetloc:LinearLayout
    lateinit var prefManager: PrefManager
    lateinit var et_location: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        setLayoutXml()
        prefManager.setToLatL("")
        val isConnected = isNetworkConnected(this.applicationContext)
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("RegisterActivity", "onCreate", "initConnected")
            initializeViews()
            initializeClickListners()
            initializeLabels()
            initializeInputs()
            changeStatusBarColor()
            printLogs("RegisterActivity", "onCreate", "exitConnected")
        }
    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_register)
    }

    override fun initializeViews() {
        choose_user_spinner=findViewById(R.id.choose_user_spinner)
        ll_userform=findViewById(R.id.ll_userform)
        medicalform=findViewById(R.id.medicalform)
        tvgetloc=findViewById(R.id.tvgetloc)
        et_location=findViewById(R.id.et_location)
    }
    override fun initializeClickListners() {

        choose_user_spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View, position: Int,id: Long ) {
                //first,  we have to retrieve the item position as a string
                // then, we can change string value into integer
                val item_position = position.toString()
                val positonInt = Integer.valueOf(item_position)

                if(positonInt.equals(1)){
                    medicalform.visibility = View.VISIBLE
                    ll_userform.visibility = View.GONE
                }else if(positonInt.equals(2)){
                    medicalform.visibility = View.GONE
                    ll_userform.visibility = View.VISIBLE
                }else{
                    medicalform.visibility = View.GONE
                    ll_userform.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        tvgetloc.setOnClickListener {
            startActivity(Intent(this, LocationPickerActivity::class.java))

        }

    }

    @SuppressLint("LongLogTag")
    private fun getCurrentLoc(){
       val to_lat = prefManager.getToLatL().toDouble()
       val to_lng = prefManager.getToLngL().toDouble()

        var geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())


        var strAdd : String? = null
        try {
            val addresses = geocoder.getFromLocation(to_lat!!, to_lng!!, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = java.lang.StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.w(" Current loction address", strReturnedAddress.toString())
            } else {
                Log.w(" Current loction address", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w(" Current loction address",  e.printStackTrace().toString())
        }
        et_location?.setText(strAdd)
    }


    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun initializeInputs() {
        var adapter=ArrayAdapter.createFromResource(this,R.array.user_type,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        choose_user_spinner.adapter=adapter
    }

    override fun initializeLabels() {}
    @SuppressLint("ObsoleteSdkInt")
    override fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            // Making notification bar transparent
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //            window.setStatusBarColor(Color.TRANSPARENT);
            window.statusBarColor = resources.getColor(R.color.App_color)
        }
    }


    override fun onResume() {
        super.onResume()
        if (prefManager.getToLatL().equals("")){

        }else{
            getCurrentLoc()
        }
    }


}