package com.pearl.medicap.UI

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pearl.medicap.Adapter.*
import com.pearl.medicap.R
import com.pearl.medicap.databinding.ActivityCustomerDashboardBinding
import com.pearl.medicap.model.ComingSoon
import com.pearl.medicap.model.MultipleImage
import com.pearl.medicap.pearlLib.BaseClass
import com.pearl.medicap.pearlLib.PrefManager
import me.relex.circleindicator.CircleIndicator
import java.io.File
import java.util.*


class Customer_Dashboard :  BaseClass() {
    lateinit var binding: ActivityCustomerDashboardBinding
    override var STORAGE_PERMISSION_CODE=1
    lateinit var multipleImageAdapter: MultipleImageAdapter


    var imagelist=ArrayList<MultipleImage>()
    var medicineList=ArrayList<String>()
    var banner_lisst=ArrayList<Int>()
    lateinit var input_medicine:EditText
    lateinit var more_button:ImageView
    lateinit var done_button:ImageView
    lateinit var submit_button:Button
    lateinit var drawer_button:ImageView
    lateinit var drawer: DrawerLayout
    lateinit var medicineAdapter: MedicineAdapter
    lateinit var prefManager: PrefManager
    lateinit var bannerAdapter: BannerAdapter
    private var timer: CountDownTimer?=null
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator
    lateinit var comingSoonAdapter: ComingSoonAdapter
    var comingsoonList=ArrayList<ComingSoon>()

    var currentPage = 0
    lateinit var timerr:Timer
    val DELAY_MS: Long = 500

    val PERIOD_MS: Long = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager= PrefManager(this)
      // prefManager.isCustomerlogin=false
        //prefManager.setCustomerlogin(false)



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
            Toast.makeText(this,"Please connnect with internet",Toast.LENGTH_SHORT).show()
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
                startActivity(Intent(this,CustomerBillDetailsActvity::class.java))
                dialog.dismiss()
            }
            dialog.show()

            Toast.makeText(this,"Your medicine Details successfully submitted",Toast.LENGTH_SHORT).show()
        }

        drawer_button.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }
        binding.navigationView.menu.findItem(R.id.customer_logout).setOnMenuItemClickListener {

            //drawer.closeDrawer(GravityCompat.END)
            val alertDialog2 = AlertDialog.Builder(this)
            alertDialog2.setTitle("Alert...")
            alertDialog2.setMessage("Are you sure you want to exit ?")
            alertDialog2.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this,"Logout Successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            alertDialog2.setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            alertDialog2.show()
               //  finish();
            true

        }
        binding.navigationView.menu.findItem(R.id.lab_test).setOnMenuItemClickListener {
            Toast.makeText(this,"lab test",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,TestLabActivity::class.java))
            true
        }

    }
    fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0)
    }

    override fun initializeInputs() {
        prefManager=PrefManager(this)
        binding.bannerList.layoutManager=LinearLayoutManager(this@Customer_Dashboard,LinearLayoutManager.HORIZONTAL,false)
                banner_lisst.add(R.drawable.banner1)
                banner_lisst.add(R.drawable.banner2)
                banner_lisst.add(R.drawable.banner3)
                banner_lisst.add(R.drawable.banner4)
                banner_lisst.add(R.drawable.banner1)

        var bannerAdapter=BannerAdapter(this@Customer_Dashboard,banner_lisst)
        binding.bannerList.adapter=bannerAdapter

        viewPagerAdapter = ImageSlideAdapter(this, banner_lisst)
        binding.viewpager.adapter = viewPagerAdapter
        indicator = findViewById(R.id.indicator) as CircleIndicator
        indicator.setViewPager(binding.viewpager)


        val handler = Handler()
        val Update = Runnable {
            if (currentPage === banner_lisst.size - 1) {
                currentPage = 0
            }

            binding.viewpager.setCurrentItem(currentPage++, true)
        }

      // This will create a new Thread

        timerr=Timer()
        timerr.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)


        binding.commingSoonList.layoutManager=GridLayoutManager(this,2)
        comingsoonList.add(ComingSoon(R.drawable.ultrasound,"Coming Soon"))
        comingsoonList.add(ComingSoon(R.drawable.xray,"Coming Soon"))
        comingsoonList.add(ComingSoon(R.drawable.ctscan,"Coming Soon"))
        comingsoonList.add(ComingSoon(R.drawable.ultrasound,"Coming Soon"))
        comingSoonAdapter=ComingSoonAdapter(this,comingsoonList)
        binding.commingSoonList.adapter=comingSoonAdapter

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

    override fun onDestroy() {
        super.onDestroy()
        clearApplicationData()

    }

    fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory()) {
            val children: Array<String> = dir.list()
            var i = 0
            while (i < children.size) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
                i++
            }
        }
        assert(dir != null)
        return dir!!.delete()
    }
    fun clearApplicationData() {
        val cache = cacheDir
        val appDir = File(cache.parent)
        if (appDir.exists()) {
            val children = appDir.list()
            for (s in children) {
                if (s != "lib") {
                    deleteDir(File(appDir, s))
                }
            }
        }
    }
}



