package com.pearl.medicap.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.pearl.medicap.R
import org.w3c.dom.Text

class TestLabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lab)
        window.statusBarColor=resources.getColor(R.color.App_color)
        var submit_btn=findViewById<TextView>(R.id.submitButton)
        submit_btn.setOnClickListener {
            Toast.makeText(this,"your appointment is deliver to medical store please wait for response",Toast.LENGTH_SHORT).show()
        }

    }
}