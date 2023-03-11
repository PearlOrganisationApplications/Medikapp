package com.pearl.medicap.UI

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.pearl.medicap.BuildConfig
import com.pearl.medicap.R
import com.pearl.medicap.pearlLib.BaseClass
import com.pearl.medicap.pearlLib.PrefManager
import com.pearl.medicap.pearlLib.Session
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : BaseClass() {

    lateinit var medicalform: LinearLayout
    lateinit var userform: LinearLayout
    lateinit var prefManager: PrefManager

    val TAKE_PIC_REQUEST_CODE = 1000
    val TAKE_CERTIFICATE_PIC_CODE = 2000
    val TAKE_SHOP_PIC_CODE = 3000
    val CHOOSE_PIC_REQUEST_CODE = 1001
    val CHOOSE_CERTIFICATE_PIC_CODE = 2001
    val CHOOSE_SHOP_PIC_CODE = 3001
    val MEDIA_TYPE_IMAGE = 1002
    private val PICK_FROM_GALLERY = 1003
    private var profileMediaUri: Uri? = null
    private var certificateMediaUri: Uri? = null
    private var shopMediaUri: Uri? = null
    private var dataIntent: Intent? = null

    override var session: Session? = null
    var profileName: String? = null

    var to_lat: Double = 0.0
    var to_lng: Double = 0.0
    var strAdd: String = ""

    //----------------normal user form components-----------------
    lateinit var userNameEt: EditText
    lateinit var userEmailEt: EditText
    lateinit var userMobileEt: EditText
    lateinit var userDobEt: EditText
    lateinit var userProfileImg: ImageView
    lateinit var changeUserPassBtn: TextView
    lateinit var updateUserProfile: TextView
    var userprofileImgPath: String? = null
    var userprofileImgExt: String? = null


    //----------------medical form components-----------------
    lateinit var ownerNameEt: EditText
    lateinit var ownerEmailEt: EditText
    lateinit var ownerMobileEt: EditText
    lateinit var ownerShopEt: EditText
    lateinit var ownerGstnoEt: EditText
    lateinit var ownerShopLocation: LinearLayout
    lateinit var ownerShopImgEt: TextView
    lateinit var ownerShopImgBtn: ImageView
    lateinit var ownerCertificateImg: TextView
    lateinit var ownerCertificateImgBtn: ImageView
    lateinit var ownerProfileImg: ImageView
    lateinit var ownerShopLicationEt: EditText
    lateinit var changeownerPassBtn: TextView
    lateinit var updateownerProfile: TextView
    var medicalprofileImgPath: String? = null
    var medicalprofileImgExt: String? = null
    var certificateImgPath: String? = null
    var certificateImgExt: String? = null
    var shopImgPath: String? = null
    var shopImgExt: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        prefManager = PrefManager(this)
        session = Session(this@EditProfileActivity)
        prefManager.setToLatL("")

        profileName = intent.getStringExtra("Usertype")
        userform = findViewById(R.id.userform)
        medicalform = findViewById(R.id.medicalform)

        if (profileName == "User") {
            userform.visibility = View.VISIBLE
            medicalform.visibility = View.GONE
        } else {
            userform.visibility = View.GONE
            medicalform.visibility = View.VISIBLE
        }


        //-------------------user profile--------------------
        userProfileImg = findViewById(R.id.EditProfileImage)
        userNameEt = findViewById(R.id.EditNameET)
        userMobileEt = findViewById(R.id.EditMobileET)
        userEmailEt = findViewById(R.id.EditemailET)
        userDobEt = findViewById(R.id.EditdobET)
        changeUserPassBtn = findViewById(R.id.EditPasswordBtn)
        updateUserProfile = findViewById(R.id.UpdateUserBtn)

        //-------------------medical profile-----------------
        ownerProfileImg = findViewById(R.id.EditOwnerProfileImage)
        ownerNameEt = findViewById(R.id.EditOwnerName)
        ownerMobileEt = findViewById(R.id.EditOwnerNumber)
        ownerEmailEt = findViewById(R.id.EditOwnerEmail)
        ownerShopEt = findViewById(R.id.EditMedicalName)
        changeownerPassBtn = findViewById(R.id.EditOwnerPassword)
        ownerGstnoEt = findViewById(R.id.EditGstNo)
        ownerCertificateImg = findViewById(R.id.editcertificateImgTV)
        ownerCertificateImgBtn = findViewById(R.id.editcertificateImgBtn)
        ownerShopImgEt = findViewById(R.id.editshopImgTV)
        ownerShopImgBtn = findViewById(R.id.editshopImgBtn)
        updateownerProfile = findViewById(R.id.UpdateOwnerBtn)
        ownerShopLocation = findViewById(R.id.edittvgetloc)
        ownerShopLicationEt = findViewById(R.id.EditMedicalLocation)




        userProfileImg.setOnClickListener {
            uploadProfileImage()
        }

        ownerProfileImg.setOnClickListener {
            uploadProfileImage()
        }

        updateUserProfile.setOnClickListener {
            updateUserProfileData()
        }

        ownerCertificateImgBtn.setOnClickListener {
            uploadCertificateImage()
        }

        ownerShopImgBtn.setOnClickListener {
            uploadShopImage()
        }

    }

    private fun updateUserProfileData() {
        var name = userNameEt.text.toString()
        var mobile = userMobileEt.text.toString()
        var email = userEmailEt.text.toString()
        var dob = userDobEt.text.toString()

        sendUserData()
    }

    private fun uploadProfileImage() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    this@EditProfileActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@EditProfileActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PICK_FROM_GALLERY
                )
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@EditProfileActivity)
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
                        profileMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
                        if (profileMediaUri == null) {
                            //display error
                            Toast.makeText(
                                applicationContext,
                                "Sorry there was an error! Try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, profileMediaUri)
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

    private fun uploadCertificateImage(){
        try {
            if (ActivityCompat.checkSelfPermission(
                    this@EditProfileActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@EditProfileActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PICK_FROM_GALLERY
                )
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@EditProfileActivity)
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
                            ), CHOOSE_CERTIFICATE_PIC_CODE
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
                            startActivityForResult(takePicture, TAKE_CERTIFICATE_PIC_CODE)
                        }
                    })
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun uploadShopImage(){
        try {
            if (ActivityCompat.checkSelfPermission(
                    this@EditProfileActivity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@EditProfileActivity,
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PICK_FROM_GALLERY
                )
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@EditProfileActivity)
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
                            ), CHOOSE_SHOP_PIC_CODE
                        )
                    })
                builder.setNegativeButton("Take Photo",
                    DialogInterface.OnClickListener { dialog, which ->
                        //take photo
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        shopMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
                        if (shopMediaUri == null) {
                            //display error
                            Toast.makeText(
                                applicationContext,
                                "Sorry there was an error! Try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, shopMediaUri)
                            startActivityForResult(takePicture, TAKE_SHOP_PIC_CODE)
                        }
                    })
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendUserData() {

    }

    override fun setLayoutXml() {
        TODO("Not yet implemented")
    }

    override fun initializeViews() {
        TODO("Not yet implemented")
    }

    override fun initializeClickListners() {
        TODO("Not yet implemented")
    }

    override fun initializeInputs() {
        TODO("Not yet implemented")
    }

    override fun initializeLabels() {
        TODO("Not yet implemented")
    }

    override fun changeStatusBarColor() {
        window.statusBarColor = resources.getColor(R.color.App_color)
    }

    private fun getOutputMediaFileUri(mediaTypeImage: Int): Uri? {
        return if (isExternalStorageAvailable()) {

            val mediaStorageDir = this@EditProfileActivity.getExternalFilesDir(null);


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
                this@EditProfileActivity,
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
                    profileMediaUri = data.data

                    val mimeType: String? = profileMediaUri?.let {
                        applicationContext?.getContentResolver()!!.getType(it).toString()
                    }


                    if (profileName == "User") {

                        userprofileImgExt =
                            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, profileMediaUri)

                        userprofileImgPath = BitMapToString(bitmap)

//                    certificateImg.setText(profileMediaUri.toString())

                        userProfileImg.setImageURI(profileMediaUri)
                    } else if (profileName == "Medical") {
                        medicalprofileImgExt =
                            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, profileMediaUri)

                        medicalprofileImgPath = BitMapToString(bitmap)

//                    certificateImg.setText(profileMediaUri.toString())

                        ownerProfileImg.setImageURI(profileMediaUri)


                    }

                }
            }

            else if(requestCode == TAKE_PIC_REQUEST_CODE){

                if (profileName == "User") {
                    userProfileImg.setImageURI(profileMediaUri)
                } else {
                    ownerProfileImg.setImageURI(profileMediaUri)
                }


            }

            //------------------for certificate image----------------
            if (requestCode == CHOOSE_CERTIFICATE_PIC_CODE) {
                if (data == null) {


                    Toast.makeText(applicationContext, "Image cannot be null!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    dataIntent = data
                    certificateMediaUri = data.data

                    val mimeType: String? = certificateMediaUri?.let {
                        applicationContext?.getContentResolver()!!.getType(it).toString()
                    }


                    certificateImgExt=
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, certificateMediaUri)

                    certificateImgPath = BitMapToString(bitmap)

                    ownerCertificateImg.setText(certificateMediaUri.toString())

                }
            }

            else if(requestCode == TAKE_CERTIFICATE_PIC_CODE){

                ownerCertificateImg.setText(certificateMediaUri.toString())


            }

            //------------------for shop image----------------
            if (requestCode == CHOOSE_SHOP_PIC_CODE) {
                if (data == null) {


                    Toast.makeText(applicationContext, "Image cannot be null!", Toast.LENGTH_LONG)
                        .show()
                } else {
                    dataIntent = data
                    shopMediaUri = data.data

                    val mimeType: String? = shopMediaUri?.let {
                        applicationContext?.getContentResolver()!!.getType(it).toString()
                    }


                    shopImgExt=
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, shopMediaUri)

                    shopImgPath = BitMapToString(bitmap)

                    ownerShopImgEt.setText(shopMediaUri.toString())

                }
            }

            else if(requestCode == TAKE_SHOP_PIC_CODE){

                ownerShopImgEt.setText(shopMediaUri.toString())


            }

        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_LONG).show()
        }
    }
}