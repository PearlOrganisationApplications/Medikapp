package com.pearl.medicap

import androidx.viewpager.widget.ViewPager
import android.widget.ProgressBar
import android.widget.TextView
import android.os.Bundle
import com.pearl.medicap.pearlLib.PrefManager
import android.content.Intent
import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.view.WindowManager
import androidx.core.content.ContextCompat
import android.widget.Toast
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import com.pearl.medicap.pearlLib.BaseClass
import java.util.*

class MainActivity : BaseClass() {
    private val toolbar: ActionBar? = null
    var viewPager: ViewPager? = null

    //   ArrayList<SliderObj> arrayList = new ArrayList<>();
    var currentPage = 0
    var timer: Timer? = null
    var DELAY_MS //delay in milliseconds before task is to be executed
            : Long = 0
    var PERIOD_MS // time in milliseconds between successive task executions.
            : Long = 0

    //   HomeSliderAdapter adapter;
    var progress: ProgressBar? = null
    var progress_tv: TextView? = null
    var total_amount: TextView? = null
    var used_amount: TextView? = null
    var remaining_amount: TextView? = null
    var tv_apply: TextView? = null
    var iv_menu: ImageView? = null
    var doubleBackToExitPressedOnce = false
    var total = 50000.0
    var used = 40000.0
    var remaning = 10000.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutXml()
        val isConnected = isNetworkConnected(this.applicationContext)
        if (isConnected) {
            //verifyVersion();
            internetChangeBroadCast()
            printLogs("MainActivity", "onCreate", "initConnected")
            initializeViews()
            initializeLabels()
            initializeClickListners()
            initializeInputs()
            changeStatusBarColor()
            printLogs("MainActivity", "onCreate", "exitConnected")
        }
        val prefManager = PrefManager(applicationContext)
        if (prefManager.isFirstTimeLaunch) {
            prefManager.isFirstTimeLaunch = false
            startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
            finish()
        }
    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_main)
    }

    override fun initializeViews() {
/*        viewPager = findViewById(R.id.viewpager2);
        progress = findViewById(R.id.progress);
        progress_tv = findViewById(R.id.progress_tv);
        total_amount = findViewById(R.id.total_amount);
        used_amount = findViewById(R.id.used_amount);
        remaining_amount = findViewById(R.id.remaining_amount);
        iv_menu = findViewById(R.id.iv_menu);
        tv_apply = findViewById(R.id.tv_apply);*/
    }

    override fun initializeClickListners() {
        /*   iv_menu.setOnClickListener(view -> {
                HomeFragment homeFragment = HomeFragment.newInstance();
                homeFragment.show(getSupportFragmentManager(), "Profile Drop Down");
        });
        tv_apply.setOnClickListener(view -> {
            startActivity(new Intent(this,ApplyLoanActivity.class));
        });*/
    }

    override fun initializeInputs() {}
    override fun initializeLabels() {
       /* total_amount!!.text = getString(R.string.currency) + total
        used_amount!!.text = getString(R.string.currency) + used
        remaining_amount!!.text = getString(R.string.currency) + remaning
        val used_amountpercent = used / total * 100
        progress!!.progress = used_amountpercent.toInt()*/

        /*  arrayList.add(new SliderObj(1,R.drawable.interest));
        arrayList.add(new SliderObj(2,R.drawable.approve));
        arrayList.add(new SliderObj(3,R.drawable.loan));
        adapter = new HomeSliderAdapter(arrayList,getApplicationContext());
        viewPager.setAdapter(adapter);
        automateViewPagerSwiping();*/
    }

    @SuppressLint("ObsoleteSdkInt")
    fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.themeColor)
        }
    }

    /* private void automateViewPagerSwiping() {
        DELAY_MS = 500;//delay in milliseconds before task is to be executed
        PERIOD_MS = 3000; // time in milliseconds between successive task executions.
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (viewPager.getCurrentItem() == adapter.getCount() - 1) { //adapter is your custom ViewPager's adapter
                    viewPager.setCurrentItem(0);
                }
                else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }*/
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // System.exit(0);
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
            finish()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "press back again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    companion object {
        private const val NUM_PAGES = 1
    }
}