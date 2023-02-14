package com.pearl.medicap.pearlLib

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.net.ConnectivityManager
import android.content.BroadcastReceiver
import android.content.Context
import androidx.annotation.RequiresApi
import android.os.Build
import android.content.Intent
import com.pearl.medicap.R
import android.content.IntentFilter
import android.widget.EditText
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.ByteArrayOutputStream
import java.lang.Exception

abstract class BaseClass : AppCompatActivity() {
    protected var activityIn: AppCompatActivity? = null
    public var LogTag: String? = null
    protected var CAId: String? = null
    protected var LogString: String? = null
    open var STORAGE_PERMISSION_CODE = 1
    open var session: Session? = null
    var classname = "Login"
    protected fun internetChangeBroadCast() {
        printLogs("Logs", "initializeViews", "init")
        //registerBroadcast()
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun printLogs(tag: String?, funcs: String, msg: String) {
        Log.i("OSG-" + tag + "__" + funcs, msg)
        LogString =
            LogString + "TAG - " + tag + "<br/> FUNCTION - " + funcs + "<br/> DATA - " + msg + "<br/><br/><br/><br/>"
    }

   /* var IChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        override fun onReceive(pContext: Context, pIntent: Intent) {
            val cm = pContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val no_connection = findViewById<View>(R.id.no_internet)
            // TextView try_again = findViewById(R.id.try_again);
            if (cm.activeNetwork != null) {
                no_connection.visibility = View.GONE
                printLogs(LogTag, "BroadcastReceiver", "func1$this")
            } else {
                no_connection.visibility = View.VISIBLE
            }
        }
    }*/

/*    fun registerBroadcast() {
        try {
            printLogs(LogTag, "registerBroadcast", "init")
            val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
            registerReceiver(IChangeReceiver, filter)
            isInternetReceiver = true
            printLogs(LogTag, "registerBroadcast", "exit")
        } catch (e: Exception) {
            printLogs(LogTag, "registerBroadcast", "Exception " + e.message)
        }
    }*/

   /* fun unregisterBroadcast() {
        printLogs(LogTag, "unregisterBroadcast", "init")
        try {
            if (isInternetReceiver) {
                printLogs(LogTag, "unregisterBroadcast", "isInternetReceiver")
                isInternetReceiver = false
                unregisterReceiver(IChangeReceiver)
            }
        } catch (e: Exception) {
            printLogs(LogTag, "unregisterBroadcast", "Exception " + e.message)
        }
    }*/

    open fun openFileExplorer() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Picture"
            ), STORAGE_PERMISSION_CODE
        )
    }

    protected fun setCustomError(msg: String?, mEditView: EditText) {
        mEditView.setError(msg, null)
        mEditView.setBackgroundResource(R.drawable.input_error_profile)
        mEditView.requestFocus()
    }

    protected fun setCustomErrorDisabled(mEditView: EditText) {
        mEditView.error = null
        mEditView.setBackgroundResource(R.drawable.input_boder_profile)
    }

    fun getExtension(uri: Uri?): String? {
        val mimeType = contentResolver.getType(uri!!)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    }

    open fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openFileExplorer()
            return
        }
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos)
        val b = baos.toByteArray()
        Base64.encodeToString(b, Base64.DEFAULT)
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun hideKeybaord(v: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }
    fun validateEmail(email: EditText): Boolean {
        val email_id: String = email.getText().toString().trim { it <= ' ' }
        setCustomError(null, email)
        return if (email_id.isEmpty()) {
            val sMessage = "Please enter medicine name..!!"
            setCustomError(sMessage, email)
            false
        } else {
            setCustomErrorDisabled(email)
            true
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    protected abstract fun setLayoutXml()
    protected abstract fun initializeViews()
    protected abstract fun initializeClickListners()
    protected abstract fun initializeInputs()
    protected abstract fun initializeLabels()
    protected abstract fun changeStatusBarColor()

    companion object {
        var isInternetReceiver = false
    }


}