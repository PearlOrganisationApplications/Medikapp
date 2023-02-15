package com.pearl.medicap.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pearl.medicap.R

class TestLabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lab)
        window.statusBarColor=resources.getColor(R.color.App_color)

    }
}