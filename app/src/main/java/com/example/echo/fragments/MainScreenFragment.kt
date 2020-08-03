package com.example.echo.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.activites.MainActivity
import com.example.echo.adapters.MainScreenAdapter
import com.example.echo.songs

/**
 * A simple [Fragment] subclass.
 */
class MainScreenFragment : Fragment() {
    var getSongsList: ArrayList<songs>?= null
    var nowPlayingBottomBar: RelativeLayout?= null
    var playPauseButton: ImageButton?= null
    var songTitle: TextView?= null
    var visibleLayout: RelativeLayout?= null
    var noSongs: RelativeLayout?= null
    var myActivity: Activity?= null
    var recyclerView: RecyclerView?= null
    var _mainScreenAdapter: MainScreenAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(R.layout.fragment_main_screen,container,false)
        activity?.title = "All Songs"
        visibleLayout = view?.findViewById<RelativeLayout>(R.id.visibleLayout)
        noSongs = view?.findViewById<RelativeLayout>(R.id.noSongs)
        nowPlayingBottomBar = view?.findViewById<RelativeLayout>(R.id.hiddenBarMainScreen)
        playPauseButton = view?.findViewById<ImageButton>(R.id.playPauseButton)
        songTitle = view?.findViewById<TextView>(R.id.songTitleMainScreen)
        recyclerView = view?.findViewById<RecyclerView>(R.id.contentMain)
        return view

        // Inflate the layout for this fragment

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getSongsList = getSongsfromPhone()
        _mainScreenAdapter = MainScreenAdapter(getSongsList as ArrayList<songs>, myActivity as Context)
        val mLayoutManager = LinearLayoutManager(myActivity)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = _mainScreenAdapter
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myActivity = activity
    }
     fun  getSongsfromPhone() : ArrayList<songs>{
         var arrayList = ArrayList<songs>()
         var contentResolver = myActivity?.contentResolver
         var songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
         var songCursor = contentResolver?.query(songUri,null,null,null,null)
         if (songCursor!=null && songCursor.moveToFirst()){
             val songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID)
             val songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
             val songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
             val songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
             val dateIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
             while(songCursor.moveToNext())
             {
                 var currentId = songCursor.getLong(songId)
                 var currentTitle = songCursor.getString(songTitle)
                 var currentArtist = songCursor.getString(songArtist)
                 var currentData = songCursor.getString(songData)
                 var currentDate = songCursor.getLong(dateIndex)
                 arrayList.add(songs(currentId,currentTitle,currentArtist,currentData,currentDate))


             }

         }
return arrayList
     }

}
