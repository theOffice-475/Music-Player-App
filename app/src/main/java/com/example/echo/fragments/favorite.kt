package com.example.echo.fragments


import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.adapters.favoriteAdapter
import com.example.echo.fragments.SongPlaying.Statified.favoriteContent
import com.example.echo.fragments.SongPlaying.Statified.fetchSongs
import com.example.echo.songs
import database.EchoDatabase

/**
 * A simple [Fragment] subclass.
 */
class favorite : Fragment() {
    var myActivity: Activity?= null


    var nowPlayingBottomBar:RelativeLayout?= null
    var noFavorites:TextView?=null
    var playPauseButton:ImageButton?= null
    var songTitle: TextView?=null
    var recyclerView: RecyclerView?=null
    var trackPosition:Int = 0
    var favoriteContent : EchoDatabase?= null

    var refreshList: ArrayList<songs>?= null
    var getListfromDatabase: ArrayList<songs>?=null



    object Statified{
        var mediaPlayer: MediaPlayer? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_favorite,container,false)
        activity?.title ="Favorites"
        favoriteContent = EchoDatabase(myActivity)
        noFavorites = view?.findViewById(R.id.noFavorites)
        nowPlayingBottomBar = view?.findViewById(R.id.hiddenBarFavScreen)
        playPauseButton = view?.findViewById(R.id.playPauseButton)
        songTitle = view?.findViewById(R.id.songTitle)
        recyclerView =view?.findViewById(R.id.favoriteRecycler)
        // Inflate the layout for this fragment
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        myActivity = activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        display_favorites_by_searching()
        bottomBarSetup()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu?.findItem(R.id.action_sort_recent)
        item?.isVisible = false
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

    fun bottomBarSetup(){
        try {
            bottomBarClickHandler()
            songTitle?.setText(SongPlaying.Statified.currentSongHelper?.songTitle)
            SongPlaying.Statified.mediaPlayer?.setOnCompletionListener({
                songTitle?.setText(SongPlaying.Statified.currentSongHelper?.songTitle)
                SongPlaying.Staticated.onSongComplete()
            })
            if (SongPlaying.Statified.mediaPlayer?.isPlaying as Boolean){
                nowPlayingBottomBar?.visibility = View.VISIBLE
            }else{
                nowPlayingBottomBar?.visibility = View.INVISIBLE
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
    fun bottomBarClickHandler(){

        nowPlayingBottomBar?.setOnClickListener ({
            Statified.mediaPlayer = SongPlaying.Statified.mediaPlayer
            val songPlayingFragment = SongPlaying()
            var args = Bundle()
            args.putString("songArtist", SongPlaying.Statified.currentSongHelper?.songArtist)
            args.putString("path", SongPlaying.Statified.currentSongHelper?.songPath)
            args.putString("songTitle", SongPlaying.Statified.currentSongHelper?.songTitle)
            args.putInt("songId", SongPlaying.Statified.currentSongHelper?.songId?.toInt() as Int)
            args.putInt("songposition", SongPlaying.Statified.currentSongHelper?.currentPosition?.toInt() as Int)
            args.putParcelableArrayList("songData", SongPlaying.Statified.fetchSongs)
            args.putString("FavBottomBar","Success")
            songPlayingFragment.arguments = args


            fragmentManager!!.beginTransaction()
                .replace(R.id.details_fragment,songPlayingFragment)
                .addToBackStack("SongPlayingFragment")
                .commit()
        })

        playPauseButton?.setOnClickListener({
            if (SongPlaying.Statified.mediaPlayer?.isPlaying as Boolean){
                SongPlaying.Statified.mediaPlayer?.pause()
                trackPosition = SongPlaying.Statified.mediaPlayer?.getCurrentPosition() as Int
                playPauseButton?.setBackgroundResource(R.drawable.play_icon)
            }else{
                SongPlaying.Statified.mediaPlayer?.seekTo(trackPosition)
                SongPlaying.Statified.mediaPlayer?.start()
                playPauseButton?.setBackgroundResource(R.drawable.pause_icon)
            }
        })
    }
    fun display_favorites_by_searching(){
        if (favoriteContent?.checkSize() as Int >0) {
            refreshList = ArrayList<songs>()
            getListfromDatabase = favoriteContent?.queryDBList()
            val fetchListfromDevice = getSongsfromPhone()
            if (fetchListfromDevice != null) {
                for (i in 0..fetchListfromDevice?.size - 1) {
                    for (j in 0..getListfromDatabase?.size as Int - 1) {
                        if (getListfromDatabase?.get(j)?.songID === fetchListfromDevice?.get(i)?.songID) {
                            refreshList?.add((getListfromDatabase as ArrayList<songs>)[j])
                        }
                    }
                }
            }
            else {
                var favouriteAdapter =
                    favoriteAdapter(refreshList as ArrayList<songs>, myActivity as Context)
                val mLayoutManager = LinearLayoutManager(activity)
                recyclerView?.layoutManager = mLayoutManager
                recyclerView?.itemAnimator = DefaultItemAnimator()
                recyclerView?.adapter = favouriteAdapter
                recyclerView?.setHasFixedSize(true)
            }
        }else{
            recyclerView?.visibility = View.INVISIBLE
            noFavorites?.visibility = View.VISIBLE

        }
    }
}

