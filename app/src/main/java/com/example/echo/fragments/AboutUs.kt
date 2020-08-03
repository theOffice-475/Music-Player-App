package com.example.echo.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.echo.R

/**
 * A simple [Fragment] subclass.
 */
class AboutUs : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.title = "About Us"
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }


}
