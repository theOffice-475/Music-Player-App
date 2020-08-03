package com.example.echo.activites

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.echo.R

class SplashActivity : AppCompatActivity() {
    var permissionString = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.MODIFY_AUDIO_SETTINGS,
    Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
    Manifest.permission.RECORD_AUDIO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if(!hasPermission(this@SplashActivity,*permissionString)){
            //we have to ask for permission
            ActivityCompat.requestPermissions(this@SplashActivity ,permissionString,131)
        }else{
            Handler().postDelayed({
            val startAct = Intent(  this@SplashActivity, MainActivity::class.java)//
              startActivity(startAct)
                this.finish()
            }, 1000)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            131 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED){
                    Handler().postDelayed({
                    val startAct = Intent(  this@SplashActivity, MainActivity::class.java)//
                    startActivity(startAct)
                    this.finish()
                }, 1000)
                }
                else{
                    Toast.makeText(this@SplashActivity, "please grant all permissions to continue",Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                return
            }
            else ->{
                Toast.makeText(this@SplashActivity,"oops something went wrong", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }
        } }
    fun hasPermission(context: Context,vararg permission: String ):Boolean{
        var hasAllPermission = true
        for(permissions in permission) {
            val res = context.checkCallingOrSelfPermission(permissions)

            if (res != PackageManager.PERMISSION_GRANTED) {
                hasAllPermission = false
            }
        }
        return hasAllPermission
    }

}
