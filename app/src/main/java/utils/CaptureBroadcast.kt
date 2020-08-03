package utils

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.example.echo.R
import com.example.echo.activites.MainActivity
import com.example.echo.fragments.SongPlaying
import java.lang.Exception

class CaptureBroadcast: BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == Intent.ACTION_NEW_OUTGOING_CALL){
            try {
                MainActivity.Statified.notificationManager?.cancel(1968)

            }catch (e:Exception){
                e.printStackTrace()
            }
            try {
                if (SongPlaying.Statified.mediaPlayer?.isPlaying as Boolean) {
                    SongPlaying.Statified.mediaPlayer?.pause()
                    SongPlaying.Statified.playPauseImageButton?.setBackgroundResource(R.drawable.play_icon)

                }
        }catch (e:Exception){
            e.printStackTrace()
            }
        }else{
            val tm: TelephonyManager = p0?.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
            when(tm?.callState){
                TelephonyManager.CALL_STATE_RINGING ->{
                    try {
                        MainActivity.Statified.notificationManager?.cancel(1968)

                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    try {
                        if (SongPlaying.Statified.mediaPlayer?.isPlaying as Boolean) {
                            SongPlaying.Statified.mediaPlayer?.pause()
                            SongPlaying.Statified.playPauseImageButton?.setBackgroundResource(R.drawable.play_icon)

                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                }else ->{

            }
            }
        }
    }

}

private fun Notification?.cancel(i: Int) {

}
