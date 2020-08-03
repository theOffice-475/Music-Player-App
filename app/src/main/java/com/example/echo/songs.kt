package com.example.echo

import android.os.Parcel
import android.os.Parcelable

class songs(var songID: Long, var songTitle: String, var artist: String, var songData: String,
            var dateAdded: Long): Parcelable{


    override fun describeContents(): Int {
        return  0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }
    object Statified{
        var nameComparator: Comparator<songs> = Comparator<songs>{song1,song2->
            val songOne = song1.songTitle.toUpperCase()
            val songTwo = song2.songTitle.toUpperCase()
            songOne.compareTo(songTwo)
        }
        var dateComparator: Comparator<songs> = Comparator<songs>{song1,song2->
            val songOne = song1.dateAdded.toDouble()
            val songTwo = song2.dateAdded.toDouble()
            songTwo.compareTo(songOne)
        }
    }




}