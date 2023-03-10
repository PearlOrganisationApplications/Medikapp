package com.pearl.medicap.UI


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.android.gms.identity.intents.Address
import com.pearl.medicap.BuildConfig
import com.pearl.medicap.R
import com.pearl.medicap.api.Methods
import com.pearl.medicap.api.RetrofitClient
import com.pearl.medicap.model.ResponseModel
import com.pearl.medicap.pearlLib.BaseClass
import com.pearl.medicap.pearlLib.PrefManager
import com.pearl.medicap.pearlLib.Session
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : BaseClass() {
    lateinit var choose_user_spinner: Spinner
    lateinit var medicalform: LinearLayout
    lateinit var ll_userform: LinearLayout
    lateinit var tvgetloc: LinearLayout
    lateinit var prefManager: PrefManager
    lateinit var et_location: EditText

    val TAKE_PIC_REQUEST_CODE = 1000
    val TAKE_PIC_REQUEST_CODE_SHOP = 2000
    val CHOOSE_PIC_REQUEST_CODE = 1001
    val CHOOSE_PIC_REQUEST_CODE_SHOP = 2001
    val MEDIA_TYPE_IMAGE = 1002
    val MEDIA_TYPE_IMAGE_SHOP = 2002
    private val PICK_FROM_GALLERY = 1003
    private val PICK_FROM_GALLERY_SHOP = 1003
    private var certificateMediaUri: Uri? = null
    private var shopMediaUri: Uri? = null
    private var dataIntent: Intent? = null

    override var session: Session? = null

    var to_lat: Double = 0.0
    var to_lng: Double = 0.0
    var strAdd: String = ""

    lateinit var item_position: String
    var positonInt: Int = 0

//--------------------------------------customer registeration field vairables--------------------------------------

    lateinit var userNameText: EditText
    lateinit var userMobileNoText: EditText
    lateinit var userEmailText: EditText
    lateinit var userDOBText: EditText
    lateinit var userPasswordText: EditText
    lateinit var registerUserBtn: TextView

//--------------------------------------medical registeration field vairables--------------------------------------

    lateinit var medNameText: EditText
    lateinit var medMobileNoText: EditText
    lateinit var medEmailText: EditText
    lateinit var medPasswordText: EditText
    lateinit var medPharmacyName: EditText
    lateinit var medGstNo: EditText
    lateinit var certificateImg: TextView
    lateinit var certificateImgBtn: ImageView
    var certificateImgPath: String? = null
    var certificateImgExt: String? = null
    var shopImgPath: String? = null
    var shopImgExt: String? = null
    lateinit var shopImg: TextView
    lateinit var shopImgBtn: ImageView
    lateinit var registerMedBtn: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        session = Session(this@RegisterActivity)
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
        choose_user_spinner = findViewById(R.id.choose_user_spinner)
        ll_userform = findViewById(R.id.ll_userform)
        medicalform = findViewById(R.id.medicalform)
        tvgetloc = findViewById(R.id.tvgetloc)
        et_location = findViewById(R.id.et_location)

//--------------------------------------Customer Registeration--------------------------------------


        userNameText = findViewById(R.id.NameET)
        userMobileNoText = findViewById(R.id.MobileET)
        userEmailText = findViewById(R.id.emailET)
        userDOBText = findViewById(R.id.dobET)
        userPasswordText = findViewById(R.id.passwordET)
        registerUserBtn = findViewById(R.id.cirRegisterButton)


//--------------------------------------Medical Registeration--------------------------------------

        medNameText = findViewById(R.id.et_Oname)
        medMobileNoText = findViewById(R.id.et_ownersNumber)
        medEmailText = findViewById(R.id.et_ownerEmail)
        medPharmacyName = findViewById(R.id.et_Mname)
        medPasswordText = findViewById(R.id.et_password)
        medGstNo = findViewById(R.id.et_gstin)
        certificateImg = findViewById(R.id.certificateImgTV)
        certificateImgBtn = findViewById(R.id.certificateImgBtn)
        shopImg = findViewById(R.id.shopImgTV)
        shopImgBtn = findViewById(R.id.shopImgBtn)
        registerMedBtn = findViewById(R.id.tvmedicalButton)


    }

    override fun initializeClickListners() {

        choose_user_spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                item_position = position.toString()
                positonInt = Integer.valueOf(item_position)

                if (positonInt.equals(1)) {
                    medicalform.visibility = View.VISIBLE
                    ll_userform.visibility = View.GONE
                } else if (positonInt.equals(2)) {
                    medicalform.visibility = View.GONE
                    ll_userform.visibility = View.VISIBLE
                } else {
                    medicalform.visibility = View.GONE
                    ll_userform.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        tvgetloc.setOnClickListener {
            startActivity(Intent(this, LocationPickerActivity::class.java))

        }

        registerMedBtn.setOnClickListener {
            registerMedicalUser()
        }

        registerUserBtn.setOnClickListener {
            registerCustomerUser()
        }

        shopImgBtn.setOnClickListener {
            uploadShopImage()
        }

        certificateImgBtn.setOnClickListener {
            uploadCertificateImage()
        }

    }


    //--------------------------------------registeration of medical--------------------------------------
    @SuppressLint("LongLogTag")
    private fun registerMedicalUser() {
        var name = medNameText.text.toString()
        var email = medEmailText.text.toString()
        var mobile = medMobileNoText.text.toString()
        var pharmacy_name = medPharmacyName.text.toString()
        var password = medPasswordText.text.toString()
        var gstno = medGstNo.text.toString()

        //--------------------------------------condition for basic details--------------------------------------
        if (name != null || name != "" &&
            email != null || email != "" &&
            mobile != null || mobile != "" &&
            pharmacy_name != null || pharmacy_name != "" &&
            password != null || password != "" &&
            gstno != null || gstno != ""
        ) {

            Log.d("Register Medical Step 1:: ", "Basic Details ok")
            //--------------------------------------condition for address details--------------------------------------
            if (to_lng != null || to_lng != 0.0 &&
                to_lat != null || to_lng != 0.0 &&
                strAdd != null || strAdd != ""
            ) {

                Log.d("Register Medical Step 2:: ", "Address Details ok")
                //--------------------------------------condition for image details--------------------------------------
                if (certificateImgPath != null || certificateImgPath != "" &&
                    certificateImgExt != null || certificateImgExt != "" &&
                    shopImgPath != null || shopImgPath != "" &&
                    shopImgExt != null || shopImgExt != ""
                ) {

                    Log.d("Register Medical Step 3:: ", "Images Details ok")

                    sendMedicalUserData(
                        name,
                        email,
                        mobile,
                        password,
                        gstno,
                        pharmacy_name,
                        certificateImgPath,
                        certificateImgExt,
                        shopImgPath,
                        shopImgExt,
                        to_lat.toString(),
                        to_lng.toString(),
                        strAdd
                    )

//                    val intent = Intent(this@RegisterActivity, Medical_Dashboard::class.java)
//                    startActivity(intent)
//                    finish()


//                    Toast.makeText(
//                        this@RegisterActivity,
//                        "Medical Profile Created",
//                        Toast.LENGTH_SHORT
//                    ).show()




                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "All Fields are Mandatory",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            } else {
                Toast.makeText(this@RegisterActivity, "All Fields are Mandatory", Toast.LENGTH_LONG)
                    .show()
            }

        } else {
            Toast.makeText(this@RegisterActivity, "All Fields are Mandatory", Toast.LENGTH_LONG)
                .show()
        }


    }


    //--------------------------------------registeration of customer--------------------------------------
    private fun registerCustomerUser() {
        var name = userNameText.text.toString()
        var email = userEmailText.text.toString()
        var password = userPasswordText.text.toString()
        var dob = userDOBText.text.toString()
        var mobile = userMobileNoText.text.toString()

        if(name!=null||name!="" && email!=null||email!="" && password!=null||password!="" && dob!=null||dob!="" && mobile!=null||mobile!=""){

            sendUserData(name,dob,email,mobile,password)

//            val intent = Intent(this@RegisterActivity, Customer_Dashboard::class.java)
//            startActivity(intent)
//            finish()


        }
    }

    //--------------------------------------uploading image of certificate--------------------------------------
    private fun uploadCertificateImage() {
//        Toast.makeText(this@RegisterActivity, "Upload Certifcate Image", Toast.LENGTH_LONG)
//            .show()
        try {
            if (ActivityCompat.checkSelfPermission(
                    this@RegisterActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@RegisterActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PICK_FROM_GALLERY
                )
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
                builder.setTitle("Upload or Take a photo")
                builder.setPositiveButton("Gallery",
                    DialogInterface.OnClickListener { dialog, which -> //upload image

                        val intent = Intent()
                        intent.setType("image/*")
                        intent.setAction(Intent.ACTION_GET_CONTENT)
                        startActivityForResult(
                            Intent.createChooser(
                                intent,
                                "Select Picture"
                            ), CHOOSE_PIC_REQUEST_CODE
                        )
                    })
                builder.setNegativeButton("Take Photo",
                    DialogInterface.OnClickListener { dialog, which ->
                        //take photo
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        certificateMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
                        if (certificateMediaUri == null) {
                            //display error
                            Toast.makeText(
                                applicationContext,
                                "Sorry there was an error! Try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, certificateMediaUri)
                            startActivityForResult(takePicture, TAKE_PIC_REQUEST_CODE)
                        }
                    })
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //--------------------------------------uploading image of shop--------------------------------------
    private fun uploadShopImage() {
        Toast.makeText(this@RegisterActivity, "Upload Shop Image", Toast.LENGTH_LONG)
            .show()

        try {
            if (ActivityCompat.checkSelfPermission(
                    this@RegisterActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@RegisterActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PICK_FROM_GALLERY_SHOP
                )
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
                builder.setTitle("Upload or Take a photo")
                builder.setPositiveButton("Gallery",
                    DialogInterface.OnClickListener { dialog, which -> //upload image

                        val intent = Intent()
                        intent.setType("image/*")
                        intent.setAction(Intent.ACTION_GET_CONTENT)
                        startActivityForResult(
                            Intent.createChooser(
                                intent,
                                "Select Picture"
                            ), CHOOSE_PIC_REQUEST_CODE_SHOP
                        )
                    })
                builder.setNegativeButton("Take Photo",
                    DialogInterface.OnClickListener { dialog, which ->
                        //take photo
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        shopMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE_SHOP)
                        if (shopMediaUri == null) {
                            //display error
                            Toast.makeText(
                                applicationContext,
                                "Sorry there was an error! Try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, shopMediaUri)
                            startActivityForResult(takePicture, TAKE_PIC_REQUEST_CODE_SHOP)
                        }
                    })
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //--------------------------------------Sending medical user data to api--------------------------------------
    private fun sendMedicalUserData(
        name: String,
        email: String,
        mobile: String,
        pass: String,
        gst_no: String,
        pharmacyname: String,
        certificateimg: String?,
        certificateimg_ext: String?,
        shopimg: String?,
        shopimg_ext: String?,
        lat: String,
        lng: String,
        address: String
    ) {

        val methods = RetrofitClient.retrofitInstance?.create(
            Methods::class.java
        )

        val call = methods?.registerMedicalUser(
            name,
            mobile,
            email,
            pass,
            pharmacyname,
            gst_no,
            certificateimg,
            certificateimg_ext,
            shopimg,
            shopimg_ext,
            lat,
            lng,
            address
        )
        call?.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {

                if (response.isSuccessful) {
                    var strOutput = ""
                    strOutput = "message: " + response.body()!!.message
                    strOutput = """
                        $strOutput
                        token: ${response.body()!!.token}
                        """.trimIndent()
                    strOutput = """
                        $strOutput
                        Status: ${response.body()!!.status}
                        """.trimIndent()
                    Log.d("Response: ", strOutput)
                    Toast.makeText(this@RegisterActivity, "Success", Toast.LENGTH_SHORT).show()

                    session!!.hasSession = true
                    session!!.token = response.body()!!.token
                    prefManager.isMedicallogin=true

                    val intent = Intent(this@RegisterActivity, Medical_Dashboard::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Error Occurred", Toast.LENGTH_SHORT)
                        .show()
                }


            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error Occurred", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }


    //--------------------------------------Sending customer user data to api--------------------------------------
    private fun sendUserData(
        name: String,
        dob: String,
        email: String,
        mobile: String,
        pass: String,

    ) {

        val methods = RetrofitClient.retrofitInstance?.create(
            Methods::class.java
        )

        val call = methods?.registerUser(name, dob, email, mobile, pass)

        call?.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if(response.isSuccessful){
                    var strOutput = ""
                    strOutput = "message: " + response.body()!!.message
                    strOutput = """
                        $strOutput
                        token: ${response.body()!!.token}
                        """.trimIndent()
                    strOutput = """
                        $strOutput
                        Status: ${response.body()!!.status}
                        """.trimIndent()
                    Log.d("Response: ", strOutput)
                    Toast.makeText(this@RegisterActivity, "Success", Toast.LENGTH_SHORT).show()

                    session!!.hasSession = true
                    session!!.token = response.body()!!.token
                    prefManager.isCustomerlogin=true

                    val intent = Intent(this@RegisterActivity, Customer_Dashboard::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Error Occurred", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error Occurred", Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }

    @SuppressLint("LongLogTag")
    private fun getCurrentLoc() {
        to_lat = prefManager.getToLatL().toDouble()
        to_lng = prefManager.getToLngL().toDouble()

        var geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())



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
            Log.w(" Current loction address", e.printStackTrace().toString())
        }
        et_location?.setText(strAdd)
    }


    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun initializeInputs() {
        var adapter = ArrayAdapter.createFromResource(
            this,
            R.array.user_type,
            R.layout.spinner_item_background
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_background)
        choose_user_spinner.adapter = adapter
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
            window.statusBarColor = resources.getColor(R.color.App_color)
        }
    }


    override fun onResume() {
        super.onResume()
        if (prefManager.getToLatL().equals("")) {

        } else {
            getCurrentLoc()
        }
    }


    private fun getOutputMediaFileUri(mediaTypeImage: Int): Uri? {
        return if (isExternalStorageAvailable()) {

            val mediaStorageDir = this@RegisterActivity.getExternalFilesDir(null);


            if (!mediaStorageDir!!.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                    return null;
                }
            }

            var mediaFile: File? = null
            val now = Date()
            val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now)
            val path: String = mediaStorageDir.getPath() + File.separator
            if (mediaTypeImage == MEDIA_TYPE_IMAGE) {
                mediaFile = File(path + "IMG_" + timestamp + ".jpg")
            }

            FileProvider.getUriForFile(
                this@RegisterActivity,
                BuildConfig.APPLICATION_ID + ".provider",
                mediaFile!!
            )

        } else {
            null
        }
    }

    private fun isExternalStorageAvailable(): Boolean {
        val state = Environment.getExternalStorageState()
        return if (state == Environment.MEDIA_MOUNTED) {
            true
        } else {
            false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE_PIC_REQUEST_CODE) {
                if (data == null) {


                    Toast.makeText(applicationContext, "Image cannot be null!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    dataIntent = data
                    certificateMediaUri = data.data

                    val mimeType: String? = certificateMediaUri?.let {
                        applicationContext?.getContentResolver()!!.getType(it).toString()
                    }
                    certificateImgExt =
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, certificateMediaUri)

                    certificateImgPath = BitMapToString(bitmap)

                    certificateImg.setText(certificateMediaUri.toString())


                }
            }
            if (requestCode == CHOOSE_PIC_REQUEST_CODE_SHOP) {
                if (data == null) {


                    Toast.makeText(applicationContext, "Image cannot be null!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    dataIntent = data
                    shopMediaUri = data.data

                    val mimeType: String? = shopMediaUri?.let {
                        applicationContext?.getContentResolver()!!.getType(it).toString()
                    }
                    shopImgExt =
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, shopMediaUri)

                    shopImgPath = BitMapToString(bitmap)

                    shopImg.setText(shopMediaUri.toString())

                }
            }

//            note:- add condition handling for take picture

            else {
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            }
        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_LONG).show()
        }
    }


}