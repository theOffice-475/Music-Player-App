package com.example.echo.adapters

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.RelativeLayout
import android.widget.TextView

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.activites.MainActivity
import com.example.echo.fragments.MainScreenFragment
import com.example.echo.fragments.SongPlaying
import com.example.echo.songs
import kotlinx.android.synthetic.main.row_custom_mainscreen_adapter.*
import java.text.FieldPosition

class MainScreenAdapter (_songDetails: ArrayList<songs>,_context: Context) : RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_custom_mainscreen_adapter, parent, false)
        return MyViewHolder(itemView)

    }


    var songDetails: ArrayList<songs>? = null
    var mContext: Context? = null
    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val songObject = songDetails?.get(position)
        holder.trackTitle?.text = songObject?.songTitle
        holder.trackArtist?.text = songObject?.artist
        holder.contentHolder?.setOnClickListener({
            val songPlayingFragment = SongPlaying()

            var args = Bundle()
            args.putString("songArtist", songObject?.artist)
            args.putString("path", songObject?.songData)
            args.putString("songTitle", songObject?.songTitle)
            args.putInt("songId", songObject?.songID?.toInt() as Int)
            args.putInt("songposition", position)
            args.putParcelableArrayList("songData",songDetails)

            songPlayingFragment.arguments = args


            (mContext as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.details_fragment, songPlayingFragment)
                .addToBackStack("SongPlayingFragment")
                .commit()
        })
    }
    override fun getItemCount(): Int {
        if (songDetails == null) {
            return 0
        } else {
            return (songDetails as ArrayList<songs>).size
        }
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: RelativeLayout? = null

        init {
            this.trackTitle = view.findViewById<TextView>(R.id.trackTitle)
            this.trackArtist = view.findViewById<TextView>(R.id.trackArtist)
            this.contentHolder = view.findViewById<RelativeLayout>(R.id.contentRow)
        }
    }

}




