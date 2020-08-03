package com.example.echo.activites

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.content.BroadcastReceiver
import android.content.Context.*
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.fragments.MainScreenFragment
import com.example.echo.R
import com.example.echo.adapters.NavigatonDrawerAdapter
import com.example.echo.fragments.SongPlaying
import java.lang.Exception

import androidx.recyclerview.widget.RecyclerView.LayoutManager as LayoutManager1
import androidx.recyclerview.widget.RecyclerView.LayoutManager as LayoutManager2

class MainActivity : AppCompatActivity() {


var navigationDrawerIconsList: ArrayList<String> = arrayListOf()
    var trackNotificationBuilder : Notification?= null
    var Images_for_navdrawer = intArrayOf(R.drawable.navigation_allsongs,R.drawable.navigation_favorites,
        R.drawable.navigation_settings,R.drawable.navigation_aboutus)
object Statified {
    var drawerLayout: DrawerLayout? = null
    var notificationManager :NotificationManager?= null
}
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        MainActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)

        navigationDrawerIconsList.add("All songs")
        navigationDrawerIconsList.add("Favorite")
        navigationDrawerIconsList.add("Settings")
        navigationDrawerIconsList.add("About Us")
        val mainScreenFragment = MainScreenFragment()
        this.supportFragmentManager
            .beginTransaction()
            .add(R.id.details_fragment,mainScreenFragment,"MainScreenFragment")
            .commit()

        var _navigationAdapter = NavigatonDrawerAdapter(navigationDrawerIconsList,Images_for_navdrawer, this)
        _navigationAdapter.notifyDataSetChanged()
        var navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = _navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)

        val intent = Intent(this@MainActivity,MainActivity::class.java)
        val pIntent =  PendingIntent.getActivity(this@MainActivity,System.currentTimeMillis().toInt(),
        intent,0)

        trackNotificationBuilder = Notification.Builder(this)
            .setContentTitle("A song is playing in the background")
            .setSmallIcon(R.drawable.echo_logo)
            .setOngoing(true)
            .setAutoCancel(true)
            .build()
        Statified.notificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStart() {
        super.onStart()
        try {
            Statified.notificationManager?.cancel(1968)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            if (SongPlaying.Statified.mediaPlayer?.isPlaying as Boolean){
                Statified.notificationManager?.notify(1968,trackNotificationBuilder)
            }
        }catch (e:Exception){


            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            Statified.notificationManager?.cancel(1968)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}




