package com.fnakhsan.mov.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.fnakhsan.mov.R
import com.fnakhsan.mov.dashboard.home.HomeFragment
import com.fnakhsan.mov.dashboard.setting.SettingFragment
import com.fnakhsan.mov.dashboard.ticket.TicketFragment
import com.fnakhsan.mov.databinding.ActivityDashboardBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.core.Tag

class DashboardActivity : AppCompatActivity() {
    private lateinit var dashboardBinding: ActivityDashboardBinding
    private lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)
        preferences = Preferences(this)
        preferences.setValues("status", "2")
        val url = preferences.getValues("url")
        Log.d(TAG, url.toString())
        setFragment(HomeFragment())

        with(dashboardBinding) {
            navHome.setOnClickListener {
                setFragment(HomeFragment())
                changeIcon(navHome, R.drawable.ic_home_active)
                changeIcon(navTicket, R.drawable.ic_tiket)
                changeIcon(navSetting, R.drawable.ic_profile)
            }
            navTicket.setOnClickListener {
                setFragment(TicketFragment())
                changeIcon(navHome, R.drawable.ic_home)
                changeIcon(navTicket, R.drawable.ic_tiket_active)
                changeIcon(navSetting, R.drawable.ic_profile)
            }
            navSetting.setOnClickListener {
                setFragment(SettingFragment())
                changeIcon(navHome, R.drawable.ic_home)
                changeIcon(navTicket, R.drawable.ic_tiket)
                changeIcon(navSetting, R.drawable.ic_profile_active)
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        Log.d(TAG, "first")
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        Log.d(TAG, "second")
        fragmentTransaction.replace(R.id.cv_fragment, fragment)
        Log.d(TAG, "third")
        fragmentTransaction.commit()
        Log.d(TAG, "4")
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    companion object {
        const val TAG = "DBA"
    }
}