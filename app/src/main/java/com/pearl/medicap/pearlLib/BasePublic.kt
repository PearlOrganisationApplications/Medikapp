package com.pearl.medicap.pearlLib

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.pearl.medicap.MainActivity

abstract class BasePublic : BaseClass() {
    fun CheckSession(baseApbcContext: Context?, activityIn: AppCompatActivity) {
        session = Session(baseApbcContext!!)
        val token = session!!.token
        var session_available = false
        if (session!!.hasSession!!) {
            val intent = Intent(baseApbcContext, MainActivity::class.java)
            startActivity(intent)
            activityIn.finish()
            /*  if (!token.isEmpty()){
                session_available = true ;
                printLogs("BasePublic ","CheckSession ","is_session_available - "+session_available);
            }else {
                session_available = false;
                printLogs("BasePublic ","CheckSession ","is_session_available - "+session_available);
            }*/
        } else {
            session_available = false
            printLogs("BasePublic ", "CheckSession ", "is_session_available - $session_available")
        }
        if (session_available) {
            val intent = Intent(baseApbcContext, MainActivity::class.java)
            startActivity(intent)
            activityIn.finish()
        }
    }
}